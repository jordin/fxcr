package dev.zebulon.fxcr.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(ChestBlockEntityRenderer.class)
public class MixinChestBlockEntityRenderer {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public <T extends BlockEntity & ChestAnimationProgress> void render(T entity, float tickDelta, MatrixStack matrices,
            VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo callbackInfo) {
        if (entity.hasWorld() && entity.getType() == BlockEntityType.CHEST) {
            callbackInfo.cancel();
        }
    }
}
