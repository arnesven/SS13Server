package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;

public class GeigerMeter extends GameItem {

	
	public GeigerMeter() {
		super("Geiger Meter", 0.2);
	}

	@Override
	public GeigerMeter clone() {
		return new GeigerMeter();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("geigermeter", "device.png", 0);
    }
}
