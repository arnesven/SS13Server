package model.plebOS;

import comm.chat.plebOS.PlebOSCommandHandler;
import model.GameData;
import model.Player;
import model.actions.objectactions.PowerConsoleAction;
import model.events.ambient.SimulatePower;
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

            for (String s : ((SimulatePower)gameData.getGameMode().getEvents().get("simulate power")).getStatusMessages(gameData)) {
                loginInstance.getConsole().plebOSSay(s, sender);
            }
    }
}
