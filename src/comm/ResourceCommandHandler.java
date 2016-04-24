package comm;

import graphics.SpriteManager;
import model.GameData;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by erini02 on 21/04/16.
 */
public class ResourceCommandHandler extends  AbstractCommandHandler {
    public ResourceCommandHandler(GameData gameData) {
        super(gameData);
    }

    @Override
    public boolean handleCommand(String command, String clid, String rest, ObjectOutputStream oos) throws IOException {
        if (command.equals("RESOURCE")) {
            String str = SpriteManager.getSpriteAsBas64(rest);

            oos.writeObject(str);
            return true;
        }
        return false;
    }
}
