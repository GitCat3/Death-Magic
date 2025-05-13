package me.give_me_moneyz.deathmagic.client.network;

import net.minecraft.core.BlockPos;
import java.util.ArrayList;
import java.util.List;

public class ClientDeadEntityTracker {
    private static final List<BlockPos> deathPositions = new ArrayList<>();

    public static void modifyDeathPositions(BlockPos pos, boolean isRemoving) {
        if (isRemoving) {
            deathPositions.remove(pos);
        }
        else {
            deathPositions.add(pos);
        }
    }

    public static List<BlockPos> getDeathPositions() {
        return deathPositions;
    }
}

