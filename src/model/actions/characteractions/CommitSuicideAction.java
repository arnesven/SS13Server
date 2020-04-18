package model.actions.characteractions;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;

import java.util.List;

/**
 * Created by erini02 on 25/08/17.
 */
public class CommitSuicideAction extends Action {

    public CommitSuicideAction() {
        super("Commit Suicide", SensoryLevel.NO_SENSE);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "committed suicide";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        performingClient.getCharacter().setHealth(0.0);
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }

    @Override
    public Sprite getAbilitySprite() {
        return new Sprite("suicideability", "interface.png", 7, 1, null);
    }
}
