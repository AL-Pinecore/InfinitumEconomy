package cn.infinitumstudios.infinitumEconomy.foundation.types;

import cn.infinitumstudios.infinitumEconomy.foundation.Currency;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class Cheque {
    private final double chequeWorth;
    private final String chequeOwner;
    private final UUID chequeOwnerUUID;
    private final UUID chequeUUID;
    private final Currency chequeCurrency;

    public Cheque(OfflinePlayer owner, double chequeWorth, Currency chequeCurrency){
        this.chequeWorth = chequeWorth;
        this.chequeOwner = owner.getName();
        this.chequeOwnerUUID = owner.getUniqueId();
        this.chequeUUID = UUID.randomUUID();
        this.chequeCurrency = chequeCurrency;
    }

    public double getWorth() {
        // Converted into Universal currency
        return chequeWorth;
    }

    public String getOwnerName() {
        return chequeOwner;
    }

    public UUID getUUID() {
        return chequeUUID;
    }

    public UUID getOwnerUUID() {
        return chequeOwnerUUID;
    }
    public Currency getCurrency(){
        return chequeCurrency;
    }
}
