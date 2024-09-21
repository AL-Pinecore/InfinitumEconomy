package cn.infinitumstudios.infinitumEconomy.commands;

import cn.infinitumstudios.infinitumEconomy.InfinitumEconomy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaltopCommand extends InfinitumSubcommand{
    public BaltopCommand(InfinitumCommand command) {
        super(command);
    }
    // Shows the balance top list to player/console
    // TODO finish the Balance Top Command

    @Override
    public void reloadCommand(InfinitumCommand command) {
        super.reloadCommand(command);
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
