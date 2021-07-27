package dev.zebulon.fxcr;

import dev.zebulon.fxcr.block.FastChestBlock;
import dev.zebulon.fxcr.block.FastTrappedChestBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.Option;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class FxcrMod implements ModInitializer {
    /**
     * This is a hack so we know what chest we are currently trying to render.
     */
    public static BlockPos CURRENT_POS = BlockPos.ORIGIN;

    public static boolean enabled = true;

    public static final Identifier FAST_CHEST_BLOCK_ID = new Identifier("fxcr", "fast_chest");
    public static final FastChestBlock FAST_CHEST_BLOCK = new FastChestBlock(
            FabricBlockSettings.of(Material.WOOD).hardness(4.0f));

    public static final Identifier FAST_TRAPPED_CHEST_BLOCK_ID = new Identifier("fxcr", "fast_trapped_chest");
    public static final FastTrappedChestBlock FAST_TRAPPED_CHEST_BLOCK = new FastTrappedChestBlock(
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

    @Override
    public void onInitialize() {
    }
}