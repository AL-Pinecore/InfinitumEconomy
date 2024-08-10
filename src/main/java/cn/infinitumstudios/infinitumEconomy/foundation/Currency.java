package cn.infinitumstudios.infinitumEconomy.foundation;

import cn.infinitumstudios.infinitumEconomy.foundation.interfaces.IJsonConvertible;
import com.google.gson.JsonObject;

import java.util.UUID;

public class Currency implements IJsonConvertible<Currency> {
    private final UUID currencyID;
    private final String name, pluralName, symbol;
    private final double currencyWorth;

    public Currency(String name, String pluralName, String symbol, double currencyWorth) {
        this(UUID.randomUUID(), name, pluralName, symbol, currencyWorth);
    }

    private Currency(UUID currencyID, String name, String pluralName, String symbol, double currencyWorth) {
        this.currencyID = currencyID;
        this.name = name;
        this.pluralName = pluralName;
        this.symbol = symbol;
        this.currencyWorth = currencyWorth;
    }

    public Currency(String name, String pluralName, String symbol) {
        this(name, pluralName, symbol, 1.0);
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getCurrencyWorth() {
        return currencyWorth;
    }

    public String getPluralName() {
        return pluralName;
    }

    public String value(double value, boolean useSymbol){
        if(useSymbol)
            return String.format("%.2f%s", value, getSymbol());
        return String.format("%.2f %s", value, value <= 1 ? getName() : getPluralName());
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("currencyID", currencyID.toString());
        json.addProperty("name", name);
        json.addProperty("pluralName", pluralName);
        json.addProperty("symbol", symbol);
        json.addProperty("currencyWorth", currencyWorth);
        return json;
    }

    @Override
    public Currency fromJson(JsonObject object) {
        return new Currency(UUID.fromString(object.get("currencyID").getAsString()),
                object.get("name").getAsString(),
                object.get("pluralName").getAsString(),
                object.get("symbol").getAsString(),
                object.get("currencyWorth").getAsDouble());
    }

    public UUID getCurrencyID() {
        return currencyID;
    }
}
