package tests;

import junit.framework.TestCase;
import model.GameData;
import model.items.NoSuchThingException;
import model.items.general.BoobyTrapBomb;
import model.map.Room;
import model.objects.StasisPod;
import model.objects.consoles.*;
import model.objects.general.BoobyTrappedObject;
import model.objects.general.Dumbwaiter;
import model.objects.general.EvidenceBox;
import model.objects.general.GameObject;
import org.junit.Test;

/**
 * Created by erini02 on 01/05/16.
 */
public class FindObjectTest extends TestCase {

    @Test
    public void testFindCrimeConsole() throws NoSuchThingException {
        GameData gameData = TestUtil.makeAGame(2);

        gameData.findObjectOfType(CrimeRecordsConsole.class);
    }

    @Test
    public void testFindCrimeConsoleWhenBoobyTrapped() throws NoSuchThingException {
        GameData gameData = TestUtil.makeAGame(2);

        CrimeRecordsConsole crc = gameData.findObjectOfType(CrimeRecordsConsole.class);
        Room secRoom = crc.getPosition();

        secRoom.getObjects().remove(crc);
        secRoom.addObject(new BoobyTrappedObject(crc, new BoobyTrapBomb(),
                gameData.getPlayerForClid(TestUtil.getPlayerName(0)), secRoom));

        gameData.findObjectOfType(CrimeRecordsConsole.class);

    }


    @Test
    public void testFindGeneratorConsole() throws NoSuchThingException {
        GameData gameData = TestUtil.makeAGame(2);

        gameData.findObjectOfType(GeneratorConsole.class);
    }

    @Test
    public void testFindVariousObjects() throws NoSuchThingException {
        GameData gameData = TestUtil.makeAGame(2);

        Class<? extends GameObject>[] classes = new Class[]{
                BotConsole.class, StasisPod.class, AIConsole.class, AdministrationConsole.class,
                Dumbwaiter.class, EvidenceBox.class
        };

        for (Class<? extends GameObject> className : classes) {
            gameData.findObjectOfType(className);
        }

    }
}
