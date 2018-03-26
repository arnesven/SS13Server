package model.modes.goals;

import model.GameData;
import model.characters.decorators.OnFireCharacterDecorator;
import model.characters.general.GameCharacter;

public class BeHotGoal extends PersonalGoal {
    @Override
    public String getText() {
        return "You're hot! Get naked and be on fire at the end of the game.";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return getBelongsTo().getCharacter().getSuit() == null &&
                getBelongsTo().getCharacter().checkInstance((GameCharacter gc) -> gc instanceof OnFireCharacterDecorator);
    }
}
