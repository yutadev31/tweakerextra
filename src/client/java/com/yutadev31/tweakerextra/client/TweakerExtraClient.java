package com.yutadev31.tweakerextra.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InputEventHandler;

public class TweakerExtraClient implements ClientModInitializer {
	private static final Configs CONFIG_HANDLER = new Configs();
	private static final KeyMapping OPEN_CONFIG = KeyMappingHelper.registerKeyMapping(new KeyMapping(
			"key.tweakerextra.open_config", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, KeyMapping.Category.MISC));

	@Override
	public void onInitializeClient() {
		ConfigManager.getInstance().registerConfigHandler("tweakerextra", CONFIG_HANDLER);
		CONFIG_HANDLER.load();
		InputEventHandler.getKeybindManager().registerKeybindProvider(TweakerExtraHotkeys.INSTANCE);
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (OPEN_CONFIG.consumeClick()) {
				client.setScreenAndShow(TweakerExtraConfigGui.create(null));
			}
		});
	}
}
