package comm.chat.plebOS;

import model.GameData;
import model.Player;
import model.plebOS.ComputerSystemLogin;

import java.util.List;

public class LsCommand extends PlebOSCommandHandler {
    public LsCommand() {
        super("ls");
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest, ComputerSystemLogin csl) {
        List<String> files = csl.getFilesForCurrentDirectory();
        for (String file : files) {
            gameData.getChat().plebOSSay(file, sender);
        }
    }
}
