package comm;

import model.GameData;
import util.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class OverlayActionCommandHandler extends AbstractCommandHandler {

    public OverlayActionCommandHandler(GameData gameData) {
        super(gameData);
    }

    @Override
    public boolean handleCommand(String command, String clid,
                                 String rest, ObjectOutputStream oos) throws IOException {
        if (command.equals("OVERLAYACTION")) {
            String actionStr = rest.substring(1, rest.length());
            Logger.log(clid + "'s next (overlay) action is " + actionStr);
            gameData.getPlayerForClid(clid).parseOverlayActionFromString(actionStr, gameData);
            oos.writeObject("ACK");
            return true;
        }
        return false;
    }
}
