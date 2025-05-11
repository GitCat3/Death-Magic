package me.give_me_moneyz.deathmagic.server.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import me.give_me_moneyz.deathmagic.server.storage.ServerDeathStorage;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class DumpDeathsCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("dumpdeaths").executes(DumpDeathsCommand::execute));
    }

    private static int execute(CommandContext<CommandSourceStack> command) {
        if (command.getSource().getEntity() instanceof ServerPlayer) {
            ServerPlayer player = command.getSource().getPlayer();
            player.sendSystemMessage(Component.literal(ServerDeathStorage.get(command.getSource().getLevel()).getDeaths().toString()));
        }
        return Command.SINGLE_SUCCESS;
    }
}
