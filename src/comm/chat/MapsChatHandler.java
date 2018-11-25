package comm.chat;

import model.GameData;
import model.Player;
import model.map.builders.MapBuilder;
import model.plebOS.ComputerSystemLogin;

public class MapsChatHandler extends ChatCommandHandler {
    public MapsChatHandler() {
        super("maps");
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest) {
        gameData.getChat().serverSay("Current map: \"" + MapBuilder.getSelectedBuilder() + "\"");
        gameData.getChat().serverSay("Available maps:");
        for (String s : MapBuilder.availableMaps()) {
            gameData.getChat().serverSay("   \"" + s + '\"');
        }
    }
}
