package dev.zebulon.fxcr.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.zebulon.fxcr.FxcrMod;
import dev.zebulon.fxcr.RenderSubstitute;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "renderLayer(Lnet/minecraft/client/render/RenderLayer;Lnet/minecraft/client/util/math/MatrixStack;DDD)V", at = @At("HEAD"), cancellable = true)
    private void onRenderLayer(final RenderLayer renderLayer, final MatrixStack matrixStack, final double x, final double y, final double z, final CallbackInfo ci) {
        if (FxcrMod.ENABLED && renderLayer == RenderLayer.getSolid()) {
            ((MixinExtWorldRenderer) client.worldRenderer).invokeRenderLayer(RenderSubstitute.FXCR_LAYER, matrixStack, x, y, z);
        }
    }
}
