package cn.infinitumstudios.infinitumEconomy.foundation.database;

import cn.infinitumstudios.infinitumEconomy.foundation.interfaces.IJsonConvertible;
import cn.infinitumstudios.infinitumEconomy.utility.Reference;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Database<T extends IJsonConvertible<T>> {
    protected HashSet<T> items;
    private final String fileName;
    private final File file;
    private final Class<T> classOfT;

    public Database(String fileName, Class<T> classOfT) {
        this(fileName, classOfT, new File(fileName + ".json"));
    }

    public Database(String fileName, Class<T> classOfT, File file) {
        items = new HashSet<>();
        this.fileName = fileName;
        this.file = file;
        this.classOfT = classOfT;
    }

    // Create
    protected void create(T item) {
        items.add(item);
    }

    // Read
    public Optional<T> read(Predicate<T> condition) {
        return items.stream().filter(condition).findFirst();
    }

    public List<T> readAll() {
        return new ArrayList<>(items);
    }

    public List<T> readWhere(Predicate<T> condition) {
        return items.stream().filter(condition).collect(Collectors.toList());
    }

    // Update
    protected boolean update(Predicate<T> condition, T newItem) {
        Optional<T> item = read(condition);
        if (item.isPresent()) {
            items.remove(item.get());
            items.add(newItem);
            return true;
        }
        return false;
    }

    // Delete
    protected boolean delete(Predicate<T> condition) {
        Optional<T> item = read(condition);
        return item.map(items::remove).orElse(false);
    }

    // Other utility methods
    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }

    public boolean contains(T item) {
        return items.contains(item);
    }

    public String getFileName() {
        return fileName;
    }

    // Batch operations
    public void createBatch(List<T> newItems) {
        items.addAll(newItems);
    }

    public void deleteBatch(Predicate<T> condition) {
        items.removeIf(condition);
    }

    // Save to file
    public void save() {
        JsonObject obj = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (T item : items) {
            jsonArray.add(item.toJson());
        }
        obj.add("items", jsonArray);

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(obj.toString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load from file
    public void load() {
        if (!file.exists()) {
            return; // File doesn't exist yet, nothing to load
        }

        try (FileReader reader = new FileReader(file)) {
            JsonElement parent = JsonParser.parseReader(reader);

            items.clear();
            for (var jsonObj : parent.getAsJsonObject().getAsJsonArray("items")) {
                T item = createItemFromJson(jsonObj.getAsJsonObject());
                if (item != null) {
                    items.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private T createItemFromJson(JsonObject jsonObject) {
        try {
            T item = classOfT.getDeclaredConstructor().newInstance();
            return item.fromJson(jsonObject);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
