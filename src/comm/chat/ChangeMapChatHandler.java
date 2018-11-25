package comm.chat;

import model.GameData;
import model.Player;
import model.map.builders.MapBuilder;
import model.plebOS.ComputerSystemLogin;

public class ChangeMapChatHandler extends ChatCommandHandler {
    public ChangeMapChatHandler() {
        super("map");
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest) {
        String requestedMap = rest.replace("/map ", "");
        if (!MapBuilder.availableMaps().contains(requestedMap)) {
            gameData.getChat().serverSay("No such map available.");
        } else {
            MapBuilder.setSelectedBuilder(requestedMap);
            gameData.getChat().serverSay("Map set to \"" + requestedMap + "\"");
        }
    }
}
