package cn.infinitumstudios.infinitumEconomy;

import cn.infinitumstudios.infinitumEconomy.commands.EconCommand;
import cn.infinitumstudios.infinitumEconomy.commands.MoneyCommand;
import cn.infinitumstudios.infinitumEconomy.event.PlayerJoinEvent;
import cn.infinitumstudios.infinitumEconomy.event.listeners.PlayerEventListener;

import cn.infinitumstudios.infinitumEconomy.foundation.VaultAPI;
import cn.infinitumstudios.infinitumEconomy.foundation.database.sql.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;

public class InfinitumEconomy extends JavaPlugin {

    private static VaultAPI econ = null;
    private static Economy eco = new VaultAPI();
    private static net.milkbowl.vault.permission.Permission perms = null;
    private static net.milkbowl.vault.chat.Chat chat = null;
    protected FileConfiguration config;

    private static InfinitumEconomy instance;

    PlayerEventListener PEL;

    private Economy provider;

    public InfinitumEconomy() {
        instance = this;
        PlayerJoinEvent.EVENT.register(this :: setPlayerAccount);
    }

    @Override
    public void onLoad () {
        getLogger().severe("Hooking into Vault...");
        Bukkit.getServicesManager().register(Economy.class, eco, Objects.requireNonNull(this.getServer().getPluginManager().getPlugin("Vault")), ServicePriority.Highest);
    }

    @Override
    public void onEnable() {

        this.saveDefaultConfig();
        config = getConfig();

        try {
            AccountSQLDatabase accountSQLDatabase = new AccountSQLDatabase(this.getDataFolder().getAbsolutePath() + "/economy.db");
            BankSQLDatabase bankSQLDatabase = new BankSQLDatabase(this.getDataFolder().getAbsolutePath() + "/economy.db");
            ChequeSQLDatabase chequeSQLDatabase = new ChequeSQLDatabase(this.getDataFolder().getAbsolutePath() + "/economy.db");
            CurrencySQLDatabase currencyDatabase = new CurrencySQLDatabase(this.getDataFolder().getAbsolutePath() + "/economy.db");
            LoanSQLDatabase loanSQLDatabase = new LoanSQLDatabase(this.getDataFolder().getAbsolutePath() + "/economy.db");
            WalletSQLDatabase walletSQLDatabase = new WalletSQLDatabase(this.getDataFolder().getAbsolutePath() + "/economy.db");
            VaultSQLDatabase vaultSQLDatabase = new VaultSQLDatabase(this.getDataFolder().getAbsolutePath() + "/economy.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (!getDataFolder().exists()){
            if (!getDataFolder().mkdirs()){
                getLogger().severe("Disabled due to failed to create plugin data folder!");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
        }

        getLogger().info(getDataFolder().toString());

        if (!setupEconomy()) {
            getLogger().severe("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        PEL = new PlayerEventListener(this);
        getServer().getPluginManager().registerEvents(PEL, this);

        this.getCommand("econ").setExecutor(new EconCommand(this));
        this.getCommand("money").setExecutor(new MoneyCommand());

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
