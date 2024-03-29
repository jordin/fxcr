package dev.zebulon.fxcr.screen;

import dev.zebulon.fxcr.FxcrMod;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class FxcrConfigScreen extends Screen {
    public FxcrConfigScreen(Text title) {
        super(title);
    }

    @Override
    @SuppressWarnings("all")
    protected void init() {
        super.init();

        ClickableWidget widget = FxcrMod.FXCR_ENABLED_OPTION.createButton(this.client.options, 10, 10, 100);

        this.addDrawableChild(widget);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);

        super.render(matrices, mouseX, mouseY, delta);
    }
}
