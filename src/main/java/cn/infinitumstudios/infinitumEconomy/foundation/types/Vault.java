package cn.infinitumstudios.infinitumEconomy.foundation.types;

import java.util.UUID;

public class Vault {
    private UUID vaultUUID, owner;
    private String name;
    private double value;

    public Vault(UUID owner, String name, double value) {
        this.vaultUUID = UUID.randomUUID();
        this.owner = owner;
        this.name = name;
        this.value = value;
    }

    public UUID getVaultUUID() {
        return vaultUUID;
    }

    public void setVaultUUID(UUID vaultUUID) {
        this.vaultUUID = vaultUUID;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
