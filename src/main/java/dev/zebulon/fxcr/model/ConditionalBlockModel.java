package dev.zebulon.fxcr.model;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;

import dev.zebulon.fxcr.RenderSubstitute;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;

public class ConditionalBlockModel implements BakedModel, FabricBakedModel {

    /**
     * The current information about the model being rendered.
     */
    public static ThreadLocal<Pair<BlockView, BlockPos>> CURRENT_MODEL_INFO_THREAD_LOCAL = new ThreadLocal<>();

    /**.
     * The original block model we will use when we are not replacing it a FXCR model. In the
     * chest's case it will render as invisible.
     */
    private FabricBakedModel original;

    /**
     * The model FXCR uses for optimized rendering.
     */
    private FabricBakedModel replacement;

    public ConditionalBlockModel(FabricBakedModel original, FabricBakedModel replacement) {
        this.original = original;
        this.replacement = replacement;
    }

    @Override
    public boolean isVanillaAdapter() {
        return true;
    }

    /**
     * When we have an implementation of the Fabric rendering api active we can easily just
     * condtionally swap the models here.
     */
    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos,
            Supplier<Random> randomSupplier, RenderContext context) {
        if (RenderSubstitute.isInAnimationState(blockView, pos)) {
            this.original.emitBlockQuads(blockView, state, pos, randomSupplier, context);
        } else {
            this.replacement.emitBlockQuads(blockView, state, pos, randomSupplier, context);
        }
    }

    /**
     * If we aren't using the fabric rendering api (like with sodium) we have to do this the hacky
     * way. We use a thread local to get the block view and position of the current block being
     * rendered and swap the models accordingly.
     */
    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction face, Random random) {
        Pair<BlockView, BlockPos> info = CURRENT_MODEL_INFO_THREAD_LOCAL.get();

        // TODO: Figure out why this is ever true. (Data race???)
        if (info == null) {
            return ImmutableList.of();
        }

        if (RenderSubstitute.isInAnimationState(info.getLeft(), info.getRight())) {
            return ((BakedModel) this.original).getQuads(state, face, random);
        } else {
            return ((BakedModel) this.replacement).getQuads(state, face, random);
        }
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
    }

    @Override
    public boolean useAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean hasDepth() {
        return false;
    }

    @Override
    public boolean isSideLit() {
        return true;
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }

    @Override
    public Sprite getSprite() {
        return ((BakedModel) original).getSprite();
    }

    @Override
    public ModelTransformation getTransformation() {
        return ModelTransformation.NONE;
    }

    @Override
    public ModelOverrideList getOverrides() {
        return ModelOverrideList.EMPTY;
    }

}
