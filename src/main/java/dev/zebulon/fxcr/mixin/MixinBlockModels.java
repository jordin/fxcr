package dev.zebulon.fxcr.mixin;

import dev.zebulon.fxcr.FxcrMod;
import dev.zebulon.fxcr.RenderSubstitute;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.render.model.BakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;

@Mixin(BlockModels.class)
public class MixinBlockModels {
    @Redirect(method = "getModel", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
    public Object getModelRedirect(Map<BlockState, BakedModel> map, Object key) {
        BlockState state = (BlockState) key;
        Block block = state.getBlock();
        if (FxcrMod.enabled && (block == Blocks.CHEST || block == Blocks.TRAPPED_CHEST)) {
            return map.get(RenderSubstitute.transformBlockState(state));
        } else {
            return map.get(state);
        }
    }
}
