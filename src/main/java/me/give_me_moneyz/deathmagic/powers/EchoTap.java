package me.give_me_moneyz.deathmagic.powers;

import me.give_me_moneyz.deathmagic.client.network.ClientDeadEntityTracker;
import net.minecraft.core.BlockPos;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import static me.give_me_moneyz.deathmagic.DeathMagic.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EchoTap {
    @SubscribeEvent
    public static void onRightClickNearSoulThingy(final PlayerInteractEvent.RightClickEmpty event) {
        if (event.getLevel().isClientSide) {
            for (BlockPos pos: ClientDeadEntityTracker.getDeathPositions()) {

            }
        }
    }
}
