package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.Target;
import model.characters.decorators.RadiationOverlayDecorator;
import model.characters.general.GameCharacter;
import model.map.rooms.Room;

public class Multimeter extends UplinkItem {

	
	public Multimeter() {
		super("Multimeter", 0.2, 149);
	}

	@Override
	public Multimeter clone() {
		return new Multimeter();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("multimeter", "device.png", 0, this);
    }

    @Override
    public void gotGivenTo(Actor to, Target from) {
       to.setCharacter(new RadiationOverlayDecorator(to.getCharacter()));
        if (from instanceof Actor) {
            removeDecorator((Actor)from);
        }
    }

    private void removeDecorator(Actor from) {
        if (from.getCharacter().checkInstance((GameCharacter ch) -> ch instanceof RadiationOverlayDecorator)){
            from.removeInstance(((GameCharacter ch) -> ch instanceof RadiationOverlayDecorator));
        }
    }

    @Override
    public void gotAddedToRoom(Actor cameFrom, Room to) {
        super.gotAddedToRoom(cameFrom, to);
        if (cameFrom != null) {
            removeDecorator(cameFrom);
        }
    }

}
