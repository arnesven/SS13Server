package model.modes;

import model.GameData;
import model.items.general.Locatable;

public interface TraitorObjective {

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
	
	/**
	 * Returns true if this objective ever was completed.
	 * May be true even if the objective isn't true right
	 * at this particular time.
	 * @return true if the objective ever was completed.
	 */
	boolean wasCompleted();
	
	/**
	 * Gets the number of points this objective is worth.
	 * @return the points for this objective.
	 */
	int getPoints();
	
	/**
	 * Gets a locatable for this objective. Locatables are used
	 * by locators to track down something.
	 * @return a locatable.
	 */
	Locatable getLocatable();
	
}
