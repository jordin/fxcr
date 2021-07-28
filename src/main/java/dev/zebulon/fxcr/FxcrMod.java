package dev.zebulon.fxcr;

import dev.zebulon.fxcr.block.FastChestBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.Option;
import net.minecraft.util.Identifier;

public class FxcrMod {
    public static boolean enabled = true;

    public static final Identifier FAST_CHEST_BLOCK_ID = new Identifier("fxcr", "fast_chest");
    public static final Identifier FAST_TRAPPED_CHEST_BLOCK_ID = new Identifier("fxcr", "fast_trapped_chest");
    public static final FastChestBlock FAST_CHEST_BLOCK = new FastChestBlock(
            FabricBlockSettings.of(Material.WOOD).hardness(4.0f));

    // Eclipse shows resource leak warnings on all calls for getInstance, ugh
    @SuppressWarnings("all")
    public static final Option FXCR_ENABLED_OPTION = CyclingOption.create("fxcr.enabled", (gameOptions) -> {
        return enabled;
    }, (gameOptions, option, boolean_) -> {
        enabled = !enabled;

        if (MinecraftClient.getInstance().world == null) {
            return;
        }

        // should we just mark the chunks as dirty?
        MinecraftClient.getInstance().worldRenderer.reload();
    });
}