package dev.zebulon.fxcr.mixin.sodium;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.zebulon.fxcr.model.ConditionalBlockModel;
import me.jellysquid.mods.sodium.client.render.chunk.compile.buffers.ChunkModelBuilder;
import me.jellysquid.mods.sodium.client.render.pipeline.BlockRenderer;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;

@Mixin(BlockRenderer.class)
public class MixinBlockRenderer {
    @Inject(method = "renderModel", at = @At("HEAD"), remap = false)
    public void renderModelFxcrHook(BlockRenderView world, BlockState state, BlockPos pos, BlockPos origin,
            BakedModel model, ChunkModelBuilder buffers, boolean cull, long seed,
            CallbackInfoReturnable<Boolean> callbackInfo) {
        ConditionalBlockModel.CURRENT_MODEL_POSITION_THREAD_LOCAL.set(pos);
    }
}
