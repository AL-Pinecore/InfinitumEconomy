package cn.infinitumstudios.infinitumEconomy.foundation.types;

import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class Cheque{

    // Cheque's value cannot be changed after created.
    private final double value;

    private final UUID chequeOwnerUUID, chequeUUID, chequeCurrencyUUID;

    public Cheque(OfflinePlayer owner, double value, UUID chequeCurrencyUUID){
        this(owner.getUniqueId(), UUID.randomUUID(), value, chequeCurrencyUUID);
    }

    public Cheque(OfflinePlayer owner, UUID chequeUUID, double value, UUID chequeCurrencyUUID){
        this(owner.getUniqueId(), chequeUUID, value, chequeCurrencyUUID);
    }

    public Cheque(UUID ownerUUID, UUID chequeUUID, double value, UUID chequeCurrencyUUID){
        this.value = value;
        this.chequeOwnerUUID = ownerUUID;
        this.chequeUUID = chequeUUID;
        this.chequeCurrencyUUID = chequeCurrencyUUID;
    }

    public double getWorth() {
        // Converted into Universal currency
        return value;
    }

    public UUID getChequeID () {
        return chequeUUID;
    }

    public UUID getOwnerID () {
        return chequeOwnerUUID;
    }

    public UUID getCurrencyID () {
        return chequeCurrencyUUID;
    }
}
