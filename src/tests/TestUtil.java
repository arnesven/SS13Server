package tests;

import model.GameData;
import util.Logger;

/**
 * Created by erini02 on 01/05/16.
 */
public class TestUtil {

    private static final String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static GameData makeAGame(int noOfPlayers) {
        util.Logger.setLevel(Logger.CRITICAL);

        GameData gameData = new GameData(false);

        // Players join game
        for (int i = 0; i < noOfPlayers; ++i) {
            gameData.createNewClient(getPlayerName(i), false);
        }
        // players are ready
        for (int i = 0; i < noOfPlayers; ++i) {
            gameData.setPlayerReady(getPlayerName(i), true);
        }
        return gameData;
    }

    public static String getPlayerName(int i) {
        return "Test" + alpha.charAt(i);
    }
}
