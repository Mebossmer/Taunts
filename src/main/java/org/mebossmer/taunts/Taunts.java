package org.mebossmer.taunts;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Mod(modid = Taunts.MODID, name = Taunts.NAME, version = Taunts.VERSION)
public class Taunts {
    public static final String MODID = "taunts";
    public static final String NAME = "Taunts";
    public static final String VERSION = "1.0";

    private static Logger logger;


    @EventHandler
    public void preInit(@NotNull FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(Taunts.class);
    }

    @SubscribeEvent
    public static void chatSent(@NotNull ServerChatEvent event) {
        EntityPlayerMP player = event.getPlayer();
        WorldServer world = player.getServerWorld();

        String msg = event.getMessage();
        int index = 0;
        try {
            index = Integer.parseInt(msg) - 1;
            world.playSound(
                    null,
                    player.getPosition(),
                    Objects.requireNonNull(SoundEvent.REGISTRY.getObject(new ResourceLocation(TauntsConfig.taunts[index]))),
                    SoundCategory.PLAYERS,
                    1.0f,
                    1.0f
            );
            logger.info("played taunt: " + index);
        } catch(NumberFormatException e) {
            logger.info("No taunt index");
        } catch(IndexOutOfBoundsException e) {
            logger.info("no such taunt");
        }
    }

    @Config(modid = Taunts.MODID)
    public static class TauntsConfig {
        @Config.Comment("list of sound events (beginning at index 1)")
        public static String[] taunts = new String[] {};
    }
}
