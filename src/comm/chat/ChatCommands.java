package comm.chat;

import comm.chat.plebOS.PlebOSCommandHandler;
import model.GameData;
import model.GameState;
import model.Player;
import model.plebOS.ComputerSystemSession;
import util.HTMLText;

import java.util.ArrayList;
import java.util.List;

public class ChatCommands {

    private static List<ChatCommandHandler> commandHandlers = fillWithHandlers();
    //private static List<ChatCommandHandler> plebOSCommands = ComputerSystem.getAllPlebosCommands();


    public static boolean chatWasCommand(GameData gameData, String rest, String clid) {
        Player sender = gameData.getPlayerForClid(clid);

        if (rest.startsWith("/")) {
            for (ChatCommandHandler cch : commandHandlers) {
                if (cch.handle(gameData, sender, rest)) {
                    return true;
                }
            }
            gameData.getChat().serverSay("Unknown command \"" + rest + "\".");
            return true;
        } else if (rest.startsWith("$")) {
            if (sender.getCharacter() == null || !PlebOSCommandHandler.isLoggedIn(sender)) {
                gameData.getChat().serverSay("You are not logged in at a console." , sender);
                return true;
            }
            ComputerSystemSession login = ComputerSystemSession.getLogin(sender);
            for (ChatCommandHandler posc : login.getAvailableCommands()) {
                if (posc.handle(gameData, sender, rest)) {
                   return true;
                }
            }
            if (PlebOSCommandHandler.isLoggedIn(sender)) {
                gameData.getChat().plebOSSay(rest, sender);
                gameData.getChat().plebOSSay("Unknown command \"" + rest.replace("$", "")
                        + "\".", sender);
            }
            return true;
        } else if (gameData.getGameState() == GameState.ACTIONS) {
            for (Player p : gameData.getPlayersAsList()) {
                p.addTolastTurnInfo(HTMLText.makeText("purple", clid + ": " + rest));
            }
        } else if (gameData.getGameState() == GameState.MOVEMENT) {
            gameData.addToLostMessages(HTMLText.makeText("purple", clid + ": " + rest));

        }
        return false;

    }

    private static List<ChatCommandHandler> fillWithHandlers() {
        List<ChatCommandHandler> list = new ArrayList<>();
        //list.add(new StyleChatHandler());
        list.add(new MapsChatHandler());
        list.add(new ChangeMapChatHandler());
        list.add(new HelpChatHandler());
        return list;
    }




}
