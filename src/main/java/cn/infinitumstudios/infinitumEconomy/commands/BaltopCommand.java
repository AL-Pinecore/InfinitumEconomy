package cn.infinitumstudios.infinitumEconomy.commands;

import cn.infinitumstudios.infinitumEconomy.InfinitumEconomy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BaltopCommand extends InfinitumSubcommand{
    // Shows the balance top list to player/console
    public BaltopCommand(InfinitumEconomy plugin){
        super(plugin);
    }
}
