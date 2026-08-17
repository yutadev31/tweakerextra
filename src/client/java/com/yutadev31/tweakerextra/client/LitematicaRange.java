package com.yutadev31.tweakerextra.client;

import net.minecraft.core.BlockPos;
import fi.dy.masa.litematica.data.DataManager;
import fi.dy.masa.litematica.selection.AreaSelection;
import fi.dy.masa.litematica.selection.Box;

/** Reads the live Litematica area selection; no separate selection is stored here. */
public final class LitematicaRange {
    private LitematicaRange() {}

    /**
     * Returns true when the position belongs to any complete box in Litematica's current selection.
     * With no complete selection, restrictions deliberately stay inactive to avoid trapping the player.
     */
    public static boolean allows(BlockPos pos) {
        AreaSelection selection = DataManager.getSelectionManager().getCurrentSelection();
        if (selection == null) {
            return true;
        }

        boolean hasCompleteBox = false;
        for (Box box : selection.getAllSubRegionBoxes()) {
            if (contains(box, pos)) {
                return true;
            }
            hasCompleteBox |= box.getPos1() != null && box.getPos2() != null;
        }

        return !hasCompleteBox;
    }

    private static boolean contains(Box box, BlockPos pos) {
        BlockPos first = box.getPos1();
        BlockPos second = box.getPos2();
        if (first == null || second == null) {
            return false;
        }

        return between(pos.getX(), first.getX(), second.getX())
                && between(pos.getY(), first.getY(), second.getY())
                && between(pos.getZ(), first.getZ(), second.getZ());
    }

    private static boolean between(int value, int first, int second) {
        return value >= Math.min(first, second) && value <= Math.max(first, second);
    }
}
