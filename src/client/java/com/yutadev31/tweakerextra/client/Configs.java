package com.yutadev31.tweakerextra.client;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yutadev31.tweakerextra.TweakerExtra;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.data.json.JsonUtils;

/** MaLiLib-backed configuration and persistence for TweakerExtra. */
public final class Configs implements IConfigHandler {
    private static final Path FILE = FileUtils.getConfigDirectory().resolve(TweakerExtra.MOD_ID + ".json");

    public static final ConfigBooleanHotkeyed RESTRICT_BREAKING = new ConfigBooleanHotkeyed("restrictBreaking", true, "")
            .apply("tweakerextra.config");
    public static final ConfigBooleanHotkeyed RESTRICT_PLACING = new ConfigBooleanHotkeyed("restrictPlacing", true, "")
            .apply("tweakerextra.config");
    public static final ConfigBoolean SHOW_BLOCKED_MESSAGE = new ConfigBoolean("showBlockedMessage", true)
            .apply("tweakerextra.config");

    public static final List<IConfigBase> OPTIONS = List.of(
            RESTRICT_BREAKING,
            RESTRICT_PLACING,
            SHOW_BLOCKED_MESSAGE
    );

    @Override
    public void load() {
        if (!Files.isReadable(FILE)) {
            return;
        }

        JsonElement element = JsonUtils.parseJsonFile(FILE);
        if (element != null && element.isJsonObject()) {
            ConfigUtils.readConfigBase(element.getAsJsonObject(), "Generic", OPTIONS);
        }
    }

    @Override
    public void save() {
        JsonObject root = new JsonObject();
        ConfigUtils.writeConfigBase(root, "Generic", OPTIONS);
        FileUtils.createDirectoriesIfMissing(FILE.getParent());
        JsonUtils.writeJsonToFile(root, FILE);
    }

    @Override
    public void onConfigsChanged() {
        this.save();
    }
}
