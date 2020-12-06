package dev.zebulon.fxcr.mixin;

import dev.zebulon.fxcr.RenderSubstitute;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {
    @Inject(method = "setWorld(Lnet/minecraft/client/world/ClientWorld;)V", at = @At("RETURN"))
    private void onSetWorld(final ClientWorld clientWorld, final CallbackInfo ci) {
        RenderSubstitute.BLOCK_VIEW.vanillaBlockView = clientWorld;
    }
}
