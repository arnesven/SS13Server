package model.modes.goals;

import model.GameData;
import model.characters.decorators.EscapedOnShuttleDecorator;
import model.characters.general.GameCharacter;

public class EscapeOnEscapeShuttleGoal extends PersonalGoal {
    @Override
    public String getText() {
        return "Escape on the escape shuttle before the end of the game.";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return getBelongsTo().getCharacter().checkInstance((GameCharacter gc) -> gc instanceof EscapedOnShuttleDecorator);
    }


}
