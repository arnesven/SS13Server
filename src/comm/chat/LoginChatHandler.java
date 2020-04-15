package comm.chat;

import comm.chat.plebOS.PlebOSCommandHandler;
import model.GameData;
import model.Player;
import model.objects.consoles.Console;
import model.objects.general.GameObject;
import model.plebOS.ComputerSystemSession;

public class LoginChatHandler extends ChatCommandHandler {
    public LoginChatHandler() {
        super("login");
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest) {
        if (sender.getCharacter() != null && sender.getCharacter().isCrew()) {
            if (PlebOSCommandHandler.isLoggedIn(sender)) {
                gameData.getChat().serverSay("You are already logged in.", sender);
            } else {
                boolean foundConsole = false;
                for (GameObject obj : sender.getCharacter().getPosition().getObjects()) {
                    if (obj instanceof Console) {
                        foundConsole = true;
                        Console con = (Console) obj;
                        if (con.isBroken()) {
                            gameData.getChat().serverSay("The console is broken, can't login!", sender);
                        } else if (!con.isPowered()) {
                            gameData.getChat().serverSay("The console has no power, can't login!", sender);
                        } else {
                            gameData.getChat().serverSay("You logged in at the " + obj.getBaseName(), sender);
                            // do fun log in stuff!
                            //new ComputerSystemSession(sender, con, gameData, cff);
                        }
                        break;
                    }
                }
                if (!foundConsole) {
                    gameData.getChat().serverSay("No console to log in on in this place.", sender);
                }
            }
        } else {
            gameData.getChat().serverSay("You cannot login at this time.", sender);
        }
    }
}
