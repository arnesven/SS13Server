package model.characters.decorators;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.GameCharacter;

public class HuskDecorator extends CharacterDecorator {

	public HuskDecorator(GameCharacter chara) {
		super(chara, "Husk");
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("husk", "human.png", 137, getActor());
    }

    @Override
	public String getPublicName() {
		return "Husk";
	}
	
	@Override
	public String getWatchString(Actor whosAsking) {
		return "Ugh! A shriveled up husk.";
	}

    @Override
    public boolean isVisibileFromAdjacentRoom() {
        return false;
    }
}
