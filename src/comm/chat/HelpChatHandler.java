package comm.chat;

import model.GameData;
import model.Player;

public class HelpChatHandler extends ChatCommandHandler {
    public HelpChatHandler() {
        super("help");
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest) {
        gameData.getChat().serverSay("Available commands are:");
        gameData.getChat().serverSay("  /ailaw [new law]     - adds law to AI console");
        gameData.getChat().serverSay("  /login               - log in on console (if available)");
        gameData.getChat().serverSay("  /style [on/off]      - turns style customization on/off");
        gameData.getChat().serverSay("  /map [mapname]       - changes the map");
        gameData.getChat().serverSay("  /maps                - Prints current map and a list of available maps");
    }
}
