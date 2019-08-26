package comm;

import model.GameData;
import util.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class InventoryActionCommandHandler extends AbstractCommandHandler {

    public InventoryActionCommandHandler(GameData gameData) {
        super(gameData);
    }

    @Override
    public boolean handleCommand(String command, String clid, String rest, ObjectOutputStream oos) throws IOException {
        if (command.equals("INVENTORYACTION")) {
            String actionStr = rest.substring(1, rest.length());
            Logger.log(clid + "'s next (inventory) action is " + actionStr);
            gameData.getPlayerForClid(clid).parseInventoryActionFromString(actionStr, gameData);
            oos.writeObject("ACK");
            return true;
        }


        return false;
    }
}
