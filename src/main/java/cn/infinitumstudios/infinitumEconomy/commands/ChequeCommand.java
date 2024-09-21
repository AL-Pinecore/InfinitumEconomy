package cn.infinitumstudios.infinitumEconomy.commands;

import cn.infinitumstudios.infinitumEconomy.InfinitumEconomy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChequeCommand extends InfinitumSubcommand{
    // TODO finish the cheque commands
    public ChequeCommand(InfinitumCommand command) {
        super(command);
    }
    // Create a Cheque using a paper

    @Override
    public void reloadCommand(InfinitumCommand command) {

    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        return super.execute(sender, args);
    }

    @Override
    public @Nullable List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return super.tabComplete(sender, args);
    }
}
