package model.characters;

public abstract class HumanCharacter extends GameCharacter {
	
	public HumanCharacter(String name, int startRoom, double speed) {
		super(name, startRoom, speed);
	}

	public String getPublicName() {
		String res = getBaseName();
		if (getSuit() == null) {
			res = "Naked " + getGender() ;
		}
		if (isDead()) {
			return res + " (dead)";
		}
		return res;
	}
}
