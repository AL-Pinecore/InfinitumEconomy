package cn.infinitumstudios.infinitumEconomy.foundation.interfaces;


import com.google.gson.JsonObject;

public interface IJsonConvertible<T> {
    public JsonObject toJson();
    public T fromJson(final JsonObject object);
}
