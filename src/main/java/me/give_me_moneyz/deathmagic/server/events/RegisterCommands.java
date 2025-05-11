package me.give_me_moneyz.deathmagic.server.events;

import me.give_me_moneyz.deathmagic.server.commands.DumpDeathsCommand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import static me.give_me_moneyz.deathmagic.DeathMagic.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RegisterCommands {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event){
        DumpDeathsCommand.register(event.getDispatcher());
    }
}