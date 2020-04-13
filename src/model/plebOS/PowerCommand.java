package model.plebOS;

import comm.chat.plebOS.PlebOSCommandHandler;
import model.GameData;
import model.Player;
import model.actions.objectactions.PowerConsoleAction;
import model.items.NoSuchThingException;
import model.objects.consoles.GeneratorConsole;

public class PowerCommand extends PlebOSCommandHandler {
    public PowerCommand() {
        super("power");
    }

    @Override
    protected boolean doesReadyUser() {
        return true;
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender,
                                  String rest, ComputerSystemSession loginInstance) {
        try {
            for (String s : gameData.findObjectOfType(GeneratorConsole.class).getSource().getStatusMessages()) {
                loginInstance.getConsole().plebOSSay(s, sender);
            }
        } catch (NoSuchThingException e) {
            loginInstance.getConsole().plebOSSay("Error - No connection to power source", sender);
        }
    }
}
