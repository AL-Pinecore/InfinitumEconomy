package cn.infinitumstudios.infinitumEconomy.foundation.database;

import cn.infinitumstudios.infinitumEconomy.foundation.interfaces.IJsonConvertible;
import cn.infinitumstudios.infinitumEconomy.utility.Reference;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
    private HashSet<T> items;
    private final String fileName;
    private final File file;
    private final Class<T> classOfT;

    public Database(String fileName, Class<T> classOfT) {
        items = new HashSet<>();
        this.fileName = fileName;
        this.file = new File(Path.of(Reference.DATA_FILES_DIRECTORY.toString(), fileName + ".json").toUri());
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
        JSONArray jsonArray = new JSONArray();
        for (T item : items) {
            jsonArray.add(item.toJson());
        }

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jsonArray.toJSONString());
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

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(file)) {
            Object obj = jsonParser.parse(reader);
            JSONArray jsonArray = (JSONArray) obj;

            items.clear();
            for (Object jsonObj : jsonArray) {
                T item = createItemFromJson((JSONObject) jsonObj);
                if (item != null) {
                    items.add(item);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private T createItemFromJson(JSONObject jsonObject) {
        try {
            T item = classOfT.getDeclaredConstructor().newInstance();
            return item.fromJson(jsonObject);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
