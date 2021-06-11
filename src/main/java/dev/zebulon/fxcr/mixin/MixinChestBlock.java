package dev.zebulon.fxcr.mixin;

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
        assert MinecraftClient.getInstance().world != null;
        ChestBlockEntity chestEntity = (ChestBlockEntity) MinecraftClient.getInstance().world.getBlockEntity(FxcrMod.CURRENT_POS);

        float animationProgress = 0;

        if (chestEntity != null) {
            animationProgress = chestEntity.getAnimationProgress(MinecraftClient.getInstance().getTickDelta());
        }

        callbackInfo.setReturnValue(FxcrMod.enabled && animationProgress == 0 ? BlockRenderType.MODEL : BlockRenderType.ENTITYBLOCK_ANIMATED);
        callbackInfo.cancel();
    }
}
