package dev.zebulon.fxcr.mixin.vanilla;

import dev.zebulon.fxcr.FxcrMod;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net/minecraft/client/render/chunk/ChunkBuilder$BuiltChunk$RebuildTask")
@SuppressWarnings("all")
public abstract class MixinChunkBuilderBuiltChunkRebuildTask {
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/chunk/ChunkRendererRegion;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;"))
    public BlockState getBlockStateRedirect(ChunkRendererRegion region, BlockPos pos) {
        FxcrMod.CURRENT_POS = pos;
        return region.getBlockState(pos);
    }
}