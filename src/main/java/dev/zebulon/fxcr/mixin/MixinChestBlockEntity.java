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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(ChestBlockEntity.class)
public abstract class MixinChestBlockEntity extends LootableContainerBlockEntity {
    @Shadow
    @Final
    private ChestLidAnimator lidAnimator;
    private int rebuildScheduler = 0;

    protected MixinChestBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(blockEntityType, pos, state);
    }

    @Inject(method = "clientTick", at = @At("RETURN"))
    private static void clientTickHook(World world, BlockPos pos, BlockState state, ChestBlockEntity blockEntity,
            CallbackInfo ci) {
        @SuppressWarnings("all")
        MixinChestBlockEntity blockEntityMixin = (MixinChestBlockEntity) (Object) blockEntity;

        if (blockEntityMixin.rebuildScheduler > 0) {
            blockEntityMixin.rebuildScheduler--;
            if (blockEntityMixin.rebuildScheduler <= 0)
                blockEntityMixin.rebuildChunk();
        }

        MixinChestLidAnimatorExt lidAccessorExt = (MixinChestLidAnimatorExt) blockEntityMixin.lidAnimator;

        float progress = lidAccessorExt.getProgress();
        float lastProgress = lidAccessorExt.getLastProgress();

        boolean sameProgress = progress == lastProgress;

        if (sameProgress)
            return;

        float progressDelta = progress - lastProgress;

        if (progressDelta > 0 && lastProgress == 0) {
            blockEntityMixin.rebuildChunk();
        } else if (progressDelta < 0 && progress == 0) {
            blockEntityMixin.rebuildScheduler = 1;
        }
    }

    @Unique
    @SuppressWarnings("all")
    private void rebuildChunk() {
        MinecraftClient.getInstance().worldRenderer.updateBlock(world, pos, getCachedState(), getCachedState(), 1);
    }
}
