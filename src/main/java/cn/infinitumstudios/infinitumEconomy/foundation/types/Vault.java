package cn.infinitumstudios.infinitumEconomy.foundation.types;

import cn.infinitumstudios.infinitumEconomy.foundation.interfaces.IJsonConvertible;
import com.google.gson.JsonObject;

import java.util.UUID;

public class Vault implements IJsonConvertible<Vault> {
    private UUID vaultUUID;
    private UUID owner;
    private final UUID bankUUID;
    private String name;
    private double value;

    public Vault(UUID bankUUID, UUID owner, String name, double value) {
        this(UUID.randomUUID(), bankUUID, owner, name, value);
    }

    public Vault(UUID vaultUUID, UUID bankUUID, UUID owner, String name, double value){
        this.vaultUUID = vaultUUID;
        this.owner = owner;
        this.name = name;
        this.value = value;
        this.bankUUID = bankUUID;
    }

    public UUID getVaultUUID() {
        return vaultUUID;
    }

    public void setVaultUUID(UUID vaultUUID) {
        this.vaultUUID = vaultUUID;
    }

    public UUID getOwnedBank(){
        return bankUUID;
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

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("uuid", vaultUUID.toString());
        jsonObject.addProperty("owner", owner.toString());
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("value", value);
        return jsonObject;
    }

    @Override
    public void fromJson(JsonObject object) {
        this.vaultUUID = UUID.fromString(object.get("uuid").getAsString());
        this.owner = UUID.fromString(object.get("owner").getAsString());
        this.name = object.get("name").getAsString();
        this.value = object.get("value").getAsDouble();
    }
}
