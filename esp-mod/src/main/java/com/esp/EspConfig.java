package com.esp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.nio.file.Files;
import java.nio.file.Path;

public class EspConfig {
    public static boolean enabled = true;
    public static boolean players = true;
    public static boolean mobs = true;
    public static boolean items = true;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static Path file() {
        return FabricLoader.getInstance().getConfigDir().resolve("esp.json");
    }

    // Colors (RGB)
    public static final int COLOR_PLAYER = 0xFF3333;
    public static final int COLOR_MOB = 0xFFAA00;
    public static final int COLOR_ITEM = 0x33DDFF;

    /** Returns true if this entity should be highlighted. */
    public static boolean matches(Entity e) {
        if (!enabled) return false;
        if (e == MinecraftClient.getInstance().player) return false;
        if (e instanceof PlayerEntity) return players;
        if (e instanceof MobEntity) return mobs;
        if (e instanceof ItemEntity) return items;
        return false;
    }

    public static int colorFor(Entity e) {
        if (e instanceof PlayerEntity) return COLOR_PLAYER;
        if (e instanceof MobEntity) return COLOR_MOB;
        return COLOR_ITEM;
    }

    private static class Data {
        boolean enabled = true, players = true, mobs = true, items = true;
    }

    public static void load() {
        try {
            if (Files.exists(file())) {
                Data d = GSON.fromJson(Files.readString(file()), Data.class);
                if (d != null) {
                    enabled = d.enabled;
                    players = d.players;
                    mobs = d.mobs;
                    items = d.items;
                }
            }
        } catch (Exception ignored) {
        }
    }

    public static void save() {
        try {
            Data d = new Data();
            d.enabled = enabled;
            d.players = players;
            d.mobs = mobs;
            d.items = items;
            Files.writeString(file(), GSON.toJson(d));
        } catch (Exception ignored) {
        }
    }
}
