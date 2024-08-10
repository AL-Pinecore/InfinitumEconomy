package cn.infinitumstudios.infinitumEconomy.commands;

import cn.infinitumstudios.infinitumEconomy.InfinitumEconomy;
import cn.infinitumstudios.infinitumEconomy.event.PluginReloadEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class InfinitumSubcommand {
    protected InfinitumCommand command;

    public InfinitumSubcommand(InfinitumCommand command) {
        this.command = command;
    }

    public void reloadCommand(InfinitumCommand command){
        this.command = command;
    }

    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        return false;
    }

    public @Nullable List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return null;
    }

    public String getUsage() {
        return "/econ <options>";
    }
}
