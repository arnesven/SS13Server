package model.items.keycard;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.items.general.GameItem;
import model.items.general.KeyCard;
import model.map.doors.ElectricalDoor;
import model.map.doors.ScienceDoor;

public class ScienceKeyCard extends KeyCard {
    public ScienceKeyCard() {
        super("Science Key Card");
    }

    @Override
    public boolean canOpenDoor(ElectricalDoor d) {
        return d instanceof ScienceDoor;
    }

    @Override
    public GameItem clone() {
        return new ScienceKeyCard();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("sciencekeycard", "card.png", 1, this);
    }

    @Override
    public String getExtendedDescription() {
        return "Locks or unlocks science doors (green) on the station";
    }
}
