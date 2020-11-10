package dev.zebulon.fxcr;

import dev.zebulon.fxcr.block.FastChestBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FxcrMod implements ModInitializer {

	public static final FastChestBlock FAST_CHEST_BLOCK = new FastChestBlock(FabricBlockSettings.of(Material.WOOD).hardness(4.0f));

	@Override
	public void onInitialize() {
		Registry.register(Registry.BLOCK, new Identifier("fxcr", "fast_chest"), FAST_CHEST_BLOCK);
	}
}