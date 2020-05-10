package comm;

import model.GameData;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ModesCommandHandler extends AbstractCommandHandler {
    public ModesCommandHandler(GameData gameData) {
        super(gameData);
    }

    @Override
    public boolean handleCommand(String command, String clid, String rest, ObjectOutputStream oos) throws IOException {
        if (command.equals("MODES")) {
            if (rest.contains("LOAD")) {
                oos.writeObject(gameData.getModesInfo());
            } else {
                oos.writeObject("ACK");
            }

            return true;
        }

        return false;
    }
}
