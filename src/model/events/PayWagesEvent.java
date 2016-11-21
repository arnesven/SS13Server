package model.events;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.objects.ATM;
import model.objects.consoles.AIConsole;
import model.objects.consoles.AdministrationConsole;
import util.Logger;

/**
 * Created by erini02 on 15/11/16.
 */
public class PayWagesEvent extends Event {
    private final ATM atm;
    private static final int STANDARD_WAGE = 10;

    public PayWagesEvent(GameData gameData, ATM atm) {
        this.atm = atm;

    }

    @Override
    public void apply(GameData gameData) {
        try {
            AdministrationConsole adminConsole = gameData.findObjectOfType(AdministrationConsole.class);
            if ((gameData.getRound()-1) % 3 == 0) {
                if (adminConsole.canPayAllWages(gameData)) {
                    for (Actor a : gameData.getActors()) {

                        //if (noConsole) {
                        //    atm.addToAccount(a, STANDARD_WAGE);
                        //    adminConsole.subtractFromBudget(STANDARD_WAGE);
                        //} else {
                            int wage =  adminConsole.getWageForActor(a);
                            if (wage != 0) {
                                atm.addToAccount(a, wage);
                                adminConsole.subtractFromBudget(wage);
                                Logger.log(Logger.INTERESTING, a.getBaseName() + "'s wage payed $$" + wage);
                            }
                        //}

                    }
                } else {
                    try {
                        gameData.findObjectOfType(AIConsole.class).informOnStation("Paychecks cancelled, station budget insufficient.", gameData);
                    } catch (NoSuchThingException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String howYouAppear(Actor performingClient) {
        return "";
    }

    @Override
    public SensoryLevel getSense() {
        return SensoryLevel.NO_SENSE;
    }
}
