package cn.infinitumstudios.infinitumEconomy.foundation.types;

import cn.infinitumstudios.infinitumEconomy.foundation.Currency;
import cn.infinitumstudios.infinitumEconomy.foundation.interfaces.IJsonConvertible;
import com.google.gson.JsonObject;

public class Wallet implements IJsonConvertible<Wallet> {
    public static final Wallet EMPTY = new Wallet(0);

    // The wallet balance uses the default currency by default. Operating on other actions will be automatically converted to other currencies.
    private double balance;
    public Wallet(double balance) {
        this.balance = balance;
    }

    public double getBaseBalance() {
        return balance;
    }

    public void setBalance(double amount, Currency of) {
        this.balance = Math.max(0, Currency.convert(amount, of));
    }

    public boolean incrementBalance(double amount, Currency of) {
        this.balance += Currency.convert(amount, of);
        return true;
    }

    public boolean decrementBalance(double amount, Currency of) {
        if(balance - Currency.convert(amount, of) < 0) return false;

        this.balance -= Currency.convert(amount, of);
        this.balance = Math.max(this.balance, 0.0d);

        return true;
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("balance", this.balance);
        return jsonObject;
    }

    @Override
    public void fromJson(JsonObject object) {
        this.balance = object.get("balance").getAsDouble();
    }
}
