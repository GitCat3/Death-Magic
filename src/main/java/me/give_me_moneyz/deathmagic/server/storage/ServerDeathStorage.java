package me.give_me_moneyz.deathmagic.server.storage;

import me.give_me_moneyz.deathmagic.common.network.EntityDeathDataPacket;
import me.give_me_moneyz.deathmagic.common.network.MyNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashMap;

public class ServerDeathStorage extends SavedData {
    public static final String DATA_NAME = "dead_entity_data";
    private final HashMap<BlockPos, Float> deathData = new HashMap<>();

    // Load from NBT
    public static ServerDeathStorage load(CompoundTag tag) {
        ServerDeathStorage storage = new ServerDeathStorage();
        ListTag list = tag.getList("deaddata", Tag.TAG_COMPOUND);
        for (Tag item : list) {
            CompoundTag dataTag = (CompoundTag) item;
            BlockPos pos = new BlockPos(
                    dataTag.getInt("x"),
                    dataTag.getInt("y"),
                    dataTag.getInt("z")
            );
            float health = dataTag.getFloat("h");
            storage.deathData.put(pos, health);
        }
        return storage;
    }

    public static ServerDeathStorage get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                new SavedData.Factory<>(ServerDeathStorage::new, ServerDeathStorage::load),
                DATA_NAME
        );
    }

    public void addDeath(BlockPos pos, float health) {
        deathData.put(pos, health);
        setDirty(); // Marks data as needing to be saved
    }

    public void changeDeathHealth(BlockPos pos, float newHealth, ServerLevel level) {
        deathData.replace(pos, newHealth);
        if (deathData.get(pos) <= 0.1) {
            deathData.remove(pos);
            for (ServerPlayer player : level.players()) {
                MyNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new EntityDeathDataPacket(pos, true));
            }
        }
        setDirty();
    }

    public HashMap<BlockPos, Float> getDeaths() {
        return deathData;
    }

    // Save to NBT
    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag list = new ListTag();
        for (BlockPos pos : deathData.keySet()) {
            CompoundTag dataTag = new CompoundTag();
            dataTag.putInt("x", pos.getX());
            dataTag.putInt("y", pos.getY());
            dataTag.putInt("z", pos.getZ());
            dataTag.putFloat("h", deathData.get(pos));
            list.add(dataTag);
        }
        tag.put("deaddata", list);
        return tag;
    }

}