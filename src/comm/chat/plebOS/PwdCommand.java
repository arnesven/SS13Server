package comm.chat.plebOS;

import model.GameData;
import model.Player;
import model.plebOS.ComputerSystemSession;

public class PwdCommand extends PlebOSCommandHandler {
    public PwdCommand() {
        super("pwd");
    }

    @Override
    protected boolean doesReadyUser() {
        return false;
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest, ComputerSystemSession csl) {
        String path =  csl.getCurrentDirectory().getPath();
        gameData.getChat().plebOSSay(path, sender);
    }
}
