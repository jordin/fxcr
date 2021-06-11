package dev.zebulon.fxcr;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.enums.ChestType;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

public class RenderSubstitute {
    public static BlockState[] BLOCK_STATE_CACHE = new BlockState[64];

    private static final int TRAPPED_CHEST_FLAG = 1 << 5;

    public static BlockState transformBlockState(BlockState chestState) {
        Block block = chestState.getBlock();

        Direction direction = chestState.get(Properties.HORIZONTAL_FACING);
        ChestType chestType = chestState.get(ChestBlock.CHEST_TYPE);

        int index = (direction.ordinal() << 2) | chestType.ordinal();

        // Add a trapped flag if it's a trapped chest.
        index |= block == Blocks.TRAPPED_CHEST ? TRAPPED_CHEST_FLAG : 0;

        BlockState cached = BLOCK_STATE_CACHE[index];

        if (cached == null) {
            if ((index & TRAPPED_CHEST_FLAG) == 0) {
                BLOCK_STATE_CACHE[index] = FxcrMod.FAST_CHEST_BLOCK.getDefaultState()
                        .with(HorizontalFacingBlock.FACING, direction).with(ChestBlock.CHEST_TYPE, chestType);
            } else {
                BLOCK_STATE_CACHE[index] = FxcrMod.FAST_TRAPPED_CHEST_BLOCK.getDefaultState()
                        .with(HorizontalFacingBlock.FACING, direction).with(ChestBlock.CHEST_TYPE, chestType);
            }

            cached = BLOCK_STATE_CACHE[index];
        }

        return cached;
    }
}
