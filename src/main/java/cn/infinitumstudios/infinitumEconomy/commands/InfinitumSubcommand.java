package cn.infinitumstudios.infinitumEconomy.commands;

import cn.infinitumstudios.infinitumEconomy.InfinitumEconomy;
import cn.infinitumstudios.infinitumEconomy.event.PluginReloadEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class InfinitumSubcommand {
    protected InfinitumEconomy plugin;
    protected FileConfiguration config;

    public InfinitumSubcommand(final InfinitumEconomy plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();

        PluginReloadEvent.EVENT.register(this::reloadCommand);
    }

    public void reloadCommand(InfinitumEconomy plugin){
        this.plugin = plugin;
        config = plugin.getConfig();
    }

    public boolean execute(CommandSender sender, String[] args) {
        return false;
    }

    public @Nullable List<String> tabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
