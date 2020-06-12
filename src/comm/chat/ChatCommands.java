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
        }
        return false;

    }

    private static List<ChatCommandHandler> fillWithHandlers() {
        List<ChatCommandHandler> list = new ArrayList<>();
        list.add(new MugshotChatHandler());
        list.add(new MapsChatHandler());
        list.add(new ChangeMapChatHandler());
        list.add(new OverRadioSayChatHandler());
        list.add(new InCharacterSayChatHandler());
        list.add(new HelpChatHandler());
        list.add(new LaughChatHandler());
        return list;
    }




}
