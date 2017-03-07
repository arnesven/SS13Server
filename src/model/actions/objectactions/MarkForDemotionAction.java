package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.crew.HeadOfStaffCharacter;
import model.characters.general.GameCharacter;
import model.objects.consoles.AdministrationConsole;
import util.HTMLText;

import java.util.List;

/**
 * Created by erini02 on 05/12/16.
 */
public class MarkForDemotionAction extends Action {
    private final AdministrationConsole admin;
    private final GameData gameData;
    private Actor selected = null;

    public MarkForDemotionAction(AdministrationConsole administrationConsole, GameData gameData) {
        super("Mark For Demotion", SensoryLevel.OPERATE_DEVICE);
        this.admin = administrationConsole;
        this.gameData = gameData;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Fiddled with admin console";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opt = super.getOptions(gameData, whosAsking);

        for (Actor a : gameData.getActors()) {
            if (a.getCharacter().isCrew()) {
                opt.addOption(a.getBaseName());
            }
        }

        return opt;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        admin.getToBeDemoted().add(selected);
        performingClient.addTolastTurnInfo("You queued " + selected.getBaseName() + " up for demotion.");
        for (Actor a : gameData.getActors()) {
            if (a.getCharacter().checkInstance((GameCharacter gc ) -> gc instanceof HeadOfStaffCharacter)) {
                a.addTolastTurnInfo(HTMLText.makeText("blue",
                        "Admin Console: " + selected.getPublicName() + " has been queued for demotion. You need to handle the request."));
            }
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (Actor a : gameData.getActors()) {
            if (a.getBaseName().equals(args.get(0))) {
                selected = a;
            }
        }

    }
}
