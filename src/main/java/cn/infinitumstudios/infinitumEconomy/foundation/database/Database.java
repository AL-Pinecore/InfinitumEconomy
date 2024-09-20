package cn.infinitumstudios.infinitumEconomy.foundation.database;

import cn.infinitumstudios.infinitumEconomy.foundation.interfaces.IJsonConvertible;
import cn.infinitumstudios.infinitumEconomy.foundation.types.Account;
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
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class Database<T extends IJsonConvertible<T>> {
    private static List<Database> cachedInstances = new ArrayList<>();
    protected HashSet<T> items;
    private final String fileName;
    private final File file;
    private final Class<T> classOfT;

    public Database(String fileName, Class<T> classOfT) {
        this(fileName, classOfT, new File(fileName + ".json"));
        cachedInstances.add(this);
    }

    public Database(String fileName, Class<T> classOfT, File file) {
        items = new HashSet<>();
        this.fileName = fileName;
        this.file = file;
        this.classOfT = classOfT;
    }

    public static void loadAll(){
        cachedInstances.forEach(Database::load);
    }

    /**
     * Adds a new item to the database.
     *
     * @param item The item to be added to the database.
     */
    // Create
    protected void create(T item) {
        items.add(item);
    }

    /**
     * Returns an Optional object containing the first element in the items collection that satisfies the given condition, or an empty Optional if no matching element is found.
     *
     * @param condition the Predicate function used to filter the items collection
     * @return an Optional object containing the first matching element, or an empty Optional if no matching element is found
     */
    // Read
    public Optional<T> read(Predicate<T> condition) {
        return items.stream().filter(condition).findFirst();
    }

    /**
     * Returns a list of all items in the database.
     *
     * @return a list of all items in the database
     */
    public List<T> readAll() {
        return new ArrayList<>(items);
    }

    /**
     * Returns a list of elements from the database that satisfy the given condition.
     *
     * @param condition the Predicate function used to filter the items collection
     * @return a list of elements from the database that satisfy the condition
     */
    public List<T> readWhere(Predicate<T> condition) {
        return items.stream().filter(condition).collect(Collectors.toList());
    }

    /**
     * Updates an item in the database that matches the given condition.
     *
     * @param condition A predicate to find the item to update.
     * @param newItem The new item to replace the existing item.
     * @return true if an item was found and updated, false otherwise.
     */
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

    /**
     * Updates an item in the database that matches the given condition.
     * @param condition A predicate to find the item to update.
     * @param updater A function that takes the found item and returns an updated version (or null to cancel the update).
     * @return true if an item was found and updated, false otherwise.
     */
    protected boolean update(Predicate<T> condition, UnaryOperator<T> updater) {
        Optional<T> item = read(condition);
        if (item.isPresent()) {
            T updatedItem = updater.apply(item.get());
            if (updatedItem != null) {
                items.remove(item.get());
                items.add(updatedItem);
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes an item from the database that matches the given condition.
     *
     * @param condition A predicate used to filter the items collection to find the item to delete.
     * @return true if an item was found and deleted, false otherwise.
     */
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

    /**
     * Deletes multiple items from the database that satisfy the given condition.
     *
     * @param condition A predicate used to filter the items collection to find the items to delete.
     */
    public void deleteBatch(Predicate<T> condition) {
        items.removeIf(condition);
    }

    /**
     * Saves the collection of items to a file.
     */
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

    /**
     * Loads data from a file into the database.
     * If the file does not exist, nothing is loaded.
     *
     * @throws Exception if an error occurs while loading data from the file
     */
    // Load from file
    public void load() {
        if (!file.exists()) {
            createDefaultFile();
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
            item.fromJson(jsonObject);
            return item;
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createDefaultFile() {
        try (FileWriter fileWriter = new FileWriter(file)) {
            JsonObject obj = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            obj.add("items", jsonArray);
            fileWriter.write(obj.toString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
