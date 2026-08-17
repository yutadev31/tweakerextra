package com.yutadev31.tweakerextra.client;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

/** Direct numeric editor for the standalone cuboid selection. */
public final class SelectionEditorScreen extends Screen {
    private final EditBox[] fields = new EditBox[6];
    public SelectionEditorScreen() { super(Component.translatable("screen.tweakerextra.selection_editor")); }
    @Override protected void init() {
        BlockPos a = CustomSelection.start() == null ? BlockPos.ZERO : CustomSelection.start();
        BlockPos b = CustomSelection.end() == null ? a : CustomSelection.end();
        int[] values = {a.getX(), a.getY(), a.getZ(), b.getX(), b.getY(), b.getZ()};
        String[] labels = {"始点 X", "始点 Y", "始点 Z", "終点 X", "終点 Y", "終点 Z"};
        int x = width / 2 - 102, y = height / 2 - 58;
        for (int i = 0; i < fields.length; i++) {
            int column = i % 3;
            int row = i / 3;
            int fieldX = x + column * 68;
            int fieldY = y + row * 52 + 14;
            addRenderableWidget(new StringWidget(fieldX, fieldY - 12, Component.literal(labels[i]), font));
            fields[i] = addRenderableWidget(new EditBox(font, fieldX, fieldY, 64, 20, Component.literal(labels[i])));
            fields[i].setValue(Integer.toString(values[i]));
            fields[i].setResponder(value -> applyValues());
        }
        addRenderableWidget(Button.builder(Component.literal("現在地を始点に設定"), btt -> setCurrentPosition(0)).bounds(x, y + 108, 100, 20).build());
        addRenderableWidget(Button.builder(Component.literal("現在地を終点に設定"), btt -> setCurrentPosition(3)).bounds(x + 102, y + 108, 100, 20).build());
    }
    private void setCurrentPosition(int offset) { if (Minecraft.getInstance().player != null) { BlockPos p = Minecraft.getInstance().player.blockPosition(); fields[offset].setValue(Integer.toString(p.getX())); fields[offset + 1].setValue(Integer.toString(p.getY())); fields[offset + 2].setValue(Integer.toString(p.getZ())); } }
    private void applyValues() {
        try { int[] v = new int[6]; for (int i = 0; i < 6; i++) v[i] = Integer.parseInt(fields[i].getValue()); CustomSelection.set(new BlockPos(v[0], v[1], v[2]), new BlockPos(v[3], v[4], v[5])); } catch (NumberFormatException ignored) {}
    }
}
