package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.crew.HeadOfStaffCharacter;
import model.characters.general.GameCharacter;
import model.objects.consoles.AdministrationConsole;
import util.HTMLText;

import java.util.List;

/**
 * Created by erini02 on 05/12/16.
 */
public class AcceptNewProfessionAction extends Action {
    private final AdministrationConsole adminConsole;

    public AcceptNewProfessionAction(AdministrationConsole administrationConsole) {
        super("Accept Job Change", SensoryLevel.OPERATE_DEVICE);
        this.adminConsole = administrationConsole;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Fiddled with admin console";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        performingClient.addTolastTurnInfo("You have now signed up for a new job. Your application is pending.");
        adminConsole.getAcceptedActors().add(performingClient);
        for (Actor a : gameData.getActors()) {
            if (a.getCharacter().checkInstance((GameCharacter gc ) -> gc instanceof HeadOfStaffCharacter)) {
                performingClient.addTolastTurnInfo(HTMLText.makeText("blue",
                        "Admin Console: " + performingClient.getPublicName() + " has signed up for a new job. You need to handle the request."));
            }
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }
}
