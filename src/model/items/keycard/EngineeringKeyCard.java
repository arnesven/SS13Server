package model.items.keycard;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;
import model.items.general.KeyCard;
import model.map.doors.ElectricalDoor;
import model.map.doors.EngineeringDoor;

public class EngineeringKeyCard extends KeyCard {
    public EngineeringKeyCard() {
        super("Engineering Key Card");
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("engkeycard", "card2.png", 0, this);
    }

    @Override
    public boolean canOpenDoor(ElectricalDoor d) {
        return d instanceof EngineeringDoor;
    }

    @Override
    public GameItem clone() {
        return new EngineeringKeyCard();
    }
}
