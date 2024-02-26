package org.mebossmer.taunts;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;

public class TauntMessage implements IMessage {
    private CharSequence event;
    private int len;
    private double x;
    private double y;
    private double z;

    public TauntMessage() {}

    public TauntMessage(@NotNull String event, @NotNull Vec3d pos) {
        this.event = event;
        this.len = event.length();
        this.x = pos.x;
        this.y = pos.y;
        this.z = pos.z;
    }

    public String getEvent() {
        return event.toString();
    }

    public Vec3d getPos() {
        return new Vec3d(x, y, z);
    }

    @Override
    public void toBytes(@NotNull ByteBuf buf) {
        buf.writeInt(len);
        buf.writeCharSequence(event, Charset.defaultCharset());
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    @Override
    public void fromBytes(@NotNull ByteBuf buf) {
        len = buf.readInt();
        event = buf.readCharSequence(len, Charset.defaultCharset());
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
    }
}
