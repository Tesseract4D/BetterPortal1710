package cn.tesseract.betterportal.block;

import net.minecraft.block.BlockPortal;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBetterPortal extends BlockPortal {
    private IIcon runegate_icon;
    private IIcon nether_portal_icon;

    public BlockBetterPortal() {
        super();
    }

    public void registerBlockIcons(IIconRegister reg) {
        super.registerBlockIcons(reg);
        this.runegate_icon = reg.registerIcon("betterportal:runegate");
        this.nether_portal_icon = reg.registerIcon("betterportal:portal_nether");
    }

    //TODO 将57僵尸猪人改成其他实体或者不刷实体
    public void updateTick(World worldIn, int x, int y, int z, Random random) {
        if (worldIn.provider.isSurfaceWorld() && worldIn.getGameRules().getGameRuleBooleanValue("doMobSpawning") && random.nextInt(2000) < worldIn.difficultySetting.getDifficultyId()) {
            int l = y;

            while (!World.doesBlockHaveSolidTopSurface(worldIn, x, l, z) && l > 0) {
                --l;
            }

            if (l > 0 && !worldIn.getBlock(x, l + 1, z).isNormalCube()) {
                Entity entity = ItemMonsterPlacer.spawnCreature(worldIn, 57, (double) x + 0.5D, (double) l + 1.1D, (double) z + 0.5D);

                if (entity != null) {
                    entity.timeUntilPortal = entity.getPortalCooldown();
                }
            }
        }
    }

    public static int getDestinationBit(int metadata) {
        return metadata & ~0;
    }

    public IIcon getIcon(int side, int meta) {
        int destination_bit = getDestinationBit(meta);
        return destination_bit == 0 ? this.runegate_icon : (destination_bit == 1 ? this.blockIcon : this.nether_portal_icon);
    }
}
