package comm.chat;

import model.GameData;
import model.Player;

public class HelpChatHandler extends ChatCommandHandler {
    public HelpChatHandler() {
        super("help");
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest) {
        gameData.getChat().serverSay("Available commands are:", sender);
        gameData.getChat().serverSay("  /style [on/off]      - turns style customization on/off", sender);
        gameData.getChat().serverSay("  /map [mapname]       - changes the map", sender);
        gameData.getChat().serverSay("  /maps                - Prints current map and a list of available maps", sender);
    }
}