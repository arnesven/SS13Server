package model.characters.general;


import graphics.sprites.Sprite;
import model.Actor;

public class SecuritronCharacter extends RobotCharacter {

	private int startRoom;

	public SecuritronCharacter(int i) {
		super("SecuriTRON", i, 0.0);
		this.startRoom = i;
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("securitron", "aibots.png", 51);
    }

    @Override
	public GameCharacter clone() {
		return new SecuritronCharacter(startRoom);
	}


}
