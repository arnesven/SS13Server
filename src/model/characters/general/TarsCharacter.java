package model.characters.general;

public class TarsCharacter extends RobotCharacter {

	public TarsCharacter() {
		super("TARS", 0, 20.0);
	}

	@Override
	public GameCharacter clone() {
		return new TarsCharacter();
	}
	
	

	
}
