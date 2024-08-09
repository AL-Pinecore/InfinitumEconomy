package cn.infinitumstudios.infinitumEconomy.foundation.types;

import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class Cheque {
    private final double chequeWorth;
    private final String chequeOwner;
    private final UUID chequeOwnerUUID;
    private final UUID chequeUUID;

    public Cheque(OfflinePlayer owner, double chequeWorth){
        this.chequeWorth = chequeWorth;
        this.chequeOwner = owner.getName();
        this.chequeOwnerUUID = owner.getUniqueId();
        this.chequeUUID = UUID.randomUUID();
    }

    public double getChequeWorth() {
        return chequeWorth;
    }

    public String getChequeOwner() {
        return chequeOwner;
    }

    public UUID getChequeUUID() {
        return chequeUUID;
    }

    public UUID getChequeOwnerUUID() {
        return chequeOwnerUUID;
    }

}
