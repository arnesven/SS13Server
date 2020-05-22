package util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class MapSavedToDisk<K, V> {

    private final String filename;
    private Map<K, V> entries = new HashMap<>();

    public MapSavedToDisk(String filename) {
        this.filename = filename;
    }

    protected abstract V readValue(Scanner scanner);

    protected abstract K readKey(Scanner scanner);

    public Map<K, V> getEntries() {
        return entries;
    }

    protected void readFile() {
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNext()) {
                entries.put(readKey(scanner), readValue(scanner));
            }
        } catch (FileNotFoundException e) {
            File f = new File(filename);
            try {
                f.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    protected void writeFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)));
            for (Map.Entry<K, V> entry : entries.entrySet()) {
                writer.write(StringifyKey(entry.getKey()) + "\t" + StringifyValue(entry.getValue()));
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected abstract String StringifyValue(V value);

    protected abstract String StringifyKey(K key);

}
