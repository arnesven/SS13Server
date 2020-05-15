package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.map.doors.ElectricalDoor;

public class UniversalKeyCard extends KeyCard {

	public UniversalKeyCard() {
		super("Universal Key Card");
	}

	@Override
	public UniversalKeyCard clone() {
		return new UniversalKeyCard();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("universalkeycard", "card.png", 2, this);
    }

    @Override
	public boolean canOpenDoor(ElectricalDoor door) {
		return true;
	}

	@Override
	public String getExtendedDescription() {
		return "Unlocks or locks any door on the station. Also needed to approve venting of station (in case of severe fires).";
	}
}
