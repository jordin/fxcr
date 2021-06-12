package dev.zebulon.fxcr.mixin.sodium;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(method = "tick()V", at = @At("HEAD"), cancellable = true)
    private void onTick(final CallbackInfo ci) {
//        System.out.println("hi");
    }

}
