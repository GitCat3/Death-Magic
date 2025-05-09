package me.give_me_moneyz.deathmagic.server.events;

import me.give_me_moneyz.deathmagic.common.network.EntityDeathPositionPacket;
import me.give_me_moneyz.deathmagic.common.network.MyNetwork;
import me.give_me_moneyz.deathmagic.server.storage.ServerDeathStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import static me.give_me_moneyz.deathmagic.DeathMagic.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerJoinEvent {
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level().isClientSide()) {
            ServerPlayer player = (ServerPlayer) event.getEntity();
            ServerDeathStorage storage = ServerDeathStorage.get(player.serverLevel());
            for (BlockPos pos : storage.getDeathPositions()) {
                MyNetwork.CHANNEL.send(
                        PacketDistributor.PLAYER.with(() -> player),
                        new EntityDeathPositionPacket(pos)
                );
            }
        }
    }

}
