package cn.infinitumstudios.infinitumEconomy.foundation;

public class Currency {
    // Currency Name and Symbol cannot be changed after created.
    private final String currencyName;
    private float ratio;
    private final String currencySymbol;
    public Currency(String currencyName, String currencySymbol, float ratio){
        this.currencyName = currencyName;
        this.currencySymbol = currencySymbol;
        this.ratio = ratio;
    }
    public Currency(String currencyName, String currencySymbol){
        this.currencyName = currencyName;
        this.currencySymbol = currencySymbol;
        this.ratio = 1;
    }
    public float getRatio(){
        return ratio;
    }

    public boolean setRatio(float newRatio){
        this.ratio = newRatio;
        return true;
    }
}
