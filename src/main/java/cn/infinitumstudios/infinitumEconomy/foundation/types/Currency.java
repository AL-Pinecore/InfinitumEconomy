package cn.infinitumstudios.infinitumEconomy.foundation.types;

import java.util.UUID;

public class Currency {
    private final UUID currencyID;

    // Currency's name were unique in the server. Multiple currency with same name is not allowed.
    // Currency's name cannot be changed once after created.
    private final String name;
    private char symbol;
    private double currencyWorth;

    public Currency(String name, char symbol, double currencyWorth) {
        this(UUID.randomUUID(), name, symbol, currencyWorth);
    }

    public Currency(UUID currencyID, String name, char symbol, double currencyWorth) {
        this.currencyID = currencyID;
        this.name = name;
        this.symbol = symbol;
        this.currencyWorth = currencyWorth;
    }

    public Currency(String name, String pluralName, char symbol) {
        this(name, symbol, 1.0);
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public double getCurrencyWorth() {
        return currencyWorth;
    }

    public void setCurrencyWorth (double currencyWorth) {
        this.currencyWorth = currencyWorth;
    }

    public UUID getCurrencyID (){
        return currencyID;
    }
}
