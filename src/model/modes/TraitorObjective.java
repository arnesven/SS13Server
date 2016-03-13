package model.modes;

import model.GameData;

public interface TraitorObjective {

	String getText();
	
	/**
	 * Checks wether or not the objective is completed
	 * right at this moment.
	 * @param gameData
	 * @return
	 */
	boolean isCompleted(GameData gameData);
	
	boolean wasCompleted();
	
	int getPoints();
	
}
