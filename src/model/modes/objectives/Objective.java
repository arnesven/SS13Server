package model.modes.objectives;

import model.GameData;

import java.io.Serializable;

/**
 * Created by erini02 on 03/12/16.
 */
public interface Objective extends Serializable {

    /**
     * Gets the objective text as it appears to the
     * traitor at the beginning of the game.
     * @return the objective text.
     */
    String getText();

    /**
     * Checks wether or not the objective is completed
     * right at this moment.
     * @param gameData
     * @return
     */
    boolean isCompleted(GameData gameData);


}
