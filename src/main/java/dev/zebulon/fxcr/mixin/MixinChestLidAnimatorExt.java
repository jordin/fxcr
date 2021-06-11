package dev.zebulon.fxcr.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.block.entity.ChestLidAnimator;

@Mixin(ChestLidAnimator.class)
public interface MixinChestLidAnimatorExt {
    @Accessor
    float getProgress();

    @Accessor
    float getLastProgress();
}
