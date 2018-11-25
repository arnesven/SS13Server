package comm.chat.plebOS;

import comm.chat.ChatCommandHandler;
import model.GameData;
import model.Player;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.plebOS.ComputerSystemLogin;
import model.plebOS.LoggedInDecorator;

public abstract class PlebOSCommandHandler extends ChatCommandHandler {

    private ComputerSystemLogin loginInstance;

    public PlebOSCommandHandler(String command) {
        super(command);
    }

    @Override
    public boolean handle(GameData gameData, Player sender, String rest) {

        ComputerSystemLogin loginInstance = getLogin(sender);

        if (rest.contains(getCommand())) {
            gameData.getChat().plebOSSay(rest, sender);
            internalHandle(gameData, sender, rest, loginInstance);
            return true;
        }
        return false;
    }

    protected abstract void internalHandle(GameData gameData, Player sender, String rest, ComputerSystemLogin loginInstance);

    protected final void internalHandle(GameData gameData, Player sender, String rest) {

    }


    private ComputerSystemLogin getLogin(Player sender) {
        GameCharacter gc = sender.getCharacter();
        while (!(gc instanceof LoggedInDecorator)) {
            gc = ((CharacterDecorator)gc).getInner();
        }

        return ((LoggedInDecorator)gc).getLogin();
    }

    public static boolean isLoggedIn(Player sender) {
        return sender.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof LoggedInDecorator);
    }


}
