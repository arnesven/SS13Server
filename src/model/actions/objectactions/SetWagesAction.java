package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.objects.consoles.PersonnelConsole;
import model.objects.consoles.RequisitionsConsole;

import java.util.List;

/**
 * Created by erini02 on 15/11/16.
 */
public class SetWagesAction extends Action {
    private final PersonnelConsole admin;
    private final GameData gameData;
    private Actor selectedActor;
    private int newWage;

    public SetWagesAction(PersonnelConsole administrationConsole,
                          GameData gameData) {
        super("Modify Paychecks", SensoryLevel.OPERATE_DEVICE);
        this.admin = administrationConsole;
        this.gameData = gameData;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Fiddled with Admin Console";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        for (Actor a : gameData.getActors()) {
            if (a.getCharacter().isCrew()) {
                ActionOption opt = new ActionOption(a.getBaseName() + " ("+ admin.getWageForActor(a) + ")");
                addWageOptions(opt, admin.getWageForActor(a));
                opts.addOption(opt);
            }
        }
        return opts;
    }

    private void addWageOptions(ActionOption opt, int current) {
        if (current != 0) {
            opt.addOption("0");
        }
        if (current / 2 != 0) {
            opt.addOption("" + (current / 2));
        }
        if (current * 2 != 0) {
            opt.addOption("" + (current * 2));
        }
        if (current == 0) {
            opt.addOption("4");
        }
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        admin.setWageForActor(selectedActor, newWage);
        performingClient.addTolastTurnInfo("You updated the wage for " + selectedActor.getBaseName() + ".");
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        selectedActor = null;
        for (Actor a : gameData.getActors()) {
            if (a.getCharacter().isCrew()) {
                if (args.get(0).contains(a.getBaseName())) {
                    selectedActor = a;
                }
            }
        }

        newWage = Integer.parseInt(args.get(1));
    }
}
