package model.characters.decorators;

import model.characters.GameCharacter;

/**
 * @author erini02
 * Interface for checking wether a character (or
 * one of its inner character, due to decorators) has
 * a certain instance.
 */
public interface InstanceChecker {
	boolean checkInstanceOf(GameCharacter ch);
}
