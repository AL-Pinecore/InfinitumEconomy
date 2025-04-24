package cn.infinitumstudios.infinitumEconomy.foundation.types;

import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class Vault{
    private final UUID vaultUUID, currencyUUID;
    private UUID bankUUID;
    private double value;

    public Vault(UUID bankUUID, UUID currencyUUID, double value) {
        this(UUID.randomUUID(), bankUUID, currencyUUID, value);
    }

    public Vault(UUID vaultUUID, UUID bankUUID, UUID currencyUUID, double value){
        this.vaultUUID = vaultUUID;
        this.currencyUUID = currencyUUID;
        this.value = value;
        this.bankUUID = bankUUID;
    }

    public UUID getVaultID () {
        return vaultUUID;
    }

    public UUID getOwnedBank(){
        return bankUUID;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setBankID (UUID bankUUID){
        this.bankUUID = bankUUID;
    }

    public UUID getCurrencyID () {
        return currencyUUID;
    }
}
