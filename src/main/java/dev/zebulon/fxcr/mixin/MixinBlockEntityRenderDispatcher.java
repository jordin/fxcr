package dev.zebulon.fxcr.mixin;

import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityRenderDispatcher.class)
public class MixinBlockEntityRenderDispatcher {
    @Inject(method = "get(Lnet/minecraft/block/entity/BlockEntity;)Lnet/minecraft/client/render/block/entity/BlockEntityRenderer;", at = @At("HEAD"), cancellable = true)
    public <E extends BlockEntity> void renderEntity(E blockEntity, CallbackInfoReturnable<@Nullable BlockEntityRenderer<E>> cir) {
        if (blockEntity.hasWorld() && blockEntity.getType() == BlockEntityType.CHEST) {
            cir.setReturnValue(null);
        }
    }
}
