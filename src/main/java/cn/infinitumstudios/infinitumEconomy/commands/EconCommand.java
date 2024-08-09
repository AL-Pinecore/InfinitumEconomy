package cn.infinitumstudios.infinitumEconomy.commands;

import cn.infinitumstudios.infinitumEconomy.InfinitumEconomy;
import cn.infinitumstudios.infinitumEconomy.utility.MessageColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EconCommand extends InfinitumCommand implements CommandExecutor, TabCompleter {
    public static String KEYWORD = "econ";

    public EconCommand(InfinitumEconomy plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(MessageColor.text("This command can only be ran by a player.", ChatColor.RED));
            return false;
        }

        if(args.length <= 0) {
            commandSender.sendMessage(getUsage());
            return false;
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }

    @Override
    public String getUsage() {
        return "/econ <>";
    }
}
