package comm.chat.plebOS;

import comm.chat.ChatCommandHandler;
import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.plebOS.ComputerSystemSession;
import model.plebOS.LoggedInDecorator;

public abstract class PlebOSCommandHandler extends ChatCommandHandler {

    public PlebOSCommandHandler(String command) {
        super(command);
    }

    @Override
    public boolean handle(GameData gameData, Player sender, String rest) {

        ComputerSystemSession loginInstance = ComputerSystemSession.getLogin(sender);

        if (rest.contains(getCommand())) {
            loginInstance.getConsole().plebOSSay("$ rest", sender);
            internalHandle(gameData, sender, rest, loginInstance);
            if (doesReadyUser()) {
                try {
                    gameData.setPlayerReady(gameData.getClidForPlayer(sender), true);
                } catch (NoSuchThingException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        return false;
    }

    protected abstract boolean doesReadyUser();

    protected abstract void internalHandle(GameData gameData, Player sender, String rest, ComputerSystemSession loginInstance);

    protected final void internalHandle(GameData gameData, Player sender, String rest) {

    }


    public static boolean isLoggedIn(Player sender) {
        return sender.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof LoggedInDecorator);
    }


}
