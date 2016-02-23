package model.actions;

import java.util.Comparator;

public class SpeedComparator implements Comparator<ActionPerformer> {

	@Override
	public int compare(ActionPerformer o1, ActionPerformer o2) {
		
		if (o1.getSpeed() > o2.getSpeed()) {
			return 1;
		} else if (o1.getSpeed() < o2.getSpeed()) {
			return -1;
		}
		return 0;
	}

}
