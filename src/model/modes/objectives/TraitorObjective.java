package model.modes.objectives;

import model.items.general.Locatable;
import model.modes.objectives.Objective;

public interface TraitorObjective extends Objective {


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
