package model.modes;

import model.GameData;
import model.Player;
import model.modes.goals.PersonalGoal;
import util.Logger;
import util.MapSavedToDisk;

import java.io.*;
import java.util.*;

/**
 * Created by erini02 on 19/10/16.
 */
public class HallOfFame extends MapSavedToDisk<String, Integer> {
    private final GameData gameData;
    private static final String filename = "hall_of_fame";

    
    public HallOfFame(GameData gameData) {
        super(filename);
        this.gameData = gameData;
        readFile();
        if (gameData.getGameMode() != null && !gameData.getGameMode().hasUpdatedHallOfFame()) {
            updateForThisGame();
            writeFile();
            gameData.getGameMode().setUpdatedHallOfFame(true);
        }
    }

    private void updateForThisGame() {
        Map<String, Integer> pts = new HashMap<>();
        for (Map.Entry<String, Player> entry: gameData.getPlayersAsEntrySet()) {
            Integer current = getEntries().get(entry.getKey());
            if (current == null) {
                current = 0;
            }
            int gainedPoints = gameData.getGameMode().getPointsForPlayer(gameData, entry.getValue());
            if (entry.getValue().isASpectator()) {
                Logger.log(entry.getKey() + " was a spectator.");
            } else {
                if (gainedPoints == 0) {
                    PersonalGoal pg = gameData.getGameMode().getTasks().getGoalsForActors().get(entry.getValue());
                    if (pg != null) {
                        if (pg.isCompleted(gameData)) {
                            Logger.log(entry.getKey() + " scored a point from a personal goal!");
                            gainedPoints = 1;
                        } else {
                            Logger.log(entry.getKey() + " got 0 and no PG completed.");
                        }
                    }
                } else {
                    Logger.log(entry.getKey() + " was awarded " + gainedPoints + ".");
                }

                pts.put(entry.getKey(), gainedPoints);
                current = current + gainedPoints;
                getEntries().put(entry.getKey(), current);
            }
        }
        Logger.log("---=== POINTS FOR THIS GAME ===---");
        for (Map.Entry<String, Integer> entry : pts.entrySet()) {
            Logger.log(entry.getKey() + "  " + entry.getValue());
        }
    }


    public String getHTMLTable() {
        StringBuilder strb = new StringBuilder();
        strb.append("<b>Hall of Fame<b><br/><table>");
        List<Map.Entry<String, Integer>> list = new ArrayList<>();
        list.addAll(getEntries().entrySet());

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


    @Override
    protected Integer readValue(Scanner scanner) {
        return scanner.nextInt();
    }

    @Override
    protected String readKey(Scanner scanner) {
        return scanner.next();
    }

    @Override
    protected String StringifyValue(Integer value) {
        return value.toString();
    }

    @Override
    protected String StringifyKey(String key) {
        return key;
    }
}
