package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.misc.ReligiousFigure;
import model.objects.Altar;

import java.util.List;

public class PrayerAction extends Action {
    private final Altar altar;
    private ReligiousFigure selectedGod;

    public PrayerAction(Altar altar) {
        super("Pray to", SensoryLevel.SPEECH);
        this.altar = altar;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "prayed to " + selectedGod.getName();
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        for (ReligiousFigure g : altar.getGods()) {
            opts.addOption(g.getName());
        }
        return opts;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        performingClient.addTolastTurnInfo("You prayed to " + selectedGod.getName());
        altar.setLastPrayed(selectedGod);
        selectedGod.doWhenPrayedTo(gameData, performingClient, altar);
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (ReligiousFigure g : altar.getGods()) {
            if (args.get(0).equals(g.getName())) {
                selectedGod = g;
            }
        }
    }

    public ReligiousFigure getSelectedGod() {
        return selectedGod;
    }
}
