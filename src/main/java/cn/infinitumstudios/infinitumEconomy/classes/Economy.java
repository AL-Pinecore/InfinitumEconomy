package cn.infinitumstudios.infinitumEconomy.classes;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;

import java.util.List;

public class Economy implements net.milkbowl.vault.economy.Economy {

    /**
     * @return
     */
    @Override
    public boolean isEnabled() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public String getName() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public boolean hasBankSupport() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public int fractionalDigits() {
        return 0;
    }

    /**
     * @param v
     * @return
     */
    @Override
    public String format(double v) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public String currencyNamePlural() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public String currencyNameSingular() {
        return null;
    }

    /**
     * @param s
     * @deprecated
     */
    @Override
    public boolean hasAccount(String s) {
        return false;
    }

    /**
     * @param offlinePlayer
     * @return
     */
    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    /**
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public boolean hasAccount(String s, String s1) {
        return false;
    }

    /**
     * @param offlinePlayer
     * @param s
     * @return
     */
    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }

    /**
     * @param s
     * @deprecated
     */
    @Override
    public double getBalance(String s) {
        return 0;
    }

    /**
     * @param offlinePlayer
     * @return
     */
    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return 0;
    }

    /**
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public double getBalance(String s, String s1) {
        return 0;
    }

    /**
     * @param offlinePlayer
     * @param s
     * @return
     */
    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        return 0;
    }

    /**
     * @param s
     * @param v
     * @deprecated
     */
    @Override
    public boolean has(String s, double v) {
        return false;
    }

    /**
     * @param offlinePlayer
     * @param v
     * @return
     */
    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        return false;
    }

    /**
     * @param s
     * @param s1
     * @param v
     * @deprecated
     */
    @Override
    public boolean has(String s, String s1, double v) {
        return false;
    }

    /**
     * @param offlinePlayer
     * @param s
     * @param v
     * @return
     */
    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        return false;
    }

    /**
     * @param s
     * @param v
     * @deprecated
     */
    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {
        return null;
    }

    /**
     * @param offlinePlayer
     * @param v
     * @return
     */
    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @param v
     * @deprecated
     */
    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        return null;
    }

    /**
     * @param offlinePlayer
     * @param s
     * @param v
     * @return
     */
    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return null;
    }

    /**
     * @param s
     * @param v
     * @deprecated
     */
    @Override
    public EconomyResponse depositPlayer(String s, double v) {
        return null;
    }

    /**
     * @param offlinePlayer
     * @param v
     * @return
     */
    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @param v
     * @deprecated
     */
    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        return null;
    }

    /**
     * @param offlinePlayer
     * @param s
     * @param v
     * @return
     */
    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public EconomyResponse createBank(String s, String s1) {
        return null;
    }

    /**
     * @param s
     * @param offlinePlayer
     * @return
     */
    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public EconomyResponse deleteBank(String s) {
        return null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public EconomyResponse bankBalance(String s) {
        return null;
    }

    /**
     * @param s
     * @param v
     * @return
     */
    @Override
    public EconomyResponse bankHas(String s, double v) {
        return null;
    }

    /**
     * @param s
     * @param v
     * @return
     */
    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return null;
    }

    /**
     * @param s
     * @param v
     * @return
     */
    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return null;
    }

    /**
     * @param s
     * @param offlinePlayer
     * @return
     */
    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return null;
    }

    /**
     * @param s
     * @param offlinePlayer
     * @return
     */
    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public List<String> getBanks() {
        return null;
    }

    /**
     * @param s
     * @deprecated
     */
    @Override
    public boolean createPlayerAccount(String s) {
        return false;
    }

    /**
     * @param offlinePlayer
     * @return
     */
    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    /**
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return false;
    }

    /**
     * @param offlinePlayer
     * @param s
     * @return
     */
    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }
}
