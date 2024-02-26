package org.mebossmer.taunts;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

@Mod(modid = Taunts.MODID, name = Taunts.NAME, version = Taunts.VERSION)
public class Taunts {
    public static final String MODID = "taunts";
    public static final String NAME = "Taunts";
    public static final String VERSION = "1.0";

    public static final SimpleNetworkWrapper NETWORK_WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

    public static Logger logger;


    @EventHandler
    public void preInit(@NotNull FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(Taunts.class);

        NETWORK_WRAPPER.registerMessage(TauntMessageHandler.class, TauntMessage.class, 0, Side.CLIENT);
    }

    @SubscribeEvent
    public static void chatSent(@NotNull ServerChatEvent event) {
        EntityPlayerMP player = event.getPlayer();

        String msg = event.getMessage();
        int index = 0;
        try {
            index = Integer.parseInt(msg) - 1;
        } catch(NumberFormatException e) {
            // no such taunt, do nothing
            return;
        }

        NETWORK_WRAPPER.sendToAll(new TauntMessage(TauntsConfig.taunts[index], player.getPositionVector()));
    }

    @Config(modid = Taunts.MODID)
    public static class TauntsConfig {
        @Config.Comment("list of sound events (beginning at index 1)")
        public static String[] taunts = new String[] {};
    }
}
