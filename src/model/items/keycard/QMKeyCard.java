package model.items.keycard;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.items.general.GameItem;
import model.items.general.KeyCard;
import model.map.doors.CommandDoor;
import model.map.doors.ElectricalDoor;

public class QMKeyCard extends KeyCard {
    public QMKeyCard() {
        super("QM Key Card");
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("qmkeycard", "card.png", 6, this);
    }


    @Override
    public boolean canOpenDoor(ElectricalDoor d) {
        return !(d instanceof CommandDoor);
    }

    @Override
    public GameItem clone() {
        return new QMKeyCard();
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A key card which grants access to pretty much anywhere on the station, except for locked Command rooms.";
    }
}
