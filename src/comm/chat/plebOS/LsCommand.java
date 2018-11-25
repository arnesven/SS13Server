package comm.chat.plebOS;

import model.GameData;
import model.Player;
import model.plebOS.ComputerSystemLogin;

public class LsCommand extends PlebOSCommandHandler {
    public LsCommand() {
        super("ls");
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest, ComputerSystemLogin csl) {
        gameData.getChat().plebOSSay("alarms", sender);
    }
}
