package cn.infinitumstudios.infinitumEconomy.foundation;

public class Currency {
    private final String name, symbol;
    private final double ratio;
    public Currency(String name, String symbol, double ratio) {
        this.name = name;
        this.symbol = symbol;
        this.ratio = ratio;
    }

    public Currency(String name, String symbol) {
        this(name, symbol, 1.0);
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getRatio() {
        return ratio;
    }
}
