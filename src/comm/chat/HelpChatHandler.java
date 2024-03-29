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
        gameData.getChat().serverSay("  /insay [message]     - say something in character, NOT over the radio (heard in room)", sender);
        gameData.getChat().serverSay("  /rinsay [message]    - say something in character over the radio (heard by all)", sender);
        gameData.getChat().serverSay("  /laugh               - laugh out loud", sender);
        gameData.getChat().serverSay("  /mugshot             - take a mugshot of your current character", sender);
        gameData.getChat().serverSay("  /map [mapname]       - changes the map", sender);
        gameData.getChat().serverSay("  /maps                - Prints current map and a list of available maps", sender);
    }
}
