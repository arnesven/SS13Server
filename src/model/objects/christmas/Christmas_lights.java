package model.objects.christmas;

import graphics.sprites.Sprite;
import model.Player;
import model.map.Room;
import model.objects.general.GameObject;

public class Christmas_lights extends GameObject {

	public Christmas_lights(Room position) {
		super("Christmas lights", position);
	}
	


	@Override
	public Sprite getSprite(Player whosAsking){
		return new Sprite("christmas_lights", "christmas_lights.png", 0);
	}
}
