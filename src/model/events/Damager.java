package model.events;

public interface Damager {
	
	
	/**
	 * @return the text to the client when it hits it.
	 */
	String getText();

	boolean isDamageSuccessful(boolean reduced);

	double getDamage();

	/**
	 * Name of the damager type, e.g. fire, asphyxiation.
	 * @return
	 */
	String getName();

}
