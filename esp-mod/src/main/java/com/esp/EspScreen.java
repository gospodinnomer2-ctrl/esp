package com.esp;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class EspScreen extends Screen {
    private ButtonWidget bMaster, bPlayers, bMobs, bItems;

    public EspScreen() {
        super(Text.literal("ESP"));
    }

    private static Text label(String name, boolean on) {
        return Text.literal(name + ": " + (on ? "ВКЛ" : "ВЫКЛ"));
    }

    @Override
    protected void init() {
        int w = 180, h = 20, x = this.width / 2 - w / 2;
        int y = this.height / 2 - 55;

        bMaster = ButtonWidget.builder(label("ESP", EspConfig.enabled), b -> {
            EspConfig.enabled = !EspConfig.enabled;
            refresh();
        }).dimensions(x, y, w, h).build();

        bPlayers = ButtonWidget.builder(label("Игроки", EspConfig.players), b -> {
            EspConfig.players = !EspConfig.players;
            refresh();
        }).dimensions(x, y + 28, w, h).build();

        bMobs = ButtonWidget.builder(label("Мобы", EspConfig.mobs), b -> {
            EspConfig.mobs = !EspConfig.mobs;
            refresh();
        }).dimensions(x, y + 56, w, h).build();

        bItems = ButtonWidget.builder(label("Предметы", EspConfig.items), b -> {
            EspConfig.items = !EspConfig.items;
            refresh();
        }).dimensions(x, y + 84, w, h).build();

        ButtonWidget done = ButtonWidget.builder(Text.literal("Готово"), b -> this.close())
                .dimensions(x, y + 116, w, h).build();

        addDrawableChild(bMaster);
        addDrawableChild(bPlayers);
        addDrawableChild(bMobs);
        addDrawableChild(bItems);
        addDrawableChild(done);
    }

    private void refresh() {
        bMaster.setMessage(label("ESP", EspConfig.enabled));
        bPlayers.setMessage(label("Игроки", EspConfig.players));
        bMobs.setMessage(label("Мобы", EspConfig.mobs));
        bItems.setMessage(label("Предметы", EspConfig.items));
        EspConfig.save();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.height / 2 - 80, 0xFFFFFF);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
