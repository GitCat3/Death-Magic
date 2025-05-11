package me.give_me_moneyz.deathmagic.common.network;

import me.give_me_moneyz.deathmagic.client.network.ClientDeadEntityTracker;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;

public class EntityDeathDataPacket {
    private final BlockPos position;

    public EntityDeathDataPacket(BlockPos position) {
        this.position = position;
    }

    public static void encode(EntityDeathDataPacket pkt, FriendlyByteBuf buf) {
        buf.writeBlockPos(pkt.position);
    }

    public static EntityDeathDataPacket decode(FriendlyByteBuf buf) {
        return new EntityDeathDataPacket(buf.readBlockPos());
    }

    public static void handle(EntityDeathDataPacket pkt, NetworkEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            ClientDeadEntityTracker.addDeathPosition(pkt.position);
        });
        ctx.setPacketHandled(true);
    }
}
