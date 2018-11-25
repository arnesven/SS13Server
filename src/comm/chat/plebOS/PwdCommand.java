package comm.chat.plebOS;

import model.GameData;
import model.Player;
import model.plebOS.ComputerSystemLogin;

public class PwdCommand extends PlebOSCommandHandler {
    public PwdCommand() {
        super("pwd");
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest, ComputerSystemLogin csl) {
        String path =  csl.getCurrentDirectory();
        gameData.getChat().plebOSSay(path, sender);
    }
}
