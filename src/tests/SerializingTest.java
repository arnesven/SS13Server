package tests;

import junit.framework.TestCase;
import model.GameData;
import model.Player;
import org.junit.Test;
import util.GameRecovery;
import util.Logger;

import java.io.IOException;

/**
 * Created by erini02 on 30/04/16.
 */
public class SerializingTest extends TestCase {

    @Test
    public void testSerializing() {

        util.Logger.setLevel(Logger.CRITICAL);

        GameData gameData = TestUtil.makeAGame(2);

        Player player1 = gameData.getPlayerForClid(TestUtil.getPlayerName(0));
        Player player2 = gameData.getPlayerForClid(TestUtil.getPlayerName(1));

        player1.setNextMove(player1.getPosition().getNeighbors()[0]);
        player2.setNextMove(player2.getPosition().getNeighbors()[0]);

        gameData.setPlayerReady("TestA", true);
        gameData.setPlayerReady("TestB", true);

        player1.parseActionFromString("root,Do Nothing", gameData);
        player2.parseActionFromString("root,Do Nothing", gameData);

        gameData.setPlayerReady("TestA", true);
        gameData.setPlayerReady("TestB", true);
    }

    @Test
    public void testDeserializing() throws IOException, ClassNotFoundException {

        util.Logger.setLevel(Logger.CRITICAL);

        GameData gameData = GameRecovery.recover();

    }

}
