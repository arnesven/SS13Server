package comm;

import graphics.sprites.SpriteManager;
import model.GameData;
import shared.SoundManager;
import sounds.ServerSoundManager;
import util.Logger;

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
            if (rest.contains("SOUND")) {
                rest = rest.replace(" SOUND ", "");
                if (rest.length() > 0) {
                    String str = ServerSoundManager.getBase64String(rest);
                    oos.writeObject(str);
                    return true;
                } else {
                    Logger.log(Logger.CRITICAL, "Something went wrong when getting SOUND resource, rest was: " + rest);
                    return false;
                }
            } else {

                if (rest.length() > 0) {
                    String str = SpriteManager.getSpriteAsBas64(rest);

                    oos.writeObject(str);
                    return true;
                } else {
                    Logger.log(Logger.CRITICAL, "Something went wrong when getting resource, rest was: " + rest);
                    return false;
                }
            }
        }
        return false;
    }
}
