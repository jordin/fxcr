package dev.zebulon.fxcr.mixin;

import dev.zebulon.fxcr.model.ConditionalBlockModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {
    @Inject(method = "setWorld(Lnet/minecraft/client/world/ClientWorld;)V", at = @At("RETURN"))
    private void onSetWorld(ClientWorld clientWorld, CallbackInfo ci) {
        ConditionalBlockModel.clientWorld = clientWorld;
    }
}
