package cn.infinitumstudios.infinitumEconomy;

import cn.infinitumstudios.infinitumEconomy.classes.Chat;
import cn.infinitumstudios.infinitumEconomy.classes.Economy;
import cn.infinitumstudios.infinitumEconomy.classes.Permission;
import cn.infinitumstudios.infinitumEconomy.listeners.PlayerEventListener;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class InfinitumEconomy extends JavaPlugin {

    private static Economy econ = null;
    private static net.milkbowl.vault.permission.Permission perms = null;
    private static net.milkbowl.vault.chat.Chat chat = null;
    protected FileConfiguration config;

    PlayerEventListener PEL;

    @Override
    public void onEnable() {

        this.saveDefaultConfig();
        config = getConfig();
        getLogger().info(getDataFolder().toString());

        Bukkit.getServer().getServicesManager().register(Economy.class, new Economy(), this, ServicePriority.Highest);
        Bukkit.getServer().getServicesManager().register(Chat.class, new Chat(perms), this, ServicePriority.Highest);
        Bukkit.getServer().getServicesManager().register(Permission.class, new Permission(), this, ServicePriority.Highest);

        if (!setupEconomy()) {
            getLogger().severe("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        PEL = new PlayerEventListener();
        getServer().getPluginManager().registerEvents(PEL, this);

        setupPermissions();
        setupChat();

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

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        assert rsp != null;
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        assert rsp != null;
        perms = rsp.getProvider();
        return perms != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static net.milkbowl.vault.permission.Permission getPermissions() {
        return perms;
    }

    public static net.milkbowl.vault.chat.Chat getChat() {
        return chat;
    }

    @Override
    public void onDisable() {
        getLogger().info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }

    protected void setPlayerAccount(String playerName, String playerUUID){

    }
}
