package cn.infinitumstudios.infinitumEconomy;

import cn.infinitumstudios.infinitumEconomy.commands.EconCommand;
import cn.infinitumstudios.infinitumEconomy.event.PlayerJoinEvent;
import cn.infinitumstudios.infinitumEconomy.foundation.VaultAPI;
import cn.infinitumstudios.infinitumEconomy.event.listeners.PlayerEventListener;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class InfinitumEconomy extends JavaPlugin {

    private static VaultAPI econ = null;
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

//        try {
//            AccountSQLDatabase accountSQLDatabase = new AccountSQLDatabase(this.getDataFolder().getAbsolutePath() + "/economy.db");
//            BankSQLDatabase bankSQLDatabase = new BankSQLDatabase(this.getDataFolder().getAbsolutePath() + "/economy.db");
//            ChequeSQLDatabase chequeSQLDatabase = new ChequeSQLDatabase(this.getDataFolder().getAbsolutePath() + "/economy.db");
//            CurrencyDatabase currencyDatabase = new CurrencyDatabase(this.getDataFolder().getAbsolutePath() + "/economy.db");
//            AccountSQLDatabase accountSQLDatabase = new AccountSQLDatabase(this.getDataFolder().getAbsolutePath() + "/economy.db");
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        if (!getDataFolder().exists()){
            if (!getDataFolder().mkdirs()){
                getLogger().severe("Disabled due to failed to create plugin data folder!");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
        }

        getLogger().info(getDataFolder().toString());

        Bukkit.getServer().getServicesManager().register(VaultAPI.class, new VaultAPI(), this, ServicePriority.Highest);

        if (!setupEconomy()) {
            getLogger().severe("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        PEL = new PlayerEventListener(this);
        getServer().getPluginManager().registerEvents(PEL, this);

        this.getCommand("econ").setExecutor(new EconCommand(this));
        this.getCommand("pay").setExecutor(new EconCommand(this));
        this.getCommand("money").setExecutor(new EconCommand(this));
        this.getCommand("loan").setExecutor(new EconCommand(this));
        this.getCommand("cheque").setExecutor(new EconCommand(this));
        this.getCommand("baltop").setExecutor(new EconCommand(this));


        getLogger().info("InfinitumEconomy plugin successfully enabled!");

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<VaultAPI> rsp = getServer().getServicesManager().getRegistration(VaultAPI.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static VaultAPI getEconomy() {
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
        if (instance == null){
            instance = getPlugin(InfinitumEconomy.class);
        }
        return instance;
    }
}
