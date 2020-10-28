package lyeoj.tfcthings.entity.projectile;

import io.netty.buffer.ByteBuf;
import lyeoj.tfcthings.blocks.BlockRopeBridge;
import lyeoj.tfcthings.init.TFCThingsBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.ArrayList;

public class EntityRopeBridgeThrown extends EntityThrowable {

    private ItemStack bridges;

    public EntityRopeBridgeThrown(World worldIn) {
        super(worldIn);
        bridges = ItemStack.EMPTY;
    }

    public EntityRopeBridgeThrown(World worldIn, EntityLivingBase throwerIn, ItemStack bridges) {
        super(worldIn, throwerIn);
        this.bridges = bridges;
    }

    public ItemStack getBridges() {
        return bridges;
    }

    public void setBridges(ItemStack bridges) {
        this.bridges = bridges;
    }

    public void writeSpawnData(ByteBuf buffer) {
        ByteBufUtils.writeItemStack(buffer, this.bridges);
    }

    public void readSpawnData(ByteBuf additionalData) {
        this.setBridges(ByteBufUtils.readItemStack(additionalData));
    }

    public void writeEntityToNBT(NBTTagCompound compound) {
        NBTTagList tag = new NBTTagList();
        tag.appendTag(this.bridges.serializeNBT());
        compound.setTag("bridges", tag);
        super.writeEntityToNBT(compound);
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        NBTTagList tag = compound.getTagList("bridges", 10);
        this.bridges = tag.tagCount() > 0 ? new ItemStack(tag.getCompoundTagAt(0)) : ItemStack.EMPTY;
        super.readEntityFromNBT(compound);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) {
            if(result.getBlockPos() == null) {
                this.world.setEntityState(this, (byte)3);
                this.setDead();
                return;
            }
            if(result.getBlockPos() != null && world.getBlockState(result.getBlockPos()).getCollisionBoundingBox(world, result.getBlockPos()) != Block.NULL_AABB) {
                if(getThrower() != null) {
                    BlockPos end = result.getBlockPos().up();
                    BlockPos start = getThrower().getPosition();
                    int xDif = end.getX() - start.getX();
                    int zDif = end.getZ() - start.getZ();
                    boolean axis = Math.abs(zDif) > Math.abs(xDif);
                    int length = axis ? Math.abs(zDif) : Math.abs(xDif);
                    boolean diagonal = false;
                    int yDif = start.getY() - end.getY();
                    if(length - 1 > bridges.getCount()) {
                        getThrower().sendMessage(new TextComponentTranslation("tfcthings.tooltip.rope_bridge_too_long", new Object[0]));
                    } else if(((length - 2) / 8) < Math.abs(yDif)) {
                        getThrower().sendMessage(new TextComponentTranslation("tfcthings.tooltip.rope_bridge_too_steep", new Object[0]));
                    } else {
                        if(axis) {
                            if(Math.abs(xDif) < 3) {
                                BlockPos tempEnd = new BlockPos(start.getX(), end.getY(), end.getZ());
                                if(shouldReplaceBlock(tempEnd.down(), world)) {
                                    BlockPos tempStart = new BlockPos(end.getX(), start.getY(), start.getZ());
                                    if(shouldReplaceBlock(tempStart.down(), world)) {
                                        getThrower().sendMessage(new TextComponentTranslation("tfcthings.tooltip.rope_bridge_bad_connection", new Object[0]));
                                        diagonal = true;
                                    } else {
                                        start = tempStart;
                                    }
                                } else {
                                    end = tempEnd;
                                }
                            } else {
                                getThrower().sendMessage(new TextComponentTranslation("tfcthings.tooltip.rope_bridge_diagonal", new Object[0]));
                                diagonal = true;
                            }

                        } else {
                            if(Math.abs(zDif) < 3) {
                                BlockPos tempEnd = new BlockPos(end.getX(), end.getY(), start.getZ());
                                if(shouldReplaceBlock(tempEnd.down(), world)) {
                                    BlockPos tempStart = new BlockPos(start.getX(), start.getY(), end.getZ());
                                    if(shouldReplaceBlock(tempStart.down(), world)) {
                                        getThrower().sendMessage(new TextComponentTranslation("tfcthings.tooltip.rope_bridge_bad_connection", new Object[0]));
                                        diagonal = true;
                                    } else {
                                        start = tempStart;
                                    }
                                } else {
                                    end = tempEnd;
                                }
                            } else {
                                getThrower().sendMessage(new TextComponentTranslation("tfcthings.tooltip.rope_bridge_diagonal", new Object[0]));
                                diagonal = true;
                            }
                        }
                        EnumFacing direction = axis ? (zDif > 0 ? EnumFacing.SOUTH : EnumFacing.NORTH) : (xDif > 0 ? EnumFacing.EAST : EnumFacing.WEST);
                        switch(direction) {
                            case SOUTH:
                                start = start.south();
                                end = end.north();
                                break;
                            case NORTH:
                                start = start.north();
                                end = end.south();
                                break;
                            case EAST:
                                start = start.east();
                                end = end.west();
                                break;
                            default:
                                start = start.west();
                                end = end.east();
                        }
                        if(!diagonal) {
                            buildBridge(start, end, axis, direction, yDif, length);
                        }
                    }
                }
                this.world.setEntityState(this, (byte)3);
                this.setDead();
            }
        }
    }

    private void buildBridge(BlockPos start, BlockPos end, boolean axis, EnumFacing direction, int yDif, int length) {
        ArrayList<BridgeInfo> bridgePath = new ArrayList<BridgeInfo>();
        int startHeight = 0;
        int endHeight = 0;
        int startDif = yDif > 0 ? yDif + 1 : 1;
        int endDif = yDif < 0 ? Math.abs(yDif) + 1 : 1;
        int remainingPieces = length;
        while(remainingPieces > 1) {
            if(startDif == endDif) {
                if(shouldReplaceBlock(start, world)) {
                    bridgePath.add(new BridgeInfo(start, startHeight));
                    start = moveInDirection(direction, start, false);
                    if(startHeight == 0) {
                        if(startDif > 0 && shouldReplaceBlock(start.down(), world)) {
                            startHeight = 7;
                            start = start.down();
                            startDif--;
                        } else if(startHeight < endHeight) {
                            if(endHeight - startHeight >= remainingPieces / 2) {
                                startHeight++;
                            }
                        }
                    } else {
                        if(startHeight < endHeight) {
                            if(endHeight - startHeight >= remainingPieces / 2) {
                                startHeight++;
                            }
                        } else {
                            startHeight--;
                        }
                    }
                    remainingPieces--;
                } else {
                    getThrower().sendMessage(new TextComponentTranslation("tfcthings.tooltip.rope_bridge_interrupted", new Object[0]));
                    return;
                }
                if(remainingPieces > 0) {
                    if(shouldReplaceBlock(end, world)) {
                        if(remainingPieces == 1 && endHeight < 7) {
                            endHeight++;
                        }
                        bridgePath.add(new BridgeInfo(end, endHeight));
                        end = moveInDirection(direction, end, true);
                        if(endHeight == 0) {
                            if(endDif > 0 && shouldReplaceBlock(end.down(), world)) {
                                endHeight = 7;
                                end = end.down();
                                endDif--;
                            } else if(endHeight < startHeight) {
                                if(startHeight - endHeight >= remainingPieces / 2) {
                                    endHeight++;
                                }
                            }
                        } else {
                            if(endHeight < startHeight) {
                                if(startHeight - endHeight >= remainingPieces / 2) {
                                    endHeight++;
                                }
                            } else {
                                endHeight--;
                            }
                        }
                        remainingPieces--;
                    } else {
                        getThrower().sendMessage(new TextComponentTranslation("tfcthings.tooltip.rope_bridge_interrupted", new Object[0]));
                        return;
                    }
                }
            } else if(startDif > endDif) {
                if(shouldReplaceBlock(start, world)) {
                    bridgePath.add(new BridgeInfo(start, startHeight));
                    start = moveInDirection(direction, start, false);
                    if(startHeight == 0) {
                        if(startDif > 0 && shouldReplaceBlock(start.down(), world)) {
                            startHeight = 7;
                            start = start.down();
                            startDif--;
                        } else if(((remainingPieces - 1) / 8) < startDif - 1){
                            getThrower().sendMessage(new TextComponentTranslation("tfcthings.tooltip.rope_bridge_too_steep", new Object[0]));
                            return;
                        }
                    } else {
                        startHeight--;
                    }
                    remainingPieces--;
                } else {
                    getThrower().sendMessage(new TextComponentTranslation("tfcthings.tooltip.rope_bridge_interrupted", new Object[0]));
                    return;
                }
            } else {
                if(shouldReplaceBlock(end, world)) {
                    bridgePath.add(new BridgeInfo(end, endHeight));
                    end = moveInDirection(direction, end, true);
                    if(endHeight == 0) {
                        if(endDif > 0 && shouldReplaceBlock(end.down(), world)) {
                            endHeight = 7;
                            end = end.down();
                            endDif--;
                        } else if(((remainingPieces - 1) / 8) < endDif - 1){
                            getThrower().sendMessage(new TextComponentTranslation("tfcthings.tooltip.rope_bridge_too_steep", new Object[0]));
                            return;
                        }
                    } else {
                        endHeight--;
                    }
                    remainingPieces--;
                } else {
                    getThrower().sendMessage(new TextComponentTranslation("tfcthings.tooltip.rope_bridge_interrupted", new Object[0]));
                    return;
                }
            }
        }
        for(BridgeInfo info : bridgePath) {
            world.setBlockState(info.pos, TFCThingsBlocks.ROPE_BRIDGE_BLOCK.getDefaultState().withProperty(BlockRopeBridge.AXIS, axis).withProperty(BlockRopeBridge.OFFSET, info.height));
        }
        if(getThrower() instanceof EntityPlayer && !(((EntityPlayer) getThrower()).isCreative())) {
            bridges.setCount(bridges.getCount() - (length - 1));
        }
    }

    private BlockPos moveInDirection(EnumFacing direction, BlockPos pos, boolean flip) {
        BlockPos value;
        switch(direction) {
            case SOUTH:
                if(flip) {
                    value = pos.north();
                } else {
                    value = pos.south();
                }
                break;
            case NORTH:
                if(flip) {
                    value = pos.south();
                } else {
                    value = pos.north();
                }
                break;
            case EAST:
                if(flip) {
                    value = pos.west();
                } else {
                    value = pos.east();
                }
                break;
            default:
                if(flip) {
                    value = pos.east();
                } else {
                    value = pos.west();
                }
        }
        return value;
    }

    private boolean shouldReplaceBlock(BlockPos pos, World world) {
        IBlockState iblockstate = world.getBlockState(pos);
        Material material = iblockstate.getMaterial();
        if(material == Material.LAVA || material == Material.WATER) {
            return false;
        }
        return world.getBlockState(pos).getBlock().isReplaceable(world, pos);
    }

    private static class BridgeInfo {
        public BlockPos pos;
        public int height;
        public boolean axis;

        public BridgeInfo(BlockPos pos, int height) {
            this.pos = pos;
            this.height = height;
        }

    }

}
