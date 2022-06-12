package dev.zebulon.fxcr;

import dev.zebulon.fxcr.block.FastChestBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FxcrMod {
    public static boolean enabled = true;

    public static final Identifier FAST_CHEST_BLOCK_ID = new Identifier("fxcr", "fast_chest");
    public static final Identifier FAST_TRAPPED_CHEST_BLOCK_ID = new Identifier("fxcr", "fast_trapped_chest");

    public static final FastChestBlock FAST_CHEST_BLOCK = Registry.register(
            Registry.BLOCK,
            FAST_CHEST_BLOCK_ID,
            new FastChestBlock(FabricBlockSettings.of(Material.WOOD).hardness(4.0f))
    );

    // Eclipse shows resource leak warnings on all calls for getInstance, ugh
    @SuppressWarnings("all")
    public static final SimpleOption<Boolean> FXCR_ENABLED_OPTION = SimpleOption.ofBoolean("fxcr.enabled", true, (value) -> {
        enabled = value;

        if (MinecraftClient.getInstance().world == null) {
            return;
        }

        // should we just mark the chunks as dirty?
        MinecraftClient.getInstance().worldRenderer.reload();
    });
}