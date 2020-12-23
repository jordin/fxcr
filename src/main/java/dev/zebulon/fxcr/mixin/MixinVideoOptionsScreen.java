package dev.zebulon.fxcr.mixin;

import dev.zebulon.fxcr.FxcrMod;
import net.minecraft.client.gui.screen.VideoOptionsScreen;
import net.minecraft.client.option.Option;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(VideoOptionsScreen.class)
public abstract class MixinVideoOptionsScreen {
    @Mutable
    @Final
    @Shadow
    private static Option[] OPTIONS;

    static {
        Option[] oldOptions = OPTIONS;
        OPTIONS = new Option[oldOptions.length + 1];

        System.arraycopy(oldOptions, 0, OPTIONS, 0, oldOptions.length);

        OPTIONS[OPTIONS.length - 1] = FxcrMod.FXCR_ENABLED_OPTION;
    }
}
