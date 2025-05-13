package me.give_me_moneyz.deathmagic.common.network;

import me.give_me_moneyz.deathmagic.client.network.ClientDeadEntityTracker;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;

public class EntityDeathDataPacket {
    private final BlockPos position;
    private final boolean isRemoving;

    public EntityDeathDataPacket(BlockPos position, boolean isRemoving) {
        this.position = position;
        this.isRemoving = isRemoving;
    }

    public static void encode(EntityDeathDataPacket pkt, FriendlyByteBuf buf) {
        buf.writeBlockPos(pkt.position);
        buf.writeBoolean(pkt.isRemoving);
    }

    public static EntityDeathDataPacket decode(FriendlyByteBuf buf) {
        return new EntityDeathDataPacket(buf.readBlockPos(), buf.readBoolean());
    }

    public static void handle(EntityDeathDataPacket pkt, NetworkEvent.Context ctx) {
        ctx.enqueueWork(() -> ClientDeadEntityTracker.modifyDeathPositions(pkt.position, pkt.isRemoving));
        ctx.setPacketHandled(true);
    }
}
