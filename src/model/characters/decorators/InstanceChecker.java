package model.characters.decorators;

import model.characters.general.GameCharacter;

import java.io.Serializable;

/**
 * @author erini02
 * Interface for checking wether a character (or
 * one of its inner character, due to decorators) has
 * a certain instance.
 */
public interface InstanceChecker extends Serializable{
	boolean checkInstanceOf(GameCharacter ch);
}
