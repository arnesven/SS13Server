package comm;

import java.io.IOException;
import java.io.ObjectOutputStream;

import model.GameData;
import model.GameState;
import util.Logger;


public class IDMessageHandler implements MessageHandler {

    private GameData gameData;

    public IDMessageHandler(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public boolean handle(String message, ObjectOutputStream oos) throws IOException {
        if (message.contains("IDENT ME")) {
            boolean spec = false;
            if (message.contains("SPECTATOR")) {
                spec = true;
                message = message.replace(" SPECTATOR", "");
            }

            try {
                String clid = gameData.createNewClient(message.replace("IDENT ME", ""), spec);
                Logger.log("This new dude(ette) calls shimself " + clid);
                oos.writeObject(clid + ":" + gameData.getPlayerForClid(clid).getSoundQueue().getCurrentIndex());
            } catch (IllegalStateException ise) {
                Logger.log("That ID already existed!");
                oos.writeObject("ERROR" + ise.getMessage());
                return true;
            }

            return true;

        }

        return false;
    }

}
