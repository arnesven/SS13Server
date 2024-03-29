package util;

import model.GameData;

import java.io.*;

/**
 * Created by erini02 on 30/04/16.
 */
public class GameRecovery {

    private static final String PATH = "recovery_data";

    public static GameData recover() throws IOException, ClassNotFoundException {

        ObjectInputStream oyester = new ObjectInputStream(
                new FileInputStream(new File(PATH)));
         GameData gd = (GameData)oyester.readObject();
         gd.doAfterRecovery();

        return gd;
    }

    public static void saveData(GameData gameData) throws IOException {
        ObjectOutputStream rooster = new ObjectOutputStream(
                new FileOutputStream(new File(PATH)));
        rooster.writeObject(gameData);
        rooster.close();
    }

    public static boolean removeDataFile() {
        return new File(PATH).delete();
    }
}
