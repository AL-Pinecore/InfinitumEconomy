package cn.infinitumstudios.infinitumEconomy.foundation.types;

import java.util.UUID;

public class Account {
    private UUID accountUUID, accountHolder;
    private String nickname;
    private double balance;

    public Account(UUID accountHolder, String nickname) {
        this.accountUUID = UUID.randomUUID();
        this.accountHolder = accountHolder;
        this.nickname = nickname;
        this.balance = 0.0;
    }

    public Account(UUID accountHolder) {
        this(accountHolder, "");
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public UUID getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(UUID accountHolder) {
        this.accountHolder = accountHolder;
    }

    public UUID getAccountUUID() {
        return accountUUID;
    }

    public void setAccountUUID(UUID accountUUID) {
        this.accountUUID = accountUUID;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = Math.max(0, balance);
    }

    public boolean incrementBalance(double amount) {
        this.balance += amount;
        return true;
    }

    public boolean decrementBalance(double amount) {
        if(balance - amount < 0) return false;

        this.balance -= amount;
        this.balance = Math.max(this.balance, 0.0d);

        return true;
    }
}
