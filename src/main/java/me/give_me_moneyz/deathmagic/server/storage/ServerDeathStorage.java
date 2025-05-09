package me.give_me_moneyz.deathmagic.server.storage;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import java.util.ArrayList;
import java.util.List;

public class ServerDeathStorage extends SavedData {
    private final List<BlockPos> deathPositions = new ArrayList<>();

    public static final String DATA_NAME = "dead_entity_positions";

    public void addDeath(BlockPos pos) {
        deathPositions.add(pos);
        setDirty(); // Marks data as needing to be saved
    }

    public List<BlockPos> getDeathPositions() {
        return deathPositions;
    }

    // Save to NBT
    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag list = new ListTag();
        for (BlockPos pos : deathPositions) {
            CompoundTag posTag = new CompoundTag();
            posTag.putInt("x", pos.getX());
            posTag.putInt("y", pos.getY());
            posTag.putInt("z", pos.getZ());
            list.add(posTag);
        }
        tag.put("positions", list);
        return tag;
    }

    // Load from NBT
    public static ServerDeathStorage load(CompoundTag tag) {
        ServerDeathStorage storage = new ServerDeathStorage();
        ListTag list = tag.getList("positions", Tag.TAG_COMPOUND);
        for (Tag item : list) {
            CompoundTag posTag = (CompoundTag) item;
            BlockPos pos = new BlockPos(
                    posTag.getInt("x"),
                    posTag.getInt("y"),
                    posTag.getInt("z")
            );
            storage.deathPositions.add(pos);
        }
        return storage;
    }

    public static ServerDeathStorage get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                new SavedData.Factory<>(ServerDeathStorage::new, ServerDeathStorage::load),
                DATA_NAME
        );
    }

}