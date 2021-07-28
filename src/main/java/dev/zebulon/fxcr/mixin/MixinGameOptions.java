package dev.zebulon.fxcr.mixin;

import io.netty.util.internal.StringUtil;
import net.minecraft.client.option.GameOptions;
import net.minecraft.nbt.NbtCompound;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import dev.zebulon.fxcr.FxcrMod;

import java.io.PrintWriter;

@Mixin(GameOptions.class)
public abstract class MixinGameOptions {
    @SuppressWarnings("unused")
    @Shadow
    static boolean isTrue(String string) {
        throw new IllegalStateException("Mixin shadow method body invoked.");
    }

    @Shadow
    protected abstract NbtCompound update(NbtCompound nbt);

    @Redirect(method = "write()V", at = @At(value = "INVOKE", target = "Ljava/io/PrintWriter;println(Ljava/lang/String;)V", ordinal = 0))
    private void onWritePrintLn(PrintWriter printWriter, String x) {
        printWriter.println("fxcrEnabled:" + FxcrMod.enabled);
        printWriter.println(x);
    }

    // FIXME: this is pointlessly called a lot of times in a loop
    @Redirect(method = "load()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;update(Lnet/minecraft/nbt/NbtCompound;)Lnet/minecraft/nbt/NbtCompound;", ordinal = 0))
    private NbtCompound onLoadGetString(GameOptions gameOptions, NbtCompound nbt) {
        NbtCompound updated = update(nbt);
        String enabledStr = updated.getString("fxcrEnabled");
        FxcrMod.enabled = StringUtil.isNullOrEmpty(enabledStr) || isTrue(enabledStr);
        return updated;
    }
}
