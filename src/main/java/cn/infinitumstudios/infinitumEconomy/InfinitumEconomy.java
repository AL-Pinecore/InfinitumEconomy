package cn.infinitumstudios.infinitumEconomy;

import cn.infinitumstudios.infinitumEconomy.event.PlayerJoinEvent;
import cn.infinitumstudios.infinitumEconomy.foundation.Economy;
import cn.infinitumstudios.infinitumEconomy.event.listeners.PlayerEventListener;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class InfinitumEconomy extends JavaPlugin {

    private static Economy econ = null;
    private static net.milkbowl.vault.permission.Permission perms = null;
    private static net.milkbowl.vault.chat.Chat chat = null;
    protected FileConfiguration config;

    private static InfinitumEconomy instance;

    PlayerEventListener PEL;

    public InfinitumEconomy() {
        instance = this;
        PlayerJoinEvent.EVENT.register(player -> {
            setPlayerAccount(player);
        });
    }

    @Override
    public void onEnable() {

        this.saveDefaultConfig();
        config = getConfig();
        getLogger().info(getDataFolder().toString());

        Bukkit.getServer().getServicesManager().register(Economy.class, new Economy(), this, ServicePriority.Highest);

        if (!setupEconomy()) {
            getLogger().severe("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        PEL = new PlayerEventListener(this);
        getServer().getPluginManager().registerEvents(PEL, this);

        getLogger().info("InfinitumEconomy plugin successfully enabled!");

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

    @Override
    public void onDisable() {
        getLogger().info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }

    public void setPlayerAccount(OfflinePlayer joinPlayer){
        if(!econ.hasAccount(joinPlayer)){
            econ.createPlayerAccount(joinPlayer);
        }
    }

    public static InfinitumEconomy get() {
        if(instance == null){
            instance = getPlugin(InfinitumEconomy.class);
        }
        return instance;
    }
}
