package com.yutadev31.tweakerextra.client;

import net.minecraft.client.gui.screens.Screen;
import fi.dy.masa.malilib.config.gui.GuiModConfigs;

/** The standard MaLiLib options screen for TweakerExtra. */
public final class TweakerExtraConfigGui {
    private TweakerExtraConfigGui() {}

    public static Screen create(Screen parent) {
        return new GuiModConfigs("tweakerextra", Configs.OPTIONS, "tweakerextra.gui.title.configs")
                .setParent(parent);
    }
}
