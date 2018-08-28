package model.modes.goals;

import model.GameData;
import model.actions.characteractions.MarriageAction;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;

public class GetMarriedGoal extends PersonalGoal {
    @Override
    public String getText() {
        return "Get married to another crewmember!";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return getBelongsTo().getCharacter().checkInstance((GameCharacter gc)-> gc instanceof MarriageAction.MarriedDecorator);
    }
}
