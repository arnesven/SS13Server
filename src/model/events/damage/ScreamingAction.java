package model.events.damage;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;

import java.util.List;

public class ScreamingAction extends Action {
    private final Actor screamer;

    public ScreamingAction(Actor screamer) {
        super("Scream", SensoryLevel.SCREAM);
        this.screamer = screamer;
    }


    @Override
    protected String getVerb(Actor whosAsking) {
        return "screamed";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        performingClient.addTolastTurnInfo("You screamed!");
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }

    @Override
    public String getDistantDescription(Actor whosAsking) {
        return "You hear a " + screamer.getCharacter().getGender() + " screaming...";
    }


}
