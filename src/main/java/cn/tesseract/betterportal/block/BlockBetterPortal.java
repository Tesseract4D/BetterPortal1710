package cn.tesseract.betterportal.block;

import cn.tesseract.betterportal.BetterPortal;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
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

    public boolean func_150000_e(World world, int x, int y, int z) {
        if (world.provider.dimensionId == 1) {
            return false;
        } else {
            byte a = 0, b = 0;
            if (world.getBlock(x - 1, y, z) == Blocks.obsidian || world.getBlock(x + 1, y, z) == Blocks.obsidian) {
                a = 1;
            }

            if (world.getBlock(x, y, z - 1) == Blocks.obsidian || world.getBlock(x, y, z + 1) == Blocks.obsidian) {
                b = 1;
            }

            if (a == b) {
                return false;
            } else {
                if (world.getBlock(x - a, y, z - b) == Blocks.air) {
                    x -= a;
                    z -= b;
                }

                int i, j;
                for (i = -1; i <= 2; ++i) {
                    for (j = -1; j <= 3; ++j) {
                        boolean var9 = i == -1 || i == 2 || j == -1 || j == 3;
                        if (i != -1 && i != 2 || j != -1 && j != 3) {
                            Block var10 = world.getBlock(x + a * i, y + j, z + b * i);
                            if (var9) {
                                if (var10 != Blocks.obsidian) {
                                    return false;
                                }
                            } else if (var10 != Blocks.air && var10 != Blocks.fire /*&& var10 != Blocks.spark*/) {
                                return false;
                            }
                        }
                    }
                }

                for (i = 0; i < 2; ++i) {
                    for (j = 0; j < 3; ++j) {
                        world.setBlock(x + a * i, y + j, z + b * i, Blocks.portal, 0, 2);
                    }
                }

                int metadata = this.getPortalTypeBasedOnLocation(world, x, y, z, true);

                for (i = 0; i < 2; ++i) {
                    for (j = 0; j < 3; ++j) {
                        world.setBlock(x + a * i, y + j, z + b * i, Blocks.portal, metadata, 2);
                    }
                }

                return true;
            }
        }
    }

    public static int getDestinationBitForDimensionId(int destination_dimension_id) {
        if (destination_dimension_id == 0) {
            return 0;
        } else if (destination_dimension_id == -2) {
            return 1;
        } else if (destination_dimension_id == -1) {
            return 2;
        } else {
            System.out.println("getDestinationBitForDimensionId: destination_dimension_id not handled " + destination_dimension_id);
            return 0;
        }
    }

    public static int getDestinationBit(World world) {
        return getDestinationBitForDimensionId(world.provider.dimensionId);
    }

    public int getPortalTypeBasedOnLocation(World world, int x, int y, int z, boolean test_for_runegate) {
        if (test_for_runegate && this.isRunegate(world, x, y, z, true)) {
            return 8 | getDestinationBit(world);
        } else if (world.provider.dimensionId == -1) {
            return 1;
        } else if (this.isTouchingBottomBedrock(world, x, y, z)) {
            return world.provider.dimensionId == 0 ? 1 : 2;
        } else {
            return 0;
        }
    }

    public boolean isRunegate(World world, int x, int y, int z, boolean intensive_check) {
        if (!intensive_check) {
            return this.isRunegate(world.getBlockMetadata(x, y, z));
        } else {
            return this.getRunegateType(world, x, y, z) != null;
        }
    }

    public boolean isRunegate(int metadata) {
        return (metadata & 8) == 8;
    }

    public BlockRunestone getRunegateType(World world, int x, int y, int z) {
        int frame_min_x = this.getFrameMinX(world, x, y, z);
        int frame_max_x = this.getFrameMaxX(world, x, y, z);
        int frame_min_y = this.getFrameMinY(world, x, y, z);
        int frame_max_y = this.getFrameMaxY(world, x, y, z);
        int frame_min_z = this.getFrameMinZ(world, x, y, z);
        int frame_max_z = this.getFrameMaxZ(world, x, y, z);
        if (frame_max_x - frame_min_x > frame_max_z - frame_min_z) {
            if (world.getBlock(frame_min_x, frame_min_y, z) == BetterPortal.runestoneMithril && world.getBlock(frame_max_x, frame_min_y, z) == BetterPortal.runestoneMithril && world.getBlock(frame_min_x, frame_max_y, z) == BetterPortal.runestoneMithril && world.getBlock(frame_max_x, frame_max_y, z) == BetterPortal.runestoneMithril) {
                return BetterPortal.runestoneMithril;
            } else {
                return world.getBlock(frame_min_x, frame_min_y, z) == BetterPortal.runestoneAdamantium && world.getBlock(frame_max_x, frame_min_y, z) == BetterPortal.runestoneAdamantium && world.getBlock(frame_min_x, frame_max_y, z) == BetterPortal.runestoneAdamantium && world.getBlock(frame_max_x, frame_max_y, z) == BetterPortal.runestoneAdamantium ? BetterPortal.runestoneAdamantium : null;
            }
        } else if (world.getBlock(x, frame_min_y, frame_min_z) == BetterPortal.runestoneMithril && world.getBlock(x, frame_min_y, frame_max_z) == BetterPortal.runestoneMithril && world.getBlock(x, frame_max_y, frame_min_z) == BetterPortal.runestoneMithril && world.getBlock(x, frame_max_y, frame_max_z) == BetterPortal.runestoneMithril) {
            return BetterPortal.runestoneMithril;
        } else {
            return world.getBlock(x, frame_min_y, frame_min_z) == BetterPortal.runestoneAdamantium && world.getBlock(x, frame_min_y, frame_max_z) == BetterPortal.runestoneAdamantium && world.getBlock(x, frame_max_y, frame_min_z) == BetterPortal.runestoneAdamantium && world.getBlock(x, frame_max_y, frame_max_z) == BetterPortal.runestoneAdamantium ? BetterPortal.runestoneAdamantium : null;
        }
    }

    public boolean isTouchingBottomBedrock(World world, int x, int y, int z) {
        int frame_min_y = this.getFrameMinY(world, x, y, z);
        if (frame_min_y > 8) {
            return false;
        } else {
            int frame_min_x = this.getFrameMinX(world, x, y, z);
            int frame_max_x = this.getFrameMaxX(world, x, y, z);
            int frame_min_z = this.getFrameMinZ(world, x, y, z);
            int frame_max_z = this.getFrameMaxZ(world, x, y, z);

            for (int frame_x = frame_min_x; frame_x <= frame_max_x; ++frame_x) {
                for (int frame_z = frame_min_z; frame_z <= frame_max_z; ++frame_z) {
                    if (isBottomBlock(world, frame_x, frame_min_y - 1, frame_z)) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    //TODO 不同维度应该有不同的底部方块
    public final boolean isBottomBlock(World world, int x, int y, int z) {
        return world.getBlock(x, y, z) == Blocks.bedrock;
    }

    private int getFrameMinX(World world, int x, int y, int z) {
        boolean x_aligned = world.getBlock(x + 1, y, z) == this;

        int min_x;
        for (min_x = x; world.getBlock(min_x - 1, y, z) == this; x_aligned = true) {
            --min_x;
        }

        return x_aligned ? min_x - 1 : min_x;
    }

    private int getFrameMaxX(World world, int x, int y, int z) {
        boolean x_aligned = world.getBlock(x - 1, y, z) == this;

        int max_x;
        for (max_x = x; world.getBlock(max_x + 1, y, z) == this; x_aligned = true) {
            ++max_x;
        }

        return x_aligned ? max_x + 1 : max_x;
    }

    private int getFrameMinY(World world, int x, int y, int z) {
        int min_y;
        for (min_y = y; world.getBlock(x, min_y - 1, z) == this; --min_y) {
        }

        return min_y - 1;
    }

    private int getFrameMaxY(World world, int x, int y, int z) {
        int max_y;
        for (max_y = y; world.getBlock(x, max_y + 1, z) == this; ++max_y) {
        }

        return max_y + 1;
    }

    private int getFrameMinZ(World world, int x, int y, int z) {
        boolean z_aligned = world.getBlock(x, y, z + 1) == this;

        int min_z;
        for (min_z = z; world.getBlock(x, y, min_z - 1) == this; z_aligned = true) {
            --min_z;
        }

        return z_aligned ? min_z - 1 : min_z;
    }

    private int getFrameMaxZ(World world, int x, int y, int z) {
        boolean z_aligned = world.getBlock(x, y, z - 1) == this;

        int max_z;
        for (max_z = z; world.getBlock(x, y, max_z + 1) == this; z_aligned = true) {
            ++max_z;
        }

        return z_aligned ? max_z + 1 : max_z;
    }

    public static int getDestinationBit(int metadata) {
        return metadata & ~0;
    }

    public IIcon getIcon(int side, int meta) {
        int destination_bit = getDestinationBit(meta);
        return destination_bit == 0 ? this.runegate_icon : (destination_bit == 1 ? this.blockIcon : this.nether_portal_icon);
    }
}
