package model.characters.decorators;

import model.characters.general.GameCharacter;

public class DrunkChecker implements InstanceChecker {

	@Override
	public boolean checkInstanceOf(GameCharacter ch) {
		if (ch instanceof DrunkDecorator) {
			return true;
		} else if (ch instanceof CharacterDecorator) {
			return checkInstanceOf(((CharacterDecorator) ch).getInner());
		}
		return false;
	}

}
