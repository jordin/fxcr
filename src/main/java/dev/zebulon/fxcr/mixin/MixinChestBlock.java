package dev.zebulon.fxcr.mixin;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.MinecraftClient;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.zebulon.fxcr.FxcrMod;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;

@Mixin(ChestBlock.class)
@SuppressWarnings("all")
public class MixinChestBlock {
    @Inject(method = "getRenderType", at = @At("HEAD"), cancellable = true)
    public void getRenderType(BlockState state, CallbackInfoReturnable<BlockRenderType> callbackInfo) {
        callbackInfo.setReturnValue(BlockRenderType.MODEL);
        callbackInfo.cancel();
    }
}
