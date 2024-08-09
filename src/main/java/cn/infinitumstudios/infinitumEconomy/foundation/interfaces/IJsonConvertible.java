package cn.infinitumstudios.infinitumEconomy.foundation.interfaces;

import org.json.simple.JSONObject;

public interface IJsonConvertible<T> {
    public JSONObject toJson();
    public T fromJson(JSONObject object);
}
