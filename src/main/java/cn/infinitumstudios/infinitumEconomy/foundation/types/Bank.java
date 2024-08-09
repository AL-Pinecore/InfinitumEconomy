package cn.infinitumstudios.infinitumEconomy.foundation.types;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Bank {
    private String name;
    private UUID bankUUID, bankOwner;
    private List<UUID> users = new ArrayList<>();
    private double balance;

    public Bank(String name, UUID owner, double balance) {
        this.bankUUID = UUID.randomUUID();
        this.name = name;
        this.bankOwner = owner;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getBankOwner() {
        return bankOwner;
    }

    public void setBankOwner(UUID owner) {
        this.bankOwner = owner;
    }

    public List<UUID> getUsers() {
        return users;
    }

    public void setUsers(List<UUID> users) {
        this.users = users;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public UUID getBankUUID() {
        return bankUUID;
    }

    public void setBankUUID(UUID uuid) {
        this.bankUUID = uuid;
    }
}
