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
            gameData.getChat().plebOSSay("Station funds: $$" + cons.getMoney(), sender);
            if (cons.getHistory().size() > 0) {
                gameData.getChat().plebOSSay("SHIPMENT HISTORY (ORDERED BY)", sender);
                for (Pair<Actor, Shipment> p : cons.getHistory()) {
                    gameData.getChat().plebOSSay("$$" + p.second.getCost() + " - " +
                            p.second.getName() + " (" + p.first.getBaseName() + ")", sender);
                }
            }
        } catch (NoSuchThingException e) {
            gameData.getChat().plebOSSay("Error - No connection to Admin console", sender);
            e.printStackTrace();
        }

    }
}
