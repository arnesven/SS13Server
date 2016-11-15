package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.Target;
import model.characters.decorators.RadiationOverlayDecorator;
import model.characters.general.GameCharacter;

public class GeigerMeter extends UplinkItem {

	
	public GeigerMeter() {
		super("Geiger Meter", 0.2, 49);
	}

	@Override
	public GeigerMeter clone() {
		return new GeigerMeter();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("geigermeter", "device.png", 0);
    }

    @Override
    public void gotGivenTo(Actor to, Target from) {
       to.setCharacter(new RadiationOverlayDecorator(to.getCharacter()));
        if (from instanceof Actor) {
            if (((Actor) from).getCharacter().checkInstance((GameCharacter ch) -> ch instanceof RadiationOverlayDecorator)){
                ((Actor) from).removeInstance(((GameCharacter ch) -> ch instanceof RadiationOverlayDecorator));
            }
        }
    }
}
