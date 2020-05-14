package model.events;

import model.Actor;
import model.Bank;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.objects.consoles.AIConsole;
import model.objects.consoles.PersonnelConsole;
import model.objects.consoles.RequisitionsConsole;
import util.Logger;

/**
 * Created by erini02 on 15/11/16.
 */
public class PayWagesEvent extends Event {

    private static final int STANDARD_WAGE = 10;



    @Override
    public void apply(GameData gameData) {
        Logger.log("Applying paychecks. - ran once");
        try {
            PersonnelConsole adminConsole = gameData.findObjectOfType(PersonnelConsole.class);
            if ((gameData.getRound()-1) % 3 == 0) {
                if (adminConsole.canPayAllWages(gameData)) {
                    for (Actor a : gameData.getActors()) {


                            int wage =  adminConsole.getWageForActor(a);
                            if (wage != 0) {
                                gameData.getGameMode().getBank().addToAccount(a, wage);
                                adminConsole.subtractFromBudget(wage);
                                Logger.log(Logger.INTERESTING, a.getBaseName() + "'s wage payed $$" + wage);
                            }
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
