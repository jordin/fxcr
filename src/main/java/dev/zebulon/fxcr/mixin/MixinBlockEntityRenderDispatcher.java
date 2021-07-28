package dev.zebulon.fxcr.mixin;

import dev.zebulon.fxcr.FxcrMod;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityRenderDispatcher.class)
public abstract class MixinBlockEntityRenderDispatcher {
    @Inject(method = "get(Lnet/minecraft/block/entity/BlockEntity;)Lnet/minecraft/client/render/block/entity/BlockEntityRenderer;", at = @At("HEAD"), cancellable = true)
    public <E extends BlockEntity> void renderEntity(E blockEntity, CallbackInfoReturnable<@Nullable BlockEntityRenderer<E>> cir) {
        BlockEntityType<?> type = blockEntity.getType();
        boolean invalidType = type == BlockEntityType.CHEST || type == BlockEntityType.TRAPPED_CHEST;
        
        if (FxcrMod.enabled && blockEntity.hasWorld() && invalidType) {
            ChestBlockEntity chestEntity = (ChestBlockEntity) blockEntity;
            float animationProgress = chestEntity.getAnimationProgress(MinecraftClient.getInstance().getTickDelta());

            if (animationProgress == 0.0f) {
                cir.setReturnValue(null);
            }
        }
    }
}
