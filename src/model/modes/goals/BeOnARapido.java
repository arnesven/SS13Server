package model.modes.goals;

import model.GameData;
import model.items.suits.Rapido;

/**
 * Created by erini02 on 15/09/17.
 */
public class BeOnARapido extends PersonalGoal {
    @Override
    public String getText() {
        return "End the game on a rapido";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return getBelongsTo().getCharacter().getSuit() instanceof Rapido;
    }
}
