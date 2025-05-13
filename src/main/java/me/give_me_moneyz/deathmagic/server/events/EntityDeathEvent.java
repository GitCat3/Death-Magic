package me.give_me_moneyz.deathmagic.server.events;

import me.give_me_moneyz.deathmagic.common.network.EntityDeathDataPacket;
import me.give_me_moneyz.deathmagic.common.network.MyNetwork;
import me.give_me_moneyz.deathmagic.server.storage.ServerDeathStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import static me.give_me_moneyz.deathmagic.DeathMagic.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityDeathEvent {
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!event.getEntity().level().isClientSide()) {
            ServerLevel level = (ServerLevel) event.getEntity().level();
            BlockPos pos = event.getEntity().blockPosition();
            float health = event.getEntity().getMaxHealth();
            if (health >= 20) {
                ServerDeathStorage deathStorage = ServerDeathStorage.get(level);
                deathStorage.addDeath(pos, health);

                // Optionally send to online players
                for (ServerPlayer player : level.players()) {
                    MyNetwork.CHANNEL.send(
                            PacketDistributor.PLAYER.with(() -> player),
                            new EntityDeathDataPacket(pos, false)
                    );
                }
            }
        }
    }
}
