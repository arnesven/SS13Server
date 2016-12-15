package comm;

import model.GameData;
import util.MyStrings;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Scanner;

/**
 * Created by erini02 on 15/12/16.
 */
public class SoundHandler extends AbstractCommandHandler {

    public SoundHandler(GameData gameData) {
        super(gameData);
    }

    @Override
    public boolean handleCommand(String command, String clid, String rest, ObjectOutputStream oos) throws IOException {

          if (command.contains("SOUNDGET")) {
            Scanner scan = new Scanner(rest);
            int clientsIndex = scan.nextInt();
            if (clientsIndex < gameData.getPlayerForClid(clid).getSoundQueue().getCurrentIndex()) {
                List<String> missingMessages = gameData.getPlayerForClid(clid).getSoundQueue().getSoundsFrom(clientsIndex+1);

                oos.writeObject(MyStrings.join(missingMessages));
            }
            return true;
        }

        return false;
    }
}
