package dev.zebulon.fxcr.mixin.sodium;

import dev.zebulon.fxcr.RenderSubstitute;
import me.jellysquid.mods.sodium.client.render.chunk.ChunkGraphicsState;
import me.jellysquid.mods.sodium.client.render.chunk.compile.ChunkBuildBuffers;
import me.jellysquid.mods.sodium.client.render.chunk.compile.ChunkBuildResult;
import me.jellysquid.mods.sodium.client.render.chunk.data.ChunkRenderBounds;
import me.jellysquid.mods.sodium.client.render.chunk.data.ChunkRenderData;
import me.jellysquid.mods.sodium.client.render.pipeline.context.ChunkRenderCacheLocal;
import me.jellysquid.mods.sodium.client.util.task.CancellationSource;
import me.jellysquid.mods.sodium.client.world.WorldSlice;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.chunk.ChunkOcclusionDataBuilder;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.*;

import me.jellysquid.mods.sodium.client.render.chunk.tasks.ChunkRenderRebuildTask;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ChunkRenderRebuildTask.class)
public class MixinChunkRenderRebuildTask<T extends ChunkGraphicsState> {
    @Redirect(method = "performBuild", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getRenderType()Lnet/minecraft/block/BlockRenderType;"))
    public BlockRenderType getRenderTypeRedirect(BlockState blockState) {
        return BlockRenderType.INVISIBLE;
    }

    @Inject(method = "performBuild", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getRenderType()Lnet/minecraft/block/BlockRenderType;"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void onGetRenderTypeHook(ChunkRenderCacheLocal cache, ChunkBuildBuffers buffers, CancellationSource cancellationSource, CallbackInfoReturnable<ChunkBuildResult<T>> cir, ChunkRenderData.Builder renderData, ChunkOcclusionDataBuilder occluder, ChunkRenderBounds.Builder bounds, WorldSlice slice, int baseX, int baseY, int baseZ, BlockPos.Mutable pos, BlockPos renderOffset, int relY, int relZ, int relX, BlockState blockState) {
        BlockRenderType renderType = RenderSubstitute.getRenderType(blockState, pos);

        if (renderType == BlockRenderType.MODEL) {
            RenderLayer layer = RenderLayers.getBlockLayer(blockState);

            BakedModel model = cache.getBlockModels()
                    .getModel(blockState);

            long seed = blockState.getRenderingSeed(pos);

            if (cache.getBlockRenderer().renderModel(slice, blockState, pos, model, buffers.get(layer), true, seed)) {
                bounds.addBlock(relX, relY, relZ);
            }
        }
    }
}
