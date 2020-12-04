package lyeoj.tfcthings.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import java.util.UUID;

public class TileEntityBearTrap extends TileEntity {
    private EntityLivingBase capturedEntity;
    private UUID capturedId;
    private boolean open;

    public TileEntityBearTrap() {
        super();
        this.open = true;
    }

    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("open", open);
        if(this.capturedEntity != null) {
            compound.setUniqueId("capturedId", this.capturedEntity.getUniqueID());
        }
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.open = compound.getBoolean("open");
        this.capturedId = compound.getUniqueId("capturedId");
        super.readFromNBT(compound);
    }

    protected void sendUpdates() {
        this.world.markBlockRangeForRenderUpdate(pos, pos);
        this.world.notifyBlockUpdate(pos, this.world.getBlockState(pos), this.world.getBlockState(pos), 3);
        this.world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
        markDirty();
    }

    private void readCapturedEntity() {
        if(this.capturedId != null) {
            if(this.world.getPlayerEntityByUUID(capturedId) != null) {
                EntityLivingBase entity = this.world.getPlayerEntityByUUID(capturedId);
                this.capturedEntity = entity;
            } else if(this.world instanceof WorldServer) {
                Entity entity = ((WorldServer)this.world).getEntityFromUuid(this.capturedId);
                if(entity instanceof EntityLivingBase) {
                    this.capturedEntity = (EntityLivingBase)entity;
                }
            }
        }
    }

    public boolean isOpen() {
        return this.open;
    }

    public void setOpen(boolean isOpen) {
        this.open = isOpen;
        sendUpdates();
    }

    public EntityLivingBase getCapturedEntity() {
        readCapturedEntity();
        return this.capturedEntity;
    }

    public void setCapturedEntity(EntityLivingBase entity) {

        this.capturedEntity = entity;
        if(entity != null) {
            this.capturedId = entity.getUniqueID();
        } else {
            this.capturedId = UUID.randomUUID();
        }
        sendUpdates();
    }
}
