package dev.zebulon.fxcr.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.ChestLidAnimator;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(ChestBlockEntity.class)
public abstract class MixinChestBlockEntity extends LootableContainerBlockEntity {
    /**
     * A flag to inform the world renderer that it is important that the chunk mesh is recreated immediately.
     */
    private static final int FLAG_IMPORTANT = 0x1;

    @Shadow
    @Final
    private ChestLidAnimator lidAnimator;

    @Unique
    private boolean wasPreviouslyAnimating = false;

    @SuppressWarnings("unused")
    protected MixinChestBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(blockEntityType, pos, state);
    }

    @SuppressWarnings("all")
    @Inject(method = "clientTick", at = @At("RETURN"))
    private static void clientTickHook(World world, BlockPos pos, BlockState state, ChestBlockEntity blockEntity,
            CallbackInfo ci) {
        MixinChestBlockEntity blockEntityMixin = (MixinChestBlockEntity) (Object) blockEntity;
        MixinChestLidAnimatorExt lidAccessorExt = (MixinChestLidAnimatorExt) blockEntityMixin.lidAnimator;

        WorldRenderer worldRenderer = MinecraftClient.getInstance().worldRenderer;
        BlockState cachedBlockState = blockEntityMixin.getCachedState();

        float progress = lidAccessorExt.getProgress();
        float lastProgress = lidAccessorExt.getLastProgress();
        float progressDelta = progress - lastProgress;

        // Hacky way to force minecraft to rebuild the chunk mesh, so we can re-add our fake chest
        // block model.
        if (blockEntityMixin.wasPreviouslyAnimating && progress == 0) {
            worldRenderer.updateBlock(world, pos, cachedBlockState, cachedBlockState, FLAG_IMPORTANT);
            blockEntityMixin.wasPreviouslyAnimating = false;
        }
        
        if (progress == lastProgress) {
            return;
        }
        
        // Flag the chest as previously animation so we don't rebuild the chunk mesh multiple times.
        blockEntityMixin.wasPreviouslyAnimating = true;

        if (progressDelta > 0 && lastProgress == 0) {
            worldRenderer.updateBlock(world, pos, cachedBlockState, cachedBlockState, FLAG_IMPORTANT);
        }
    }
}
