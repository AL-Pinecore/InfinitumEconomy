package cn.infinitumstudios.infinitumEconomy.foundation.types;

import cn.infinitumstudios.infinitumEconomy.foundation.interfaces.IJsonConvertible;
import com.google.gson.JsonObject;

import java.util.Objects;
import java.util.UUID;

public class Account implements IJsonConvertible<Account> {
    private final UUID accountUUID;
    private UUID accountHolder;
    private String nickname;
    private double balance;

    public Account(UUID accountHolder, String nickname) {
        this.accountUUID = UUID.randomUUID();
        this.accountHolder = accountHolder;
        this.nickname = nickname;
        this.balance = 0.0;
    }

    private Account(UUID accountUUID, UUID accountHolder, String nickname, double balance) {
        this.accountUUID = accountUUID;
        this.accountHolder = accountHolder;
        this.nickname = nickname;
        this.balance = balance;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return Objects.equals(getAccountUUID(), account.getAccountUUID()) && Objects.equals(getAccountHolder(), account.getAccountHolder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountUUID(), getAccountHolder());
    }

    @Override
    public JsonObject toJson() {
        return null;
    }

    @Override
    public Account fromJson(final JsonObject object) {
        return new Account(UUID.fromString(object.get("uuid").getAsString()), UUID.fromString(object.get("player_uuid").getAsString()), "", 0.0);
    }
}
