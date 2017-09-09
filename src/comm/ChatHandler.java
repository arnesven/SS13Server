package comm;

import model.GameData;
import util.MyStrings;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Scanner;

/**
 * Created by erini02 on 26/11/16.
 */
public class ChatHandler extends AbstractCommandHandler {

    public ChatHandler(GameData gameData) {
        super(gameData);
    }

    @Override
    public boolean handleCommand(String command, String clid, String rest, ObjectOutputStream oos) throws IOException {
        if (command.contains("CHATPUT")) {
            rest = rest.substring(1);
            boolean wasCommand = gameData.chatWasCommand(rest, clid);
            if (!wasCommand) {
                gameData.getChat().add(clid + ": " + rest);
            }

            oos.writeObject("ACK");
            return true;
        } else if (command.contains("CHATGET")) {
            Scanner scan = new Scanner(rest);
            int clientsIndex = scan.nextInt();
            if (clientsIndex < gameData.getChat().getLastMessageIndex()) {
                List<String> missingMessages = gameData.getChat().getMessagesFrom(clientsIndex+1);

                oos.writeObject(MyStrings.join(missingMessages));
            }
            return true;
        }

        return false;
    }
}
