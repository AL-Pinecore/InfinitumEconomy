package cn.infinitumstudios.infinitumEconomy.foundation.types;

import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class Wallet{
    private final UUID ownerUUID, currencyUUID, walletUUID;
    private double value;

    public Wallet(OfflinePlayer owner, UUID currencyUUID){
        this(owner.getUniqueId(), currencyUUID, UUID.randomUUID(), 0.0d);
    }

    public Wallet(UUID ownerUUID, UUID currencyUUID, UUID walletUUID, double value){
        this.ownerUUID = ownerUUID;
        this.currencyUUID = currencyUUID;
        this.walletUUID = walletUUID;
        this.value = value;
    }

    public UUID getOwnerID () {
        return ownerUUID;
    }

    public UUID getCurrencyID () {
        return currencyUUID;
    }

    public UUID getWalletID () {
        return walletUUID;
    }

    public double getValue () {
        return value;
    }

    public void setValue (double value) {
        this.value = value;
    }
}
