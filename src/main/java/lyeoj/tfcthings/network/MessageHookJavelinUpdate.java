package lyeoj.tfcthings.network;

import io.netty.buffer.ByteBuf;
import lyeoj.tfcthings.main.TFCThings;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageHookJavelinUpdate implements IMessage {
    private int javelinID;
    private boolean inGround;

    public MessageHookJavelinUpdate() {

    }

    public MessageHookJavelinUpdate(int javelinID, boolean inGround) {
        this.javelinID = javelinID;
        this.inGround = inGround;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        javelinID = buf.readInt();
        inGround = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(javelinID);
        buf.writeBoolean(inGround);
    }

    public static class Handler implements IMessageHandler<MessageHookJavelinUpdate, IMessage> {

        @Override
        public IMessage onMessage(MessageHookJavelinUpdate message, MessageContext ctx) {
            TFCThings.proxy.getThreadListener(ctx).addScheduledTask(() ->
                    TFCThings.proxy.syncJavelinGroundState(message.javelinID, message.inGround));
            return null;
        }
    }

}
