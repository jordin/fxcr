package dev.zebulon.fxcr.mixin.vanilla;

import dev.zebulon.fxcr.FxcrMod;
import dev.zebulon.fxcr.RenderSubstitute;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.chunk.BlockBufferBuilderStorage;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.render.chunk.ChunkOcclusionDataBuilder;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

@Mixin(targets = "net/minecraft/client/render/chunk/ChunkBuilder$BuiltChunk$RebuildTask")
@SuppressWarnings("all")
public abstract class MixinChunkBuilderBuiltChunkRebuildTask {

    @Unique
    private ChunkBuilder.BuiltChunk builtChunk;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void initHook(ChunkBuilder.BuiltChunk builtChunk, double distance, ChunkRendererRegion chunkRendererRegion, CallbackInfo callbackInfo) {
        this.builtChunk = builtChunk;
    }

//    @Redirect(method = "Lnet/minecraft/client/render/chunk/ChunkBuilder$BuiltChunk$RebuildTask;render(FFFLnet/minecraft/client/render/chunk/ChunkBuilder$ChunkData;Lnet/minecraft/client/render/chunk/BlockBufferBuilderStorage;)Ljava/util/Set;", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/chunk/ChunkRendererRegion;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;"))
//    public BlockState getBlockStateRedirect(ChunkRendererRegion region, BlockPos pos) {
//        FxcrMod.CURRENT_POS = pos;
//        return region.getBlockState(pos);
//    }

    @Inject(method = "Lnet/minecraft/client/render/chunk/ChunkBuilder$BuiltChunk$RebuildTask;render(FFFLnet/minecraft/client/render/chunk/ChunkBuilder$ChunkData;Lnet/minecraft/client/render/chunk/BlockBufferBuilderStorage;)Ljava/util/Set;", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getRenderType()Lnet/minecraft/block/BlockRenderType;"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void onGetRenderTypeHook(float cameraX, float cameraY, float cameraZ, ChunkBuilder.ChunkData data, BlockBufferBuilderStorage buffers, CallbackInfoReturnable<Set<BlockEntity>> callbackInfo, int i, BlockPos blockPos, BlockPos blockPos2, ChunkOcclusionDataBuilder chunkOcclusionDataBuilder, Set<?> set, ChunkRendererRegion chunkRendererRegion, MatrixStack matrixStack, Random random, BlockRenderManager blockRenderManager, Iterator<?> iterator, BlockPos blockPos3, BlockState blockState) {
        BlockRenderType renderType = RenderSubstitute.getRenderType(blockState, blockPos3);

        if (renderType == BlockRenderType.MODEL && blockState.getBlock() instanceof ChestBlock) {
            BlockState transformedBlockState = RenderSubstitute.transformBlockState(blockState);
            RenderLayer renderLayer2 = RenderLayers.getBlockLayer(transformedBlockState);
            BufferBuilder bufferBuilder2 = buffers.get(renderLayer2);
            if (data.initializedLayers.add(renderLayer2)) {
                this.builtChunk.beginBufferBuilding(bufferBuilder2);
            }

            matrixStack.push();
            matrixStack.translate((double)(blockPos3.getX() & 15), (double)(blockPos3.getY() & 15), (double)(blockPos3.getZ() & 15));
            if (blockRenderManager.renderBlock(transformedBlockState, blockPos3, chunkRendererRegion, matrixStack, bufferBuilder2, true, random)) {
                data.empty = false;
                data.nonEmptyLayers.add(renderLayer2);
            }

            matrixStack.pop();
        }
    }
}