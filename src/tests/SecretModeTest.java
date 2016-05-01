package tests;

import junit.framework.TestCase;
import model.GameData;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by erini02 on 19/04/16.
 */
public class SecretModeTest extends TestCase {

    private static final int NO_OF_GAMES = 1;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testSecretMode() {
        Map<String, Integer> freqs = new HashMap<>();

        for (int i = NO_OF_GAMES; i > 0; i--) {
            GameData gameData = new GameData();
            gameData.createNewClient("TestGuy1");
            gameData.createNewClient("TestGuy2");
            gameData.setPlayerReady("TestGuy1", true);
            gameData.setPlayerReady("TestGuy2", true);
            String mode = gameData.getGameMode().getName();
            if (freqs.get(mode) == null) {
                freqs.put(mode, 0);
            } else {
                freqs.put(mode, freqs.get(mode) + 1);
            }
        }

        for (String mode : freqs.keySet()) {
            System.out.println(mode + " " + String.format("%.1f", freqs.get(mode) / 1.0) + "%");
        }

    }
}
