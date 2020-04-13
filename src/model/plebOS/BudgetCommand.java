package model.plebOS;

import comm.chat.plebOS.PlebOSCommandHandler;
import model.Actor;
import model.GameData;
import model.Player;
import model.items.NoSuchThingException;
import model.objects.consoles.AdministrationConsole;
import model.objects.shipments.Shipment;
import util.Pair;

public class BudgetCommand extends PlebOSCommandHandler {
    public BudgetCommand() {
        super("budget");
    }

    @Override
    protected boolean doesReadyUser() {
        return true;
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender,
                                  String rest, ComputerSystemSession loginInstance) {
        try {
            AdministrationConsole cons = gameData.findObjectOfType(AdministrationConsole.class);
            loginInstance.getConsole().plebOSSay("Station funds: $$" + cons.getMoney(), sender);
            if (cons.getHistory().size() > 0) {
                loginInstance.getConsole().plebOSSay("SHIPMENT HISTORY (ORDERED BY)", sender);
                for (Pair<Actor, Shipment> p : cons.getHistory()) {
                    loginInstance.getConsole().plebOSSay("$$" + p.second.getCost() + " - " +
                            p.second.getName() + " (" + p.first.getBaseName() + ")", sender);
                }
            }
        } catch (NoSuchThingException e) {
            loginInstance.getConsole().plebOSSay("Error - No connection to Admin console", sender);
            e.printStackTrace();
        }

    }
}
