package dev.zebulon.fxcr.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.zebulon.fxcr.RenderSubstitute;
import net.minecraft.client.render.RenderLayer;

@Mixin(RenderLayer.class)
public class MixinRenderLayer {
    @Inject(method = "getBlockLayers()Ljava/util/List;", at = @At("HEAD"), cancellable = true)
    private static void onGetBlockLayers(final CallbackInfoReturnable<List<RenderLayer>> cir) {
        cir.setReturnValue(RenderSubstitute.BLOCK_LAYERS);
    }
}
