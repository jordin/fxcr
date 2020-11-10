package dev.zebulon.fxcr.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(WorldRenderer.class)
public interface MixinExtWorldRenderer {
    @Invoker
    void invokeRenderLayer(final RenderLayer renderLayer, final MatrixStack matrixStack, final double x, final double y,
            final double z);
}
