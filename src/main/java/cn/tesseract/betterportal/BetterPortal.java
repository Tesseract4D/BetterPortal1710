package cn.tesseract.betterportal;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

@Mod(modid = "betterportal", name = "Better Portal", acceptedMinecraftVersions = "[1.7.10]", version = Tags.VERSION)
public class BetterPortal {
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
    }

    public static void syncConfig(File f) {

    }
}
