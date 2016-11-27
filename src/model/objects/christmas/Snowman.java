package model.objects.christmas;

import graphics.sprites.Sprite;
import model.Player;
import model.map.Room;
import model.objects.general.GameObject;

public class Snowman extends GameObject {

	public Snowman(Room position) {
		super("Snowman", position);
	}
	
	@Override
	public Sprite getSprite(Player whosAsking){
		return new Sprite("snowman", "snowman.png", 0);
		
	}

}
