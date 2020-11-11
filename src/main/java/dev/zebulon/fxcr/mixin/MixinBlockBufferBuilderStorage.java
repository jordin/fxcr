package dev.zebulon.fxcr.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.zebulon.fxcr.RenderSubstitute;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.chunk.BlockBufferBuilderStorage;

@Mixin(BlockBufferBuilderStorage.class)
public class MixinBlockBufferBuilderStorage {

    @Shadow
    @Final
    private Map<RenderLayer, BufferBuilder> builders;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void init(CallbackInfo callbackInfo) {
        RenderLayer renderLayer = RenderSubstitute.FXCR_LAYER;
        BufferBuilder builder = new BufferBuilder(renderLayer.getExpectedBufferSize());
        builders.put(renderLayer, builder);
    }
}
