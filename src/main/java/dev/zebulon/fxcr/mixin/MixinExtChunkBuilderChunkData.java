package dev.zebulon.fxcr.mixin;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.chunk.ChunkBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(ChunkBuilder.ChunkData.class)
public interface MixinExtChunkBuilderChunkData {
    @Accessor
    Set<RenderLayer> getInitializedLayers();

    @Accessor
    Set<RenderLayer> getNonEmptyLayers();
}
