package me.give_me_moneyz.deathmagic.common.network;

import me.give_me_moneyz.deathmagic.client.network.ClientDeadEntityTracker;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EntityDeathPositionPacket {
    private final BlockPos position;

    public EntityDeathPositionPacket(BlockPos position) {
        this.position = position;
    }

    public static void encode(EntityDeathPositionPacket pkt, FriendlyByteBuf buf) {
        buf.writeBlockPos(pkt.position);
    }

    public static EntityDeathPositionPacket decode(FriendlyByteBuf buf) {
        return new EntityDeathPositionPacket(buf.readBlockPos());
    }

    public static void handle(EntityDeathPositionPacket pkt, NetworkEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            ClientDeadEntityTracker.addDeathPosition(pkt.position);
        });
        ctx.setPacketHandled(true);
    }
}
