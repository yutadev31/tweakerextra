package com.yutadev31.tweakerextra.client;

import java.util.List;

import fi.dy.masa.malilib.hotkeys.IKeybindManager;
import fi.dy.masa.malilib.hotkeys.IKeybindProvider;

/** Registers the hotkeys that appear beside the corresponding MaLiLib toggle buttons. */
public final class TweakerExtraHotkeys implements IKeybindProvider {
    public static final TweakerExtraHotkeys INSTANCE = new TweakerExtraHotkeys();

    private TweakerExtraHotkeys() {}

    @Override
    public void addKeysToMap(IKeybindManager manager) {
        manager.addKeybindToMap(Configs.RESTRICT_BREAKING.getKeybind());
        manager.addKeybindToMap(Configs.RESTRICT_PLACING.getKeybind());
    }

    @Override
    public void addHotkeys(IKeybindManager manager) {
        manager.addHotkeysForCategory("tweakerextra", "tweakerextra.hotkeys.category", List.of(
                Configs.RESTRICT_BREAKING,
                Configs.RESTRICT_PLACING
        ));
    }
}
