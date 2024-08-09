package cn.infinitumstudios.infinitumEconomy.foundation.account;

import cn.infinitumstudios.infinitumEconomy.foundation.Currency;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Bank {
    private String bankName;
    private Currency usingCurrency;
    private String bankOwnerName;
    private UUID bankOwnerUUID;
    private List<OfflinePlayer> bankMemberList = new ArrayList<>();
    private double bankBalance;
    public Bank(String bankName,OfflinePlayer bankOwner){
        this.bankName = bankName;
        this.bankOwnerName = bankOwner.getName();
        this.bankOwnerUUID = bankOwner.getUniqueId();
        this.bankBalance = 0.0;
        this.usingCurrency = new Currency("default", "default", 1);
    }

    public Bank(String bankName, String bankOwnerName, UUID bankOwnerUUID, List<OfflinePlayer> bankMemberList, Currency usingCurrency, int bankBalance){
        this.bankName = bankName;
        this.bankOwnerName = bankOwnerName;
        this.bankOwnerUUID = bankOwnerUUID;
        this.bankMemberList = bankMemberList;
        this.bankBalance = bankBalance;
        this.usingCurrency = usingCurrency;
    }

    public Bank(String bankName, OfflinePlayer bankOwner, double initialBalance){
        this.bankName = bankName;
        this.bankOwnerName = bankOwner.getName();
        this.bankOwnerUUID = bankOwner.getUniqueId();
        this.bankBalance = initialBalance;
        usingCurrency = new Currency("default", "default", 1);
    }

    public Bank(String bankName, OfflinePlayer bankOwner, Currency usingCurrency, double initialBalance){
        this.bankName = bankName;
        this.bankOwnerName = bankOwner.getName();
        this.bankOwnerUUID = bankOwner.getUniqueId();
        this.bankBalance = initialBalance;
        this.usingCurrency = usingCurrency;
    }

    public String getBankName(){
        return bankName;
    }
    public void setBankName(String bankName){
        this.bankName = bankName;
    }
    public OfflinePlayer getBankOwner(){
        return Bukkit.getOfflinePlayer(bankOwnerUUID);
    }
    public String getBankOwnerName(){
        return bankOwnerName;
    }
    public UUID getBankOwnerUUID(){
        return bankOwnerUUID;
    }
    public boolean setBankOwner(OfflinePlayer bankOwner){
        if (bankOwner == Bukkit.getOfflinePlayer(bankOwnerUUID)){
            return false;
        } else {
            this.bankOwnerUUID = bankOwner.getUniqueId();
            this.bankOwnerName = bankOwner.getName();
            return true;
        }
    }
    public List<OfflinePlayer> getBankMembers(){
        return bankMemberList;
    }
    public boolean isBankMember(OfflinePlayer player) {
        return bankMemberList.contains(player);
    }
    public boolean addBankMember(OfflinePlayer player){
        if (bankMemberList.contains(player)){
            return false;
        } else {
            bankMemberList.add(player);
            return true;
        }
    }
    public boolean removeBankMember(OfflinePlayer player){
        if (!this.bankMemberList.contains(player)){
            return false;
        } else {
            this.bankMemberList.remove(player);
            return true;
        }
    }
    public int getBankMemberAmount(){
        return bankMemberList.size();
    }
    public double getBankBalance(){
        return bankBalance;
    }
    public boolean setBankBalance(double amount){
        this.bankBalance = amount;
        return true;
    }
    public boolean incrementBankBalance(double amount){
        this.bankBalance += amount;
        return true;
    }
    public boolean decrementBankBalance(double amount){
        this.bankBalance -= amount;
        return true;
    }
    public boolean setUsingCurrency(Currency usingCurrency){
        this.usingCurrency = usingCurrency;
        return true;
    }
    public Currency getUsingCurrency(){
        return this.usingCurrency;
    }
}
