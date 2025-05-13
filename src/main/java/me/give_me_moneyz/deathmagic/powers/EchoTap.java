package me.give_me_moneyz.deathmagic.powers;

import me.give_me_moneyz.deathmagic.server.storage.ServerDeathStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import static me.give_me_moneyz.deathmagic.DeathMagic.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EchoTap {
    @SubscribeEvent
    public static void echoTapAbility(final PlayerInteractEvent.RightClickBlock event) {
        if (event.getSide().isServer()) {
            if (event.getHand() != InteractionHand.MAIN_HAND) return;
            ServerPlayer player = (ServerPlayer) event.getEntity();
            ServerDeathStorage deathStorage = ServerDeathStorage.get((ServerLevel) event.getLevel());
            for (BlockPos position: deathStorage.getDeaths().keySet()) {
                if (event.getHitVec().getBlockPos().distToCenterSqr(position.getCenter()) <= 2) {
                    if (player.getHealth() != player.getMaxHealth()) {
                        float amountHealed = Math.min(player.getMaxHealth()-player.getHealth(), deathStorage.getDeaths().get(position));
                        player.heal(amountHealed);
                        float deathHealth = deathStorage.getDeaths().get(position);
                        deathStorage.changeDeathHealth(position, deathHealth-amountHealed, (ServerLevel) event.getLevel());
                        break;
                    }
                }
            }
        }
    }
}
