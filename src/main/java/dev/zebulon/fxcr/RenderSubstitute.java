package dev.zebulon.fxcr;

import net.minecraft.block.*;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class RenderSubstitute {
    private final static ModelIdentifier[] MODEL_IDENTIFIER_CACHE = new ModelIdentifier[64];

    private static final int TRAPPED_CHEST_FLAG = 1 << 5;

    public static ModelIdentifier getModelIdentifier(BlockState chestState) {
        Block block = chestState.getBlock();

        Direction direction = chestState.get(Properties.HORIZONTAL_FACING);
        ChestType chestType = chestState.get(ChestBlock.CHEST_TYPE);

        int index = (direction.ordinal() << 2) | chestType.ordinal();

        // Add a trapped flag if it's a trapped chest.
        index |= block == Blocks.TRAPPED_CHEST ? TRAPPED_CHEST_FLAG : 0;

        ModelIdentifier cached = MODEL_IDENTIFIER_CACHE[index];

        if (cached == null) {
            BlockState translatedBlockState = FxcrMod.FAST_CHEST_BLOCK.getDefaultState()
                    .with(HorizontalFacingBlock.FACING, direction)
                    .with(ChestBlock.CHEST_TYPE, chestType);

            if ((index & TRAPPED_CHEST_FLAG) == 0) {
                MODEL_IDENTIFIER_CACHE[index] = BlockModels.getModelId(FxcrMod.FAST_CHEST_BLOCK_ID, translatedBlockState);
            } else {
                MODEL_IDENTIFIER_CACHE[index] = BlockModels.getModelId(FxcrMod.FAST_TRAPPED_CHEST_BLOCK_ID, translatedBlockState);
            }

            cached = MODEL_IDENTIFIER_CACHE[index];
        }

        return cached;
    }

    public static boolean isInAnimationState(BlockView blockView, BlockPos pos) {
        if (blockView.getBlockEntity(pos) instanceof ChestBlockEntity chestBlockEntity) {
            float animationProgress = chestBlockEntity.getAnimationProgress(1);

            return FxcrMod.enabled && animationProgress != 0.0f;
        }

        return false;
    }
}
