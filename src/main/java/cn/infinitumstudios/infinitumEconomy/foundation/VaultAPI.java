package cn.infinitumstudios.infinitumEconomy.foundation;

import cn.infinitumstudios.infinitumEconomy.foundation.database.AccountDatabase;
import cn.infinitumstudios.infinitumEconomy.foundation.database.BankDatabase;
import cn.infinitumstudios.infinitumEconomy.foundation.database.CurrencyDatabase;
import cn.infinitumstudios.infinitumEconomy.foundation.types.Account;
import com.google.common.util.concurrent.AtomicDouble;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class VaultAPI implements net.milkbowl.vault.economy.Economy {
    /**
     * Checks if economy method is enabled.
     *
     * @return Success or Failure
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Gets name of economy method
     *
     * @return Name of Economy Method
     */
    @Override
    public String getName() {
        return "/econ";
    }

    /**
     * Returns true if the given implementation supports banks.
     *
     * @return true if the implementation supports banks
     */
    @Override
    public boolean hasBankSupport() {
        return true;
    }

    /**
     * Some economy plugins round off after a certain number of digits.
     * This function returns the number of digits the plugin keeps
     * or -1 if no rounding occurs.
     *
     * @return number of digits after the decimal point kept
     */
    // 返回约分后的值，-1为没进行任何约分
    @Override
    public int fractionalDigits() {
        return 2;
    }

    /**
     * Format amount into a human readable String This provides translation into
     * economy specific formatting to improve consistency between plugins.
     *
     * @param amount to format
     * @return Human readable string describing amount
     */
    // 整形
    @Override
    public String format(double amount) {
        return CurrencyDatabase.DEFAULT_CURRENCY.value(amount);
    }

    /**
     * Returns the name of the currency in plural form.
     * If the economy being used does not support currency names then an empty string will be returned.
     *
     * @return name of the currency (plural)
     */
    // 返回货币的名称
    // 请返回默认名称(plural)
    @Override
    public String currencyNamePlural() {
        return CurrencyDatabase.DEFAULT_CURRENCY.getPluralName();
    }


    /**
     * Returns the name of the currency in singular form.
     * If the economy being used does not support currency names then an empty string will be returned.
     *
     * @return name of the currency (singular)
     */
    // 返回货币的名称
    // 请返回默认名称(singular)
    @Override
    public String currencyNameSingular() {
        return CurrencyDatabase.DEFAULT_CURRENCY.getName();
    }

    /**
     * @deprecated As of Economy 1.4 use {@link #hasAccount(OfflinePlayer)} instead.
     */
    @Override
    @Deprecated
    public boolean hasAccount(String playerName) {
        Optional<OfflinePlayer> player = Arrays.stream(Bukkit.getOfflinePlayers())
                .filter(offlinePlayer -> Objects.requireNonNull(offlinePlayer.getName()).equalsIgnoreCase(playerName))
                .findFirst();

        return player.filter(this::hasAccount).isPresent();
    }

    /**
     * Checks if this player has an account on the server yet
     * This will always return true if the player has joined the server at least once
     * as all major economy plugins auto-generate a player account when the player joins the server
     *
     * @param player to check
     * @return if the player has an account
     */
    // 玩家是否拥有经济帐户
    @Override
    public boolean hasAccount(OfflinePlayer player) {
        AccountDatabase db = new AccountDatabase();
        db.load();
        return db.accountExists(player);
    }

    /**
     * @deprecated As of Economy 1.4 use {@link #hasAccount(OfflinePlayer, String)} instead.
     */
    @Override
    @Deprecated
    public boolean hasAccount(String playerName, String worldName) {
        return hasAccount(playerName);
    }

    /**
     * Checks if this player has an account on the server yet on the given world
     * This will always return true if the player has joined the server at least once
     * as all major economy plugins auto-generate a player account when the player joins the server
     *
     * @param player    to check in the world
     * @param worldName world-specific account
     * @return if the player has an account
     */
    // 玩家是否拥有经济帐户
    @Override
    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return hasAccount(player);
    }

    /**
     * @deprecated As of Economy 1.4 use {@link #getBalance(OfflinePlayer)} instead.
     */
    @Override
    @Deprecated
    public double getBalance(String playerName) {
        Optional<@NotNull OfflinePlayer> temp =Arrays.stream(Bukkit.getOfflinePlayers())
                .filter(offlinePlayer -> Objects.requireNonNull(offlinePlayer.getName()).equalsIgnoreCase(playerName))
                .findFirst();

        return temp.map(this::getBalance).orElse(0.0);

    }

    /**
     * Gets balance of a player
     *
     * @param player of the player
     * @return Amount currently held in players account
     */
    // 获取玩家所拥有的钱的总量，换算为default货币
    @Override
    public double getBalance(OfflinePlayer player) {
        AccountDatabase db = new AccountDatabase();
        db.load();
        Optional<Account> temp = db.getPlayerAccount(player);
        return temp.map(Account::getBaseBalance).orElse(0.0);
    }

    /**
     * @deprecated As of Economy 1.4 use {@link #getBalance(OfflinePlayer, String)} instead.
     */
    @Override
    @Deprecated
    public double getBalance(String playerName, String world) {
        return getBalance(playerName);
    }

    /**
     * Gets balance of a player on the specified world.
     * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this the global balance will be returned.
     *
     * @param player to check
     * @param world  name of the world
     * @return Amount currently held in players account
     */
    // 获取玩家所拥有的钱的总量，换算为default货币
    @Override
    public double getBalance(OfflinePlayer player, String world) {
        return getBalance(player);
    }

    /**
     * @deprecated As of Economy 1.4 use {@link #has(OfflinePlayer, double)} instead.
     */
    @Override
    @Deprecated
    public boolean has(String playerName, double amount) {
        return getBalance(playerName) >= amount;
    }

    /**
     * Checks if the player account has the amount - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player to check
     * @param amount to check for
     * @return True if <b>player</b> has <b>amount</b>, False else wise
     */
    // 查看玩家是否拥有amount的钱（够不够），请返回GlobalBalance并且换算为default货币
    @Override
    public boolean has(OfflinePlayer player, double amount) {
        return getBalance(player) >= amount;
    }

    /**
     * @deprecated As of Economy 1.4 use @{link {@link #has(OfflinePlayer, String, double)} instead.
     */
    @Override
    @Deprecated
    public boolean has(String playerName, String worldName, double amount) {
        return getBalance(playerName) >= amount;
    }

    /**
     * Checks if the player account has the amount in a given world - DO NOT USE NEGATIVE AMOUNTS
     * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this the global balance will be returned.
     *
     * @param player    to check
     * @param worldName to check with
     * @param amount    to check for
     * @return True if <b>player</b> has <b>amount</b>, False else wise
     */
    @Override
    // 查看玩家是否拥有amount的钱（够不够），请返回GlobalBalance并且换算为default货币
    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return getBalance(player) >= amount;
    }

    /**
     * @deprecated As of Economy 1.4 use {@link #withdrawPlayer(OfflinePlayer, double)} instead.
     */
    @Override
    @Deprecated
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        return null;
    }

    /**
     * Withdraw an amount from a player - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player to withdraw from
     * @param amount Amount to withdraw
     * @return Detailed response of transaction
     */
    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        AccountDatabase db = new AccountDatabase();
        db.load();
        Optional<Account> temp = db.getPlayerAccount(player);
        if(temp.isEmpty()) return new EconomyResponse(amount, 0, EconomyResponse.ResponseType.FAILURE, "Player Account does not exist.");
        if(!has(player, amount)) return new EconomyResponse(amount, temp.get().getBaseBalance(), EconomyResponse.ResponseType.FAILURE, "Player Account does not have sufficient amount of balance.");

        db.update(account -> account.equals(temp.get()), account -> {
            account.decrementBalance(amount);
            return account;
        });
        db.save();

        return new EconomyResponse(amount, temp.get().getBaseBalance(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    /**
     * @deprecated As of Economy 1.4 use {@link #withdrawPlayer(OfflinePlayer, String, double)} instead.
     */
    @Override
    @Deprecated
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        Optional<@NotNull OfflinePlayer> temp = Arrays.stream(Bukkit.getOfflinePlayers())
                .filter(offlinePlayer -> Objects.requireNonNull(offlinePlayer.getName()).equalsIgnoreCase(playerName))
                .findFirst();

        if(temp.isEmpty()) return new EconomyResponse(amount, 0, EconomyResponse.ResponseType.FAILURE, "Player does not exist.");

        return withdrawPlayer(temp.get(), amount);
    }

    /**
     * Withdraw an amount from a player on a given world - DO NOT USE NEGATIVE AMOUNTS
     * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this the global balance will be returned.
     *
     * @param player    to withdraw from
     * @param worldName - name of the world
     * @param amount    Amount to withdraw
     * @return Detailed response of transaction
     */
    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        return withdrawPlayer(player, amount);
    }

    /**
     * @deprecated As of Economy 1.4 use {@link #depositPlayer(OfflinePlayer, double)} instead.
     */
    @Override
    @Deprecated
    public EconomyResponse depositPlayer(String playerName, double amount) {
        return null;
    }

    /**
     * Deposit an amount to a player - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player to deposit to
     * @param amount Amount to deposit
     * @return Detailed response of transaction
     */
    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        AccountDatabase db = new AccountDatabase();
        db.load();
        if(!db.accountExists(player)) return new EconomyResponse(amount, 0, EconomyResponse.ResponseType.FAILURE, "Player Account does not exist.");
        AtomicDouble count = new AtomicDouble(0);
        db.update(account -> account.isPlayerAccountHolder(player), account -> {
            account.incrementBalance(amount);
            count.set(account.getBaseBalance());
            return account;
        });
        db.save();

        return new EconomyResponse(amount, count.get(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    /**
     * @deprecated As of Economy 1.4 use {@link #depositPlayer(OfflinePlayer, String, double)} instead.
     */
    @Override
    @Deprecated
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(playerName, amount);
    }

    /**
     * Deposit an amount to a player - DO NOT USE NEGATIVE AMOUNTS
     * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this the global balance will be returned.
     *
     * @param player    to deposit to
     * @param worldName name of the world
     * @param amount    Amount to deposit
     * @return Detailed response of transaction
     */
    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        return depositPlayer(player, amount);
    }

    /**
     * @deprecated As of Economy 1.4 use {{@link #createBank(String, OfflinePlayer)} instead.
     */
    @Override
    @Deprecated
    public EconomyResponse createBank(String name, String player) {
        BankDatabase db = new BankDatabase();
        Optional<OfflinePlayer> temp = Arrays.stream(Bukkit.getOfflinePlayers()).filter(offlinePlayer -> Objects.requireNonNull(offlinePlayer.getName()).equalsIgnoreCase(player)).findFirst();
        db.load();
        if(temp.isEmpty()) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player does not exist.");
        boolean response = db.create(name, temp.get());
        if(response) return new EconomyResponse(0.0, 0, EconomyResponse.ResponseType.SUCCESS, "");
        else return new EconomyResponse(0.0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot create bank due to certain reasons.");
    }

    /**
     * Creates a bank account with the specified name and the player as the owner
     *
     * @param name   of account
     * @param player the account should be linked to
     * @return EconomyResponse Object
     */
    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return null;
    }

    /**
     * Deletes a bank account with the specified name.
     *
     * @param name of the back to delete
     * @return if the operation completed successfully
     */
    @Override
    public EconomyResponse deleteBank(String name) {
        return null;
    }

    /**
     * Returns the amount the bank has
     *
     * @param name of the account
     * @return EconomyResponse Object
     */
    @Override
    public EconomyResponse bankBalance(String name) {
        return null;
    }

    /**
     * Returns true or false whether the bank has the amount specified - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param name   of the account
     * @param amount to check for
     * @return EconomyResponse Object
     */
    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return null;
    }

    /**
     * Withdraw an amount from a bank account - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param name   of the account
     * @param amount to withdraw
     * @return EconomyResponse Object
     */
    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return null;
    }

    /**
     * Deposit an amount into a bank account - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param name   of the account
     * @param amount to deposit
     * @return EconomyResponse Object
     */
    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return null;
    }

    /**
     * @deprecated As of Economy 1.4 use {{@link #isBankOwner(String, OfflinePlayer)} instead.
     */
    @Override
    @Deprecated
    public EconomyResponse isBankOwner(String name, String playerName) {
        return null;
    }

    /**
     * Check if a player is the owner of a bank account
     *
     * @param name   of the account
     * @param player to check for ownership
     * @return EconomyResponse Object
     */
    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return null;
    }

    /**
     * @deprecated As of Economy 1.4 use {@link #isBankMember(String, OfflinePlayer)} instead.
     */
    @Override
    @Deprecated
    public EconomyResponse isBankMember(String name, String playerName) {
        return null;
    }

    /**
     * Check if the player is a member of the bank account
     *
     * @param name   of the account
     * @param player to check membership
     * @return EconomyResponse Object
     */
    // 查看玩家是否为一个银行的成员
    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return null;
    }

    /**
     * Gets the list of banks
     *
     * @return the List of Banks
     */
    // 返回银行列表，返回类型为字符串数列
    @Override
    public List<String> getBanks() {
        return null;
    }

    /**
     * @deprecated As of Economy 1.4 use {{@link #createPlayerAccount(OfflinePlayer)} instead.
     */
    @Override
    @Deprecated
    public boolean createPlayerAccount(String playerName) {
        return false;
    }

    /**
     * Attempts to create a player account for the given player
     *
     * @param player OfflinePlayer
     * @return if the account creation was successful
     */
    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        return false;
    }

    /**
     * @deprecated As of Economy 1.4 use {{@link #createPlayerAccount(OfflinePlayer, String)} instead.
     */
    @Override
    @Deprecated
    public boolean createPlayerAccount(String playerName, String worldName) {
        return false;
    }

    /**
     * Attempts to create a player account for the given player on the specified world
     * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this then false will always be returned.
     *
     * @param player    OfflinePlayer
     * @param worldName String name of the world
     * @return if the account creation was successful
     */
    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        return false;
    }
}
