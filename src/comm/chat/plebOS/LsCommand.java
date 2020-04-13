package comm.chat.plebOS;

import model.GameData;
import model.Player;
import model.plebOS.ComputerSystemSession;
import model.plebOS.FileSystemNode;

public class LsCommand extends PlebOSCommandHandler {
    public LsCommand() {
        super("ls");
    }

    @Override
    protected boolean doesReadyUser() {
        return false;
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest, ComputerSystemSession loginInstance) {
        loginInstance.getConsole().plebOSSay(".", sender);
        loginInstance.getConsole().plebOSSay("..", sender);
        for (FileSystemNode fsn : loginInstance.getCurrentDirectory().getContents()) {
            loginInstance.getConsole().plebOSSay(fsn.getName(), sender);
        }
    }

}
