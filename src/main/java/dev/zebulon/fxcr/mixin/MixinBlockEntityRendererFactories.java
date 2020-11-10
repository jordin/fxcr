package dev.zebulon.fxcr.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;

@Mixin(BlockEntityRendererFactories.class)
public class MixinBlockEntityRendererFactories {
    @Inject(method = "register(Lnet/minecraft/block/entity/BlockEntityType;Lnet/minecraft/client/render/block/entity/BlockEntityRendererFactory;)V", at = @At("HEAD"), cancellable = true)
    private static <T extends BlockEntity> void register(BlockEntityType<? extends T> type,
            BlockEntityRendererFactory<T> factory, CallbackInfo callbackInfo) {
        if (type == BlockEntityType.CHEST) {
            callbackInfo.cancel();
        }
    }
}
