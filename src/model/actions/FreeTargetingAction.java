package model.actions;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.items.general.GameItem;

import java.util.List;

public abstract class FreeTargetingAction extends TargetingAction {
    public FreeTargetingAction(String name, SensoryLevel s, Actor ap) {
        super(name, s, ap);
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        opts.setName(opts.getName() + " (0 AP)");
        return opts;
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        super.setArguments(args, performingClient);
        applyTargetingAction(getGameData(), performingClient, getTarget(), getItem());

//        if (hasRealSound()) {
//            player.getSoundQueue().add(getRealSound());
//            if (this.getSense().sound == SensoryLevel.AudioLevel.SAME_ROOM || this.getSense().sound == SensoryLevel.AudioLevel.VERY_LOUD) {
//                for (Player p : player.getPosition().getClients()) {
//                    if (p != performingClient) {
//                        p.getSoundQueue().add(getRealSound());
//                    }
//                }
//            }
//        }
    }

    @Override
    public boolean doesSetPlayerReady() {
        return false;
    }

    @Override
    public boolean wasPerformedAsQuickAction() {
        return true;
    }
}
