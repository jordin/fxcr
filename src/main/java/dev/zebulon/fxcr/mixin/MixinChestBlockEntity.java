package dev.zebulon.fxcr.mixin;

import net.minecraft.block.Block;
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
    @Shadow
    @Final
    private ChestLidAnimator lidAnimator;

    @SuppressWarnings("unused")
    protected MixinChestBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(blockEntityType, pos, state);
    }

    @SuppressWarnings("all")
    @Inject(method = "clientTick", at = @At("RETURN"))
    private static void clientTickHook(World world, BlockPos pos, BlockState state, ChestBlockEntity blockEntity, CallbackInfo ci) {
        MixinChestBlockEntity blockEntityMixin = (MixinChestBlockEntity) (Object) blockEntity;
        MixinChestLidAnimatorExt lidAccessorExt = (MixinChestLidAnimatorExt) blockEntityMixin.lidAnimator;

        WorldRenderer worldRenderer = MinecraftClient.getInstance().worldRenderer;
        BlockState cachedBlockState = blockEntityMixin.getCachedState();

        float progress = lidAccessorExt.getProgress();
        float lastProgress = lidAccessorExt.getLastProgress();

        // Hacky way to force minecraft to rebuild the chunk mesh, so we can re-add our fake chest block model.

        boolean lidOpening = lastProgress > progress && progress == 0;
        boolean lidClosing = lastProgress < progress && lastProgress == 0;

        boolean lidTransitioning = lidOpening || lidClosing;

        if (lidTransitioning) {
            worldRenderer.updateBlock(world, pos, cachedBlockState, cachedBlockState, Block.REDRAW_ON_MAIN_THREAD);
        }
    }
}
