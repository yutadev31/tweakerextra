package com.yutadev31.tweakerextra.client;

import net.minecraft.core.BlockPos;

/** Standalone selection; it intentionally has no dependency on Litematica. */
public final class CustomSelection {
    private static BlockPos start;
    private static BlockPos end;

    private CustomSelection() {}
    public static BlockPos start() { return start; }
    public static BlockPos end() { return end; }
    public static void set(BlockPos start, BlockPos end) {
        CustomSelection.start = start; CustomSelection.end = end;
    }
    public static boolean allows(BlockPos pos) {
        if (start == null) return true;
        if (end == null) return true;
        return between(pos.getX(), start.getX(), end.getX()) && between(pos.getY(), start.getY(), end.getY()) && between(pos.getZ(), start.getZ(), end.getZ());
    }
    private static boolean between(int value, int a, int b) { return value >= Math.min(a, b) && value <= Math.max(a, b); }
}
