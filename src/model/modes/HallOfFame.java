package model.modes;

import model.GameData;
import model.Player;
import util.Logger;

import java.io.*;
import java.util.*;

/**
 * Created by erini02 on 19/10/16.
 */
public class HallOfFame {
    private final GameData gameData;
    private static final String filename = "hall_of_fame";
    private Map<String, Integer> entries = new HashMap<>();

    
    public HallOfFame(GameData gameData) {
        this.gameData = gameData;
        readFile();
        if (!gameData.getGameMode().hasUpdatedHallOfFame()) {
            updateForThisGame();
            writeFile();
            gameData.getGameMode().setUpdatedHallOfFame(true);
        }
    }



    private void updateForThisGame() {
        for (Map.Entry<String, Player> entry: gameData.getPlayersAsEntrySet()) {
            Integer current = entries.get(entry.getKey());
            if (current == null) {
                current = 0;
            }
            int gainedPoints = gameData.getGameMode().getPointsForPlayer(gameData, entry.getValue());
            Logger.log(entry.getKey() + " was awarded " + gainedPoints + ".");
            current = current + gainedPoints;
            entries.put(entry.getKey(), current);
        }
    }

    private void readFile() {
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNext()) {
                entries.put(scanner.next(), scanner.nextInt());
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

    private void writeFile() {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)));
            for (Map.Entry<String, Integer> entry : entries.entrySet()) {
                writer.write(entry.getKey() + "\t" + entry.getValue());
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getHTMLTable() {
        StringBuilder strb = new StringBuilder();
        strb.append("<b>Hall of Fame<b><br/><table>");
        SortedMap<Integer, String> ordered = new TreeMap<>(Collections.reverseOrder());
        for (String name : entries.keySet()) {
            ordered.put(entries.get(name), name);
        }
        for (Map.Entry<Integer, String> entry : ordered.entrySet()) {
            strb.append("<tr><td>" + entry.getValue() + "</td><td>" + entry.getKey() + "</td></tr>");
        }
        strb.append("</table>");
        return strb.toString();
    }


}
