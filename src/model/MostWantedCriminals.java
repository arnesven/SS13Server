package model;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by erini02 on 16/11/16.
 */
public class MostWantedCriminals {

    private static final String PATH = "resources/MOST_WANTED.TXT";

    public static void add(String clidForPlayer) {

        List<String> data = loadData();
        if (!data.contains(clidForPlayer)) {
            data.add(clidForPlayer);
        }
        saveData(data);


    }

    private static void saveData(List<String> data) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(PATH)));
            for (String str : data) {
                writer.write(str);
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> loadData() {
        List<String> data = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(PATH));
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                data.add(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            try {
                (new File(PATH)).createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return data;
    }

    public static List<String> getCriminals() {
        List<String> data = loadData();
        return data;
    }

    public static void remove(String clidForPlayer) {
        List<String> data = loadData();
        data.remove(clidForPlayer);
        saveData(data);
    }
}
