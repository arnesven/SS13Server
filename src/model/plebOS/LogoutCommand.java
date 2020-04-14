package model.plebOS;

import comm.chat.plebOS.PlebOSCommandHandler;
import model.GameData;
import model.Player;

public class LogoutCommand extends PlebOSCommandHandler {
    public LogoutCommand() {
        super("logout");
    }

    @Override
    protected boolean doesReadyUser() {
        return false;
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest, ComputerSystemSession loginInstance) {
        loginInstance.logOut(gameData);
    }
}
