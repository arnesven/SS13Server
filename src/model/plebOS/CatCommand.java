package model.plebOS;

import comm.chat.plebOS.PlebOSCommandHandler;
import model.GameData;
import model.Player;

public class CatCommand extends PlebOSCommandHandler {
    public CatCommand() {
        super("cat ");
    }

    @Override
    protected boolean doesReadyUser() {
        return false;
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender,
                                  String rest, ComputerSystemSession loginInstance) {
        rest = rest.replace("$cat ", "");
        try {
            FileSystemNode d = loginInstance.getCurrentDirectory().getNodeForeName(rest);
            if (d instanceof PlebosFile) {
                if (((PlebosFile)d).isExecutable()) {
                    loginInstance.getConsole().plebOSSay(rest + ": Is a binary", sender);
                } else {
                    for (String line : ((PlebosFile)d).getTextualContents()) {
                        loginInstance.getConsole().plebOSSay(line, sender);
                    }
                }
            } else {
                loginInstance.getConsole().plebOSSay(rest + ": Is a directory", sender);
            }
        } catch (Directory.NoSubDirectoryFoundException nsedfe) {
            loginInstance.getConsole().plebOSSay(rest + ": No such file or directory", sender);
        }
    }
}
