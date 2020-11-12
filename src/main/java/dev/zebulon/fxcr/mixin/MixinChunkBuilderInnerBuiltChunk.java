package dev.zebulon.fxcr.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.zebulon.fxcr.RenderSubstitute;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.chunk.ChunkBuilder;

@Mixin(ChunkBuilder.BuiltChunk.class)
public class MixinChunkBuilderInnerBuiltChunk {

    @Shadow
    @Final
    private Map<RenderLayer, VertexBuffer> buffers;

    @Inject(method = "<init>()V", at = @At("RETURN"))
    public void init(ChunkBuilder outer, CallbackInfo ci) {
        buffers.put(RenderSubstitute.FXCR_LAYER, new VertexBuffer());
    }
}