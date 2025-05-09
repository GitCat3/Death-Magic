package me.give_me_moneyz.deathmagic.server.events;

import me.give_me_moneyz.deathmagic.common.network.EntityDeathPositionPacket;
import me.give_me_moneyz.deathmagic.common.network.MyNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import static me.give_me_moneyz.deathmagic.DeathMagic.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityDeathEvent {
    @SubscribeEvent
    public static void onEntityDeath(final LivingDeathEvent event) {
        if (!event.getEntity().level().isClientSide) {
            Entity deadEntity = event.getEntity();
            BlockPos pos = deadEntity.blockPosition();
            sendDeathPositionToClients((ServerLevel) deadEntity.level(), pos);
        }
    }

    public static void sendDeathPositionToClients(ServerLevel level, BlockPos pos) {
        for (ServerPlayer player : level.players()) {
            MyNetwork.CHANNEL.send(
                    PacketDistributor.PLAYER.with(() -> player),
                    new EntityDeathPositionPacket(pos)
            );
        }
    }

}
