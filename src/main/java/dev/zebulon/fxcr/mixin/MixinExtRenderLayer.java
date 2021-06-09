package dev.zebulon.fxcr.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexFormat;

@SuppressWarnings("BooleanParameter")
@FunctionalInterface
@Mixin(RenderLayer.class)
public interface MixinExtRenderLayer {
    @Accessor
    boolean isTranslucent();

    /*
     * @Invoker("of") static RenderLayer.MultiPhase invokeOfEnergetic(String name,
     * VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int
     * expectedBufferSize, RenderLayer.MultiPhaseParameters phaseData) { throw new
     * IllegalStateException("Mixin accessor method body invoked.");g }
     */

    @SuppressWarnings("unused")
    @Invoker("of")
    static RenderLayer.MultiPhase invokeOfFxcr(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode,
            int expectedBufferSize, boolean hasCrumbling, boolean translucent,
            RenderLayer.MultiPhaseParameters phases) {
        throw new IllegalStateException("Mixin accessor method body invoked.");
    }
}
