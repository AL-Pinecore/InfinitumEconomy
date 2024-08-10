package cn.infinitumstudios.infinitumEconomy.commands;

import cn.infinitumstudios.infinitumEconomy.InfinitumEconomy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LoanCommand extends InfinitumSubcommand {

    public LoanCommand(InfinitumCommand command) {
        super(command);
    }

    @Override
    public void reloadCommand(InfinitumCommand command) {
        super.reloadCommand(command);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        return false;
    }

    @Override
    public @Nullable List<String> tabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
