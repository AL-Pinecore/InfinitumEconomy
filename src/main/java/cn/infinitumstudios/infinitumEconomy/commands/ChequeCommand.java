package cn.infinitumstudios.infinitumEconomy.commands;

import cn.infinitumstudios.infinitumEconomy.InfinitumEconomy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChequeCommand extends InfinitumSubcommand{
    // Create a Cheque using a paper
    public ChequeCommand(InfinitumEconomy plugin) {
        super(plugin);
    }
}
