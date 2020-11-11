package dev.zebulon.fxcr.mixin;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import dev.zebulon.fxcr.FxcrMod;
import dev.zebulon.fxcr.RenderSubstitute;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.chunk.BlockBufferBuilderStorage;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.render.chunk.ChunkOcclusionDataBuilder;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;

@Mixin(targets = "net/minecraft/client/render/chunk/ChunkBuilder$BuiltChunk$RebuildTask")
@SuppressWarnings("all")
public abstract class MixinChunkBuilderBuiltChunkRebuildTask {
    @Inject(method = "Lnet/minecraft/client/render/chunk/ChunkBuilder$BuiltChunk$RebuildTask;render(FFFLnet/minecraft/client/render/chunk/ChunkBuilder$ChunkData;Lnet/minecraft/client/render/chunk/BlockBufferBuilderStorage;)Ljava/util/Set;", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FluidState;isEmpty()Z"))
    private void onRender(float x, float y, float z, ChunkBuilder.ChunkData chunkData, BlockBufferBuilderStorage blockBufferBuilderStorage, CallbackInfoReturnable<Set> cir,
                          int i, BlockPos chunkPos, BlockPos blockPos2, ChunkOcclusionDataBuilder visgraph, Set set,
                          ChunkRendererRegion chunkRendererRegion, MatrixStack matrixStack, Random random,
                          BlockRenderManager blockRenderManager, Iterator var15, BlockPos blockPos3, BlockState blockState,
                          FluidState fluidState) {
        Block block = blockState.getBlock();
        if (FxcrMod.ENABLED && !blockState.isAir() && (block == Blocks.CHEST || block == Blocks.TRAPPED_CHEST)) {
            RenderSubstitute.onRender(blockPos3, blockState, blockBufferBuilderStorage, chunkData, random, matrixStack);
        }
    }
}
