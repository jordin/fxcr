package dev.zebulon.fxcr;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.LightType;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.chunk.light.LightingProvider;
import net.minecraft.world.level.ColorResolver;

public class FxcrBlockView implements BlockRenderView {
    private static final BlockState NOT_AIR = Blocks.STONE.getDefaultState();

    public BlockRenderView vanillaBlockView;

    @Override
    public BlockState getBlockState(BlockPos blockPos) {
        BlockState state = this.vanillaBlockView.getBlockState(blockPos);

        if (state.getBlock() == Blocks.CHEST) {
            return NOT_AIR;
        }

        return state;
    }

    @Override
    public BlockEntity getBlockEntity(BlockPos pos) {
        return this.vanillaBlockView.getBlockEntity(pos);
    }

    @Override
    public FluidState getFluidState(BlockPos pos) {
        return this.vanillaBlockView.getFluidState(pos);
    }

    @Override
    public int getLuminance(BlockPos pos) {
        return this.vanillaBlockView.getLuminance(pos);
    }

    @Override
    public int getMaxLightLevel() {
        return this.vanillaBlockView.getMaxLightLevel();
    }

    @Override
    public int getSectionCount() {
        return this.vanillaBlockView.getSectionCount();
    }

    @Override
    public int getBottomSectionLimit() {
        return this.vanillaBlockView.getBottomSectionLimit();
    }

    @Override
    public int getHeight() {
        return this.vanillaBlockView.getHeight();
    }

    @Override
    public BlockHitResult raycast(RaycastContext context) {
        return this.vanillaBlockView.raycast(context);
    }

    @Nullable
    @Override
    public BlockHitResult raycastBlock(Vec3d start, Vec3d end, BlockPos pos, VoxelShape shape, BlockState state) {
        return this.vanillaBlockView.raycastBlock(start, end, pos, shape, state);
    }

    @Override
    public float getBrightness(Direction direction, boolean shaded) {
        return this.vanillaBlockView.getBrightness(direction, shaded);
    }

    @Override
    public LightingProvider getLightingProvider() {
        return this.vanillaBlockView.getLightingProvider();
    }

    @Override
    public int getColor(BlockPos pos, ColorResolver resolver) {
        return this.vanillaBlockView.getColor(pos, resolver);
    }

    @Override
    public int getLightLevel(LightType type, BlockPos pos) {
        return this.vanillaBlockView.getLightLevel(type, pos);
    }

    @Override
    public int getBaseLightLevel(BlockPos pos, int ambientDarkness) {
        return this.vanillaBlockView.getBaseLightLevel(pos, ambientDarkness);
    }

    @Override
    public boolean isSkyVisible(BlockPos pos) {
        return this.vanillaBlockView.isSkyVisible(pos);
    }
}