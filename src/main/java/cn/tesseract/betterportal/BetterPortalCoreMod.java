package cn.tesseract.betterportal;

import cn.tesseract.mycelium.asm.minecraft.HookLibPlugin;
import cn.tesseract.mycelium.asm.minecraft.HookLoader;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

public class BetterPortalCoreMod extends HookLoader {
    @Override
    protected void registerHooks() {
        //MyceliumCoreMod.dumpTransformedClass = true;
        registerHookContainer(BetterPortalHook.class.getName());
        registerNodeTransformer("net.minecraft.block.Block", node -> {
            for (MethodNode method : node.methods) {
                if (HookLibPlugin.getMethodMcpName(method.name).equals("registerBlocks"))
                    for (int i = 0; i < method.instructions.size(); i++) {
                        AbstractInsnNode insn = method.instructions.get(i);
                        if (insn instanceof MethodInsnNode minsn) {
                            if (minsn.name.equals("<init>") && minsn.owner.equals("net/minecraft/block/BlockPortal"))
                                minsn.owner = ((TypeInsnNode) method.instructions.get(i - 2)).desc = "cn/tesseract/betterportal/block/BlockBetterPortal";
                        }
                    }
            }
        });
    }
}
