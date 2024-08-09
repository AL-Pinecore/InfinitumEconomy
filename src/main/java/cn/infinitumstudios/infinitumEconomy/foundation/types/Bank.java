package cn.infinitumstudios.infinitumEconomy.foundation.types;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Bank {
    private String name;
    private UUID bankUUID, bankOwner;
    private List<Vault> vaults = new ArrayList<>();

    public Bank(String name, UUID owner) {
        this.bankUUID = UUID.randomUUID();
        this.name = name;
        this.bankOwner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getBankOwner() {
        return bankOwner;
    }

    public void setBankOwner(UUID owner) {
        this.bankOwner = owner;
    }

    public List<Vault> getVaults() {
        return vaults;
    }

    public void setVaults(List<Vault> vaults) {
        this.vaults = vaults;
    }

    public UUID getBankUUID() {
        return bankUUID;
    }

    public void setBankUUID(UUID uuid) {
        this.bankUUID = uuid;
    }
}
