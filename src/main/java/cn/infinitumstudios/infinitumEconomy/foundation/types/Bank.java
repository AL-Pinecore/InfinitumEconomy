package cn.infinitumstudios.infinitumEconomy.foundation.types;

import cn.infinitumstudios.infinitumEconomy.foundation.interfaces.IJsonConvertible;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Bank implements IJsonConvertible<Bank> {
    private String name;
    private UUID bankUUID, bankOwner;
    private List<Vault> vaults = new ArrayList<>();

    public Bank(String name, UUID owner) {
        this(name, UUID.randomUUID(), owner);
    }

    private Bank(String name, UUID bankUUID, UUID bankOwner) {
        this.bankUUID = bankUUID;
        this.bankOwner = bankOwner;
        this.name = name;
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

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("bankUUID", bankUUID.toString());
        json.addProperty("bankOwner", bankOwner.toString());
        return json;
    }

    @Override
    public void fromJson(JsonObject object) {
        this.name = object.get("name").getAsString();
        this.bankUUID = UUID.fromString(object.get("bankUUID").getAsString());
        this.bankOwner = UUID.fromString(object.get("bankOwner").getAsString());
    }
}
