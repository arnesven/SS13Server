package model.modes;

import model.GameData;
import model.Player;
import model.modes.goals.PersonalGoal;
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
            if (gainedPoints == 0) {
                PersonalGoal pg = gameData.getGameMode().getTasks().getGoalsForActors().get(entry.getValue());
                if (pg != null && pg.isCompleted(gameData)) {
                    Logger.log(entry.getKey() + " scored a point from a personal goal!");
                    gainedPoints = 1;
                } else {
                    Logger.log(entry.getKey() + " got 0 and no PG completed.");
                }
            } else {
                Logger.log(entry.getKey() + " was awarded " + gainedPoints + ".");
            }

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
        List<Map.Entry<String, Integer>> list = new ArrayList<>();
        list.addAll(entries.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue()-o1.getValue();
            }
        });

        for (Map.Entry<String, Integer> entry : list) {
            strb.append("<tr><td>" + entry.getKey() + "</td><td>" + entry.getValue() + "</td></tr>");
        }
        strb.append("</table>");
        return strb.toString();
    }


}
