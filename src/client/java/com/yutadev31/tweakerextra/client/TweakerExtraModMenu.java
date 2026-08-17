package com.yutadev31.tweakerextra.client;

import com.terraformersmc.modmenu.api.ModMenuApi;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;

/** Exposes TweakerExtra's own settings screen through Mod Menu. */
public final class TweakerExtraModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return TweakerExtraConfigGui::create;
    }
}
