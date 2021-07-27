package dev.zebulon.fxcr.mixin;

import java.util.Map;

import net.minecraft.client.render.model.BakedModelManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import dev.zebulon.fxcr.FxcrMod;
import dev.zebulon.fxcr.RenderSubstitute;
import dev.zebulon.fxcr.model.ConditionalBlockModel;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.render.model.BakedModel;

@Mixin(BlockModels.class)
public class MixinBlockModels {
    @Shadow
    @Final
    private BakedModelManager modelManager;

    @Redirect(method = "getModel", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
    public Object getModelRedirect(Map<BlockState, BakedModel> map, Object key) {
        BlockState state = (BlockState) key;
        Block block = state.getBlock();
        if (FxcrMod.enabled && (block == Blocks.CHEST || block == Blocks.TRAPPED_CHEST)) {
            FabricBakedModel original = (FabricBakedModel) map.get(key);
            FabricBakedModel replacement = (FabricBakedModel) this.modelManager.getModel(RenderSubstitute.getModelIdentifier(state));

            return new ConditionalBlockModel(original, replacement);
        } else {
            return map.get(state);
        }
    }
}
