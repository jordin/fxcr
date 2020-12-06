package dev.zebulon.fxcr.mixin;

import dev.zebulon.fxcr.FxcrMod;
import dev.zebulon.fxcr.RenderSubstitute;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class MixinWorldRenderer {
    @Shadow
    protected abstract void renderLayer(final RenderLayer renderLayer, final MatrixStack matrixStack, final double d, final double e, final double f);

    @Inject(method = "renderLayer(Lnet/minecraft/client/render/RenderLayer;Lnet/minecraft/client/util/math/MatrixStack;DDD)V", at = @At("HEAD"), cancellable = true)
    private void onRenderLayer(final RenderLayer renderLayer, final MatrixStack matrixStack, final double x, final double y, final double z, final CallbackInfo ci) {
        if (FxcrMod.enabled && renderLayer == RenderLayer.getSolid()) {
            this.renderLayer(RenderSubstitute.FXCR_LAYER, matrixStack, x, y, z);
        }
    }
}
