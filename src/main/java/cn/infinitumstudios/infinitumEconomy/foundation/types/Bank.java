package cn.infinitumstudios.infinitumEconomy.foundation.types;

import java.util.UUID;

public class Bank{
    // Bank's name were unique in the server. Multiple banks with same name is not allowed.
    private String name;
    private final UUID bankUUID, bankOwnerUUID;

    public Bank(String name, UUID owner) {
        this(name, UUID.randomUUID(), owner);
    }

    public Bank(String name, UUID bankUUID, UUID bankOwnerUUID) {
        this.bankUUID = bankUUID;
        this.bankOwnerUUID = bankOwnerUUID;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Bank's name were unique in the server. Multiple banks with same name is not allowed.
    public void setName(String name) {
        this.name = name;
    }

    public UUID getBankOwnerID () {
        return bankOwnerUUID;
    }

    public UUID getBankID () {
        return bankUUID;
    }
}
