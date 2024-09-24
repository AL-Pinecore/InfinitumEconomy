package cn.infinitumstudios.infinitumEconomy.foundation.types;

import cn.infinitumstudios.infinitumEconomy.foundation.Currency;
import cn.infinitumstudios.infinitumEconomy.foundation.database.CurrencyDatabase;
import cn.infinitumstudios.infinitumEconomy.foundation.interfaces.IJsonConvertible;
import com.google.gson.JsonObject;

import java.util.Objects;
import java.util.UUID;

public class Account implements IJsonConvertible<Account> {

    /// VaultAPI account UUID
    private UUID accountUUID;

    /// VaultAPI account holder's UUID
    private UUID accountHolder;

    /// Nickname of the economy account
    private String nickname;

    private Wallet wallet;

    public Account(UUID accountHolder, String nickname) {
        this(UUID.randomUUID(), accountHolder, nickname, Wallet.EMPTY);
    }

    public Account(UUID accountUUID, UUID accountHolder, String nickname) {
        this(accountUUID, accountHolder, nickname, Wallet.EMPTY);
    }

    private Account(UUID accountUUID, UUID accountHolder, String nickname, Wallet wallet) {
        this.accountUUID = accountUUID;
        this.accountHolder = accountHolder;
        this.nickname = nickname;
        this.wallet = wallet;
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

    public Wallet getWallet() {
        return wallet;
    }

    public void setBalance(double balance, Currency of) {
        this.wallet.setBalance(balance, of);
    }

    public void setBalance(double balance) {
        this.setBalance(balance, CurrencyDatabase.DEFAULT_CURRENCY);
    }

    public boolean incrementBalance(double amount, Currency of) {
        return this.wallet.incrementBalance(amount, of);
    }

    public boolean incrementBalance(double amount) {
        return this.incrementBalance(amount, CurrencyDatabase.DEFAULT_CURRENCY);
    }

    public boolean decrementBalance(double amount, Currency of) {
        return this.wallet.decrementBalance(amount, of);
    }

    public boolean decrementBalance(double amount) {
        return this.decrementBalance(amount, CurrencyDatabase.DEFAULT_CURRENCY);
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
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("accountUUID", accountUUID.toString());
        jsonObject.addProperty("accountHolder", accountHolder.toString());
        jsonObject.addProperty("nickname", nickname);
        jsonObject.add("wallet", wallet.toJson());
        return jsonObject;
    }

    @Override
    public void fromJson(final JsonObject object) {
        this.accountUUID = UUID.fromString(object.get("accountUUID").getAsString());
        this.accountHolder = UUID.fromString(object.get("accountHolder").getAsString());
        this.nickname = object.get("nickname").getAsString();
        Wallet temp = Wallet.EMPTY;
        temp.fromJson(object.getAsJsonObject("wallet"));
        this.wallet = temp;
    }
}
