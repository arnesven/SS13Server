package comm.chat;

import model.GameData;
import model.Player;
import model.objects.consoles.Console;
import model.objects.general.GameObject;

public class LoginChatHandler extends ChatCommandHandler {
    public LoginChatHandler() {
        super("login");
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest) {
        if (sender.getCharacter() != null) {
            boolean foundConsole = false;
            for (GameObject obj : sender.getCharacter().getPosition().getObjects()) {
                if (obj instanceof Console) {
                    foundConsole = true;
                    Console con = (Console)obj;
                    if (con.isBroken()) {
                        gameData.getChat().serverSay("The console is broken, can't login!");
                    } else if (!con.isPowered(gameData)) {
                        gameData.getChat().serverSay("The console has no power, can't login!");
                    } else {
                        gameData.getChat().serverSay("You logged in at the " + obj.getBaseName());
                        // do fun log in stuff!
                    }
                    break;
                }
            }
            if (!foundConsole) {
                gameData.getChat().serverSay("No console to log in on in this place.");
            }
        } else {
            gameData.getChat().serverSay("You cannot login at this time.");
        }
    }
}
