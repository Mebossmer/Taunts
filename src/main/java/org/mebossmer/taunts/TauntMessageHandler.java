package org.mebossmer.taunts;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.jetbrains.annotations.NotNull;

public class TauntMessageHandler implements IMessageHandler<TauntMessage, IMessage> {
    @Override
    public IMessage onMessage(@NotNull TauntMessage message, @NotNull MessageContext ctx) {
        Taunts.logger.info(message.getEvent());
        Taunts.logger.info(message.getPos());

        Vec3d pos = message.getPos();

        Minecraft.getMinecraft().world.playSound(
                pos.x,
                pos.y,
                pos.z,
                new SoundEvent(new ResourceLocation(message.getEvent())),
                SoundCategory.PLAYERS,
                1.0f,
                1.0f,
                false
        );

        return null;
    }
}
