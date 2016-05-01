package tests;

import junit.framework.TestCase;
import model.GameData;
import model.items.NoSuchThingException;
import model.objects.consoles.CrimeRecordsConsole;
import model.objects.consoles.GeneratorConsole;
import org.junit.Test;

/**
 * Created by erini02 on 01/05/16.
 */
public class NumberOfPlayersTest extends TestCase {

    @Test
    public void testLargeNumberOfPlayers() throws NoSuchThingException {
        for (int i = 4; i > 0 ; i--) {
            GameData gameData = TestUtil.makeAGame(16);
            gameData.findObjectOfType(GeneratorConsole.class);
        }
    }

    @Test
    public void testFewNumberOfPlayers() throws NoSuchThingException {
        for (int i = 4; i > 0 ; i--) {
            GameData gameData = TestUtil.makeAGame(2);
            gameData.findObjectOfType(GeneratorConsole.class);
        }
    }


}
