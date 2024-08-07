package cn.infinitumstudios.infinitumEconomy.commands;

import cn.infinitumstudios.infinitumEconomy.InfinitumEconomy;
import cn.infinitumstudios.infinitumEconomy.event.PluginReloadEvent;
import org.bukkit.configuration.file.FileConfiguration;

public class InfinitumCommand {
    protected InfinitumEconomy plugin;
    protected FileConfiguration config;

    public InfinitumCommand(final InfinitumEconomy plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();

        PluginReloadEvent.EVENT.register(this::reloadCommand);
    }

    public void reloadCommand(InfinitumEconomy plugin){
        this.plugin = plugin;
        config = plugin.getConfig();
    }
}
