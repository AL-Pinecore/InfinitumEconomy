package cn.infinitumstudios.infinitumEconomy;

import cn.infinitumstudios.infinitumEconomy.commands.EconCommand;
import cn.infinitumstudios.infinitumEconomy.commands.MoneyCommand;
import cn.infinitumstudios.infinitumEconomy.event.PlayerJoinEvent;
import cn.infinitumstudios.infinitumEconomy.event.listeners.PlayerEventListener;

import cn.infinitumstudios.infinitumEconomy.foundation.EconomyImplementer;
import cn.infinitumstudios.infinitumEconomy.utility.VaultHook;
import cn.infinitumstudios.infinitumEconomy.foundation.database.sql.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class InfinitumEconomy extends JavaPlugin {

    private static EconomyImplementer eco = new EconomyImplementer();
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

        PEL = new PlayerEventListener(this);
        getServer().getPluginManager().registerEvents(PEL, this);

        this.getCommand("econ").setExecutor(new EconCommand(this));
        this.getCommand("money").setExecutor(new MoneyCommand());

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
}