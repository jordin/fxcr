package dev.zebulon.fxcr.mixin;

import dev.zebulon.fxcr.FxcrMod;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ModelLoader.class)
public abstract class MixinModelLoader {
    @Shadow
    protected abstract void addModel(ModelIdentifier modelId);

    @SuppressWarnings("unchecked")
    @Redirect(method = "loadModel(Lnet/minecraft/util/Identifier;)V",
            at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;", ordinal = 0))
    private <T> T redirectGet(Map<Identifier, T> map, Object key) {
        if (key.equals(FxcrMod.FAST_CHEST_BLOCK_ID) || key.equals(FxcrMod.FAST_TRAPPED_CHEST_BLOCK_ID)) {
            return (T) FxcrMod.FAST_CHEST_BLOCK.getStateManager();
        }

        return map.get(key);
    }

    @Inject(method = "<init>(Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/client/color/block/BlockColors;Lnet/minecraft/util/profiler/Profiler;I)V",
            at = @At(value = "INVOKE", target = "Ljava/util/Map;forEach(Ljava/util/function/BiConsumer;)V"))
    private void onInit(ResourceManager resourceManager, BlockColors blockColors, Profiler profiler, int mipmapLevel, CallbackInfo ci) {
        FxcrMod.FAST_CHEST_BLOCK.getStateManager().getStates().forEach((blockState) -> {
            this.addModel(BlockModels.getModelId(FxcrMod.FAST_CHEST_BLOCK_ID, blockState));
            this.addModel(BlockModels.getModelId(FxcrMod.FAST_TRAPPED_CHEST_BLOCK_ID, blockState));
        });
    }
}
