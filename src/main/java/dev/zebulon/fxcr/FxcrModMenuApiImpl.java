package dev.zebulon.fxcr;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.zebulon.fxcr.screen.FxcrConfigScreen;
import net.minecraft.text.Text;

public class FxcrModMenuApiImpl implements ModMenuApi  {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (screen) -> new FxcrConfigScreen(Text.of("FXCR"));
    }
}
