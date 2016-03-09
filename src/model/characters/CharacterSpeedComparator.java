package model.characters;

import java.util.Comparator;


public class CharacterSpeedComparator implements Comparator<GameCharacter> {

		@Override
		public int compare(GameCharacter o1, GameCharacter o2) {
			
			if (o1.getSpeed() > o2.getSpeed()) {
				return -1;
			} else if (o1.getSpeed() < o2.getSpeed()) {
				return 1;
			}
			return 0;
		}
	

}
