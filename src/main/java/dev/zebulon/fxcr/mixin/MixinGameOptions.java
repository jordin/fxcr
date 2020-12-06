package dev.zebulon.fxcr.mixin;

import java.io.PrintWriter;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import dev.zebulon.fxcr.FxcrMod;
import net.minecraft.client.options.GameOptions;
import net.minecraft.nbt.CompoundTag;

@Mixin(GameOptions.class)
public abstract class MixinGameOptions {
    @Shadow
    private static boolean isTrue(String string) {
        throw new IllegalStateException("Mixin shadow method body invoked.");
    }

    @Redirect(method = "write()V", at = @At(value = "INVOKE", target = "Ljava/io/PrintWriter;println(Ljava/lang/String;)V", ordinal = 0))
    private void onWritePrintLn(PrintWriter printWriter, String x) {
        printWriter.println("fxcrEnabled:" + FxcrMod.enabled);
        printWriter.println(x);
    }

    @Redirect(method = "load()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;getString(Ljava/lang/String;)Ljava/lang/String;", ordinal = 1))
    private String onLoadGetString(CompoundTag compoundTag, String key) {
        FxcrMod.enabled = isTrue(compoundTag.getString("fxcrEnabled"));

        return compoundTag.getString(key);
    }
}
