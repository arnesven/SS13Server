package comm;

import model.GameData;
import model.Player;
import util.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class FancyFrameCommandHandler extends AbstractCommandHandler {
    public FancyFrameCommandHandler(GameData gameData) {
        super(gameData);
    }

    @Override
    public boolean handleCommand(String command, String clid, String rest, ObjectOutputStream oos) throws IOException {
        Player pl = gameData.getPlayerForClid(clid);
        if (command.contains("FANCYFRAME")) {
            if (rest.contains("GET")) {
                oos.writeObject(pl.getFancyFrame().getData());
            } else if (rest.contains("EVENT")) {
                pl.getFancyFrame().handleEvent(gameData, pl, rest.replace(" EVENT ", ""));
                respondWithNewData(oos, gameData, clid);
            } else if (rest.contains("INPUT")) {
                String data = rest.replace(" INPUT ", "");
                pl.getFancyFrame().handleInput(gameData, pl, data);
                respondWithNewData(oos, gameData, clid);
            } else if (rest.contains("CLICK")) {
                String data = rest.replace(" CLICK ", "");
                Scanner scan = new Scanner(data);
                pl.getFancyFrame().handleClick(gameData, pl, scan.nextInt(), scan.nextInt());
                respondWithNewData(oos, gameData, clid);
            } else {
                Logger.log(Logger.CRITICAL, "Unknown type of fancy frame command " + rest);
            }
            return true;
        }
        return false;
    }

    private void respondWithNewData(ObjectOutputStream oos, GameData gameData, String clid) throws IOException {
        oos.writeObject(gameData.getPlayerForClid(clid).getFancyFrame().getState() + "<part>" + gameData.getPlayerForClid(clid).getFancyFrame().getData());
    }
}
