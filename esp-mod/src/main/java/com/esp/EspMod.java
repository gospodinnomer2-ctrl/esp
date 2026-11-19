package com.esp;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class EspMod implements ClientModInitializer {
    private static KeyBinding menuKey;

    @Override
    public void onInitializeClient() {
        EspConfig.load();

        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.espmod.menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_0,
                "category.espmod"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (menuKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new EspScreen());
                }
            }
        });
    }
}
