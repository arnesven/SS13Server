package comm;

import model.GameData;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * Created by erini02 on 23/12/16.
 */
public class ServerInfoCommandHandler extends AbstractCommandHandler {

    private final ServerInfoFactory infoFac;

    public ServerInfoCommandHandler(GameData gameData, ServerInfoFactory infoFac) {
        super(gameData);
        this.infoFac = infoFac;
    }

    @Override
    public boolean handleCommand(String command, String clid, String rest, ObjectOutputStream oos) throws IOException {

        if (command.contains("INFOGET")) {

            oos.writeObject(infoFac.getInfoHTML());
            return true;
        }


        return false;
    }
}
