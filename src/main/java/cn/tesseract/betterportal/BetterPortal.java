package cn.tesseract.betterportal;

import cn.tesseract.betterportal.block.BlockRunestone;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

@Mod(modid = "betterportal", name = "Better Portal", acceptedMinecraftVersions = "[1.7.10]", version = Tags.VERSION)
public class BetterPortal {
    public static BlockRunestone runestoneMithril;
    public static BlockRunestone runestoneAdamantium;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        runestoneMithril = (BlockRunestone) registerBlock(new BlockRunestone("mithril").setHardness(2.4F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("runestone_mithril").setBlockTextureName("obsidian"));
        runestoneAdamantium = (BlockRunestone) registerBlock(new BlockRunestone("adamantium").setHardness(2.4F).setResistance(20.0F).setStepSound(Block.soundTypeStone).setBlockName("runestone_adamantium").setBlockTextureName("obsidian"));
    }

    public static Block registerBlock(Block block, Class<? extends ItemBlock> itemBlock) {
        return GameRegistry.registerBlock(block, itemBlock, block.getUnlocalizedName().replace("tile.", ""));
    }

    public static Block registerBlock(Block block) {
        return GameRegistry.registerBlock(block, block.getUnlocalizedName().replace("tile.", ""));
    }
}
