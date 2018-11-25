package comm.chat;

import model.GameData;
import model.Player;
import model.plebOS.ComputerSystemLogin;

import java.io.Serializable;

public abstract class ChatCommandHandler implements Serializable {

    private final String cmd;

    public ChatCommandHandler(String command) {
        this.cmd = command;
    }

    public boolean handle(GameData gameData, Player sender, String rest) {
        if (rest.contains(cmd)) {
            internalHandle(gameData, sender, rest);
            return true;
        }
        return false;
    }

    protected abstract void internalHandle(GameData gameData, Player sender, String rest);

    protected String getCommand() {
        return cmd;
    }
}
