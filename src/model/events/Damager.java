package model.events;

public interface Damager {

	String getText();

	boolean isDamageSuccessful(boolean reduced);

	double getDamage();

}
