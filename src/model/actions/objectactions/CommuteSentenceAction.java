package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.objects.consoles.CrimeRecordsConsole;

import java.util.List;

/**
 * Created by erini02 on 03/05/16.
 */
public class CommuteSentenceAction extends Action {
    private final CrimeRecordsConsole console;
    private Actor selected;

    public CommuteSentenceAction(CrimeRecordsConsole console) {
        super("Commute Sentence", SensoryLevel.OPERATE_DEVICE);
        this.console = console;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "commuted sentence";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opt = super.getOptions(gameData, whosAsking);
        for (Actor a : console.getSentenceMap().keySet()) {
            opt.addOption(a.getBaseName());
        }
        return opt;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        performingClient.addTolastTurnInfo("You pardoned " + selected.getBaseName() + ".");
        console.getSentenceMap().put(selected, 0);
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (Actor a : console.getSentenceMap().keySet()) {
            if (a.getBaseName().equals(args.get(0))) {
                selected = a;
            }
        }

    }
}
