package dev.zebulon.fxcr;

import net.minecraft.block.*;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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

    public static BlockRenderType getRenderType(BlockState state, BlockPos pos) {
        if (!(state.getBlock() instanceof ChestBlock)) {
            return state.getRenderType();
        }

        assert MinecraftClient.getInstance().world != null;
        ChestBlockEntity chestEntity = (ChestBlockEntity) MinecraftClient.getInstance().world.getBlockEntity(pos);

        float animationProgress = 0;

        if (chestEntity != null) {
            animationProgress = chestEntity.getAnimationProgress(MinecraftClient.getInstance().getTickDelta());
        }

        return FxcrMod.enabled && animationProgress == 0 ? BlockRenderType.MODEL : BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
