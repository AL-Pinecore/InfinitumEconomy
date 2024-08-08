package cn.infinitumstudios.infinitumEconomy.foundation.account;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Bank {
    private String bankName, bankOwnerName;
    private UUID bankOwnerUUID;
    private Player bankOwner;
    private List<Player> bankMemberList = new ArrayList<>();
    private double bankBalance;
    public Bank(String bankName,Player bankOwner){
        this.bankName = bankName;
        this.bankOwner = bankOwner;
        this.bankOwnerName = bankOwner.getName();
        this.bankOwnerUUID = bankOwner.getUniqueId();
        this.bankBalance = 0.0;
    }

    public Bank(String bankName, Player bankOwner, UUID bankOwnerUUID, String bankOwnerName, List<Player> bankMemberList, int bankBalance){
        this.bankName = bankName;
        this.bankOwner = bankOwner;
        this.bankOwnerName = bankOwnerName;
        this.bankOwnerUUID = bankOwnerUUID;
        this.bankMemberList = bankMemberList;
        this.bankBalance = bankBalance;
    }

    public Bank(String bankName,Player bankOwner,double initialBalance){
        this.bankName = bankName;
        this.bankOwner = bankOwner;
        this.bankOwnerName = bankOwner.getName();
        this.bankOwnerUUID = bankOwner.getUniqueId();
        this.bankBalance = initialBalance;
    }

    public String getBankName(){
        return bankName;
    }
    public void setBankName(String bankName){
        this.bankName = bankName;
    }
    public Player getBankOwner(){
        return bankOwner;
    }
    public String getBankOwnerName(){
        return bankOwnerName;
    }
    public UUID getBankOwnerUUID(){
        return bankOwnerUUID;
    }
    public boolean setBankOwner(Player bankOwner){
        if (this.bankOwner == bankOwner){
            return false;
        } else {
            this.bankOwner = bankOwner;
            this.bankOwnerUUID = bankOwner.getUniqueId();
            this.bankOwnerName = bankOwner.getName();
            return true;
        }
    }
    public List<Player> getBankMembers(){
        return bankMemberList;
    }
    public boolean isBankMember(Player player) {
        return bankMemberList.contains(player);
    }
    public boolean addBankMember(Player player){
        if (bankMemberList.contains(player)){
            return false;
        } else {
            bankMemberList.add(player);
            return true;
        }
    }
    public boolean removeBankMember(Player player){
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
    public void incrementBankBalance(double amount){
        this.bankBalance += amount;
    }
    public boolean decrementBankBalance(double amount){
        this.bankBalance -= amount;
        return true;
    }
}
