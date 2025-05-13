package me.give_me_moneyz.deathmagic.powers;

import me.give_me_moneyz.deathmagic.server.storage.ServerDeathStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import static me.give_me_moneyz.deathmagic.DeathMagic.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Gravewalk {
    @SubscribeEvent
    public static void gravewalkAbility(final PlayerInteractEvent.RightClickItem event) {
        if (event.getSide().isServer() && event.getItemStack().getItem().equals(Items.DIAMOND)) {
            for (BlockPos pos: ServerDeathStorage.get((ServerLevel) event.getLevel()).getDeaths().keySet()) {
                if (event.getPos().distToCenterSqr(pos.getCenter()) <= 2) {

                }
            }
        }
    }
}
