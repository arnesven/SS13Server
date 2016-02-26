package model.actions;

import java.util.Comparator;

import model.Actor;

public class SpeedComparator implements Comparator<Actor> {

	@Override
	public int compare(Actor o1, Actor o2) {
		
		if (o1.getSpeed() > o2.getSpeed()) {
			return -1;
		} else if (o1.getSpeed() < o2.getSpeed()) {
			return 1;
		}
		return 0;
	}

}
