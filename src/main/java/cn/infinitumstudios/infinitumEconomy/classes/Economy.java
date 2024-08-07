package cn.infinitumstudios.infinitumEconomy.classes;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;

import java.util.List;

public class Economy implements net.milkbowl.vault.economy.Economy {

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double value) {
        return null;
    }

    @Override
    public String currencyNamePlural() {
        return null;
    }

    @Override
    public String currencyNameSingular() {
        return null;
    }

    @Override
    @Deprecated
    public boolean hasAccount(String playerName) {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    @Override
    public boolean hasAccount(String playerName, String playerUUID) {
        return false;
    }

    @Override
    @Deprecated
    public boolean hasAccount(OfflinePlayer offlinePlayer, String playerName) {
        return false;
    }

    @Override
    @Deprecated
    public double getBalance(String playerName) {
        return 0;
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return 0;
    }

    @Override
    public double getBalance(String playerName, String playerUUID) {
        return 0;
    }

    @Override
    @Deprecated
    public double getBalance(OfflinePlayer offlinePlayer, String playerName) {
        return 0;
    }

    @Override
    @Deprecated
    public boolean has(String playerName, double value) {
        return false;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double value) {
        return false;
    }

    @Override
    public boolean has(String playerName, String playerUUID, double value) {
        return false;
    }

    @Override
    @Deprecated
    public boolean has(OfflinePlayer offlinePlayer, String playerName, double v) {
        return false;
    }

    @Override
    @Deprecated
    public EconomyResponse withdrawPlayer(String playerName, double value) {
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double value) {
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String playerUUID, double value) {
        return null;
    }

    @Override
    @Deprecated
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String playerName, double value) {
        return null;
    }

    @Override
    @Deprecated
    public EconomyResponse depositPlayer(String playerName, double value) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double value) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String playerUUID, double value) {
        return null;
    }

    @Override
    @Deprecated
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String playerName, double value) {
        return null;
    }

    @Override
    @Deprecated
    public EconomyResponse createBank(String bankName, String playerName) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String bankName, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String bankName) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String bankName) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String bankName, double value) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String bankName, double value) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String bankName, double value) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String bankName, String playerName) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String bankName, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String bankName, String playerName) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String bankName, OfflinePlayer offlinePlayer) {
        return null;
    }


    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    @Deprecated
    public boolean createPlayerAccount(String playerName) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(String playerName, String playerUUID) {
        return false;
    }

    @Override
    @Deprecated
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String playerName) {
        return false;
    }

}
