package model.characters;


public class SecuritronCharacter extends RobotCharacter {

	private int startRoom;

	public SecuritronCharacter(int i) {
		super("SecuriTRON", i, 0.5);
		this.startRoom = i;
	}

	@Override
	public GameCharacter clone() {
		return new SecuritronCharacter(startRoom);
	}


}
