package dev.zebulon.fxcr.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(BlockEntityRenderDispatcher.class)
public class MixinBlockEntityRenderDispatcher {
    @Inject(method = "Lnet/minecraft/client/render/block/entity/BlockEntityRenderDispatcher;renderEntity(Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)Z", at = @At("HEAD"), cancellable = true)
    public <E extends BlockEntity> void renderEntity(E entity, MatrixStack matrix,
            VertexConsumerProvider vertexConsumerProvider, int light, int overlay, CallbackInfoReturnable<Boolean> callbackInfo) {
        if (entity.hasWorld() && entity.getType() == BlockEntityType.CHEST) {
            callbackInfo.setReturnValue(true);
        }
    }
}
