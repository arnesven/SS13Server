package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.characteractions.StealAction;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.DoNothingAction;
import model.fancyframe.FancyFrame;
import model.fancyframe.LootingFancyFrame;
import model.fancyframe.StealFancyFrame;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

public class ShowStealFancyFrameAction extends StealAction {
    private final GameData gameData;

    public ShowStealFancyFrameAction(GameData gameData, ArrayList<Action> at, Actor actor) {
        super(actor);
        setName("Steal From/Plant Item");
        this.gameData = gameData;
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opt = new ActionOption(getName());
        for (ActionOption superopt : super.getOptions(gameData, whosAsking).getSuboptions()) {
            opt.addOption(superopt.getName());
        }
        return opt;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        // should not happen
    }
    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        if (performingClient instanceof Player) {
            Actor victim = null;
            for (Actor a : performingClient.getPosition().getActors()) {
                if (a.getPublicName().equals(args.get(0))) {
                    victim = a;
                    break;
                }
            }

            if (victim != null) {
                FancyFrame ff = new StealFancyFrame((Player) performingClient, gameData, victim);
                ((Player) performingClient).setFancyFrame(ff);
                ((Player) performingClient).setNextAction(new DoNothingAction());
                ((Player) performingClient).refreshClientData();
            } else {
                Logger.log("Could not find person to steal from!");
            }
        }
    }

    @Override
    public boolean doesSetPlayerReady() {
        return false;
    }
}
