package dev.zebulon.fxcr;

import dev.zebulon.fxcr.block.FastChestBlock;
import dev.zebulon.fxcr.block.FastTrappedChestBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.CyclingOption;
import net.minecraft.client.options.Option;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FxcrMod implements ModInitializer {
    public static boolean ENABLED = System.getProperty("FXCR.DISABLED") == null;

    // Eclipse shows resource leak warnings on all calls for getInstance, ugh
    @SuppressWarnings("all")
    public static final Option FXCR_ENABLED_OPTION = CyclingOption.create("fxcr.enabled", (gameOptions) -> {
        return ENABLED;
     }, (gameOptions, option, boolean_) -> {
        ENABLED = !ENABLED;

        if (MinecraftClient.getInstance().world == null) {
            return;
        }

        MinecraftClient.getInstance().worldRenderer.reload();
     });

    public static final FastChestBlock FAST_CHEST_BLOCK = new FastChestBlock(FabricBlockSettings.of(Material.WOOD).hardness(4.0f));
    public static final FastTrappedChestBlock FAST_TRAPPED_CHEST_BLOCK = new FastTrappedChestBlock(FabricBlockSettings.of(Material.WOOD).hardness(4.0f));

    @Override
    public void onInitialize() {
        Registry.register(Registry.BLOCK, new Identifier("fxcr", "fast_chest"), FAST_CHEST_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier("fxcr", "fast_trapped_chest"), FAST_TRAPPED_CHEST_BLOCK);
    }
}