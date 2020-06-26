package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.npcs.NPC;
import model.npcs.behaviors.CommandedByBehavior;
import model.npcs.behaviors.FollowMeBehavior;

import java.util.List;

public class FollowCaptainAction extends Action {

    private final Actor pirateCaptain;

    public FollowCaptainAction(Actor pirateCaptain) {
        super("Follow Captain", SensoryLevel.NO_SENSE);
        this.pirateCaptain = pirateCaptain;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "started following captain";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (performingClient instanceof NPC) {
            ((NPC) performingClient).setMoveBehavior(new FollowMeBehavior(pirateCaptain, gameData));
        }
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }
}
