package cn.infinitumstudios.infinitumEconomy;

import cn.infinitumstudios.infinitumEconomy.event.PlayerJoinEvent;
import cn.infinitumstudios.infinitumEconomy.event.listeners.PlayerEventListener;

import cn.infinitumstudios.infinitumEconomy.foundation.EconomyImplementer;
import cn.infinitumstudios.infinitumEconomy.utility.VaultHook;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class InfinitumEconomy extends JavaPlugin {

    private static EconomyImplementer eco = new EconomyImplementer();
    private static SQLiteDatabaseManager sqliteDatabase;
    private static net.milkbowl.vault.permission.Permission perms = null;
    private static net.milkbowl.vault.chat.Chat chat = null;
    protected FileConfiguration config;
    private static InfinitumEconomy instance;
    private VaultHook vaultHook;

    PlayerEventListener PEL;

    private Economy provider;

    public InfinitumEconomy() {
        instance = this;
        PlayerJoinEvent.EVENT.register(this :: setPlayerAccount);
    }

    @Override
    public void onLoad () {
        instance = this;
        vaultHook = new VaultHook();
        vaultHook.hook();
        loadDatabase();
    }

    private static void loadDatabase(){
        try {
            sqliteDatabase = new SQLiteDatabaseManager();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onEnable() {

        this.saveDefaultConfig();
        config = getConfig();

        if (!getDataFolder().exists()){
            if (!getDataFolder().mkdirs()){
                getLogger().severe("Disabled due to failed to create plugin data folder!");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
        }

        PEL = new PlayerEventListener(this);
        getServer().getPluginManager().registerEvents(PEL, this);

//        this.getCommand("econ").setExecutor(new EcoCommand(this));
//        this.getCommand("money").setExecutor(new MoneyCommand());

        getLogger().info("InfinitumEconomy plugin successfully enabled!");

    }

    @Override
    public void onDisable() {
        getLogger().info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
        vaultHook.unhook();
    }

    private void setPlayerAccount(OfflinePlayer joinPlayer){
        if(!eco.hasAccount(joinPlayer)){
            eco.createPlayerAccount(joinPlayer);
        }
    }

    public static InfinitumEconomy get() {
        if (instance == null){
            instance = getPlugin(InfinitumEconomy.class);
        }
        return instance;
    }

    public static InfinitumEconomy getPlugin() {
        return instance;
    }

    public static EconomyImplementer getEconomyImplementer(){
        return eco;
    }

    public static SQLiteDatabaseManager getSqliteDatabaseManager(){
        if (sqliteDatabase == null){
            loadDatabase();
        }
        return sqliteDatabase;
    }
}