package model.plebOS;

import comm.chat.plebOS.PlebOSCommandHandler;
import model.GameData;
import model.Player;

public class CdCommand extends PlebOSCommandHandler {
    public CdCommand() {
        super("cd");
    }

    @Override
    protected boolean doesReadyUser() {
        return false;
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest, ComputerSystemSession loginInstance) {
        rest = rest.replace("$", "");
        if (rest.equals("cd") || rest.equals("cd /")) {
            loginInstance.setCurrentDirectory(gameData.getComputerSystem().getRootDirectory());
        } else if (rest.equals("cd .")) {
            // don't do anything!
        } else if (rest.equals("cd ..")) {
            loginInstance.setCurrentDirectory(loginInstance.getCurrentDirectory().getParent());
        } else {
            String path = rest.replace("cd ", "");
            boolean found = false;
            for (FileSystemNode fsn : loginInstance.getCurrentDirectory().getContents()) {
                if (fsn.getName().equals(path)) {
                    if (! (fsn instanceof Directory)) {
                        gameData.getChat().plebOSSay(fsn.getName() + ": not a directory", sender);
                    } else {
                        loginInstance.setCurrentDirectory((Directory)fsn);
                    }
                    found = true;
                }
            }
            if (!found) {
                gameData.getChat().plebOSSay(path + ": No such file or directory", sender);
            }

        }
    }
}
