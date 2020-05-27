package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.special.AlienCharacter;

import java.util.List;

public class LayEggsAction extends Action {
    private final AlienCharacter alienChar;

    public LayEggsAction(AlienCharacter alienCharacter) {
        super("Lay Eggs", SensoryLevel.PHYSICAL_ACTIVITY);
        this.alienChar = alienCharacter;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "laid eggs";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {

        performingClient.addTolastTurnInfo("You laid some eggs!");
        alienChar.layEggsInRoom(performingClient, performingClient.getPosition(), gameData);

    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }
}
