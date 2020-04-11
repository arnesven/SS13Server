package comm;

import model.GameData;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class FancyFrameCommandHandler extends AbstractCommandHandler {
    public FancyFrameCommandHandler(GameData gameData) {
        super(gameData);
    }

    @Override
    public boolean handleCommand(String command, String clid, String rest, ObjectOutputStream oos) throws IOException {
        if (command.contains("FANCYFRAME")) {
            if (rest.contains("GET")) {
                oos.writeObject(gameData.getPlayerForClid(clid).getFancyFrame().getData());
            } else if (rest.contains("EVENT")) {
                gameData.getPlayerForClid(clid).getFancyFrame().handleEvent(rest.replace("EVENT ", ""));
                oos.writeObject(gameData.getPlayerForClid(clid).getFancyFrame().getState() + "<part>" + gameData.getPlayerForClid(clid).getFancyFrame().getData());
            }
            return true;
        }
        return false;
    }
}
