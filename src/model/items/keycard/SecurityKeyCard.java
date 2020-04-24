package model.items.keycard;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.items.general.KeyCard;
import model.map.doors.ElectricalDoor;
import model.map.doors.SecurityDoor;

public class SecurityKeyCard extends KeyCard {

    public SecurityKeyCard() {
        setName("Security Key Card");
    }

    @Override
    public boolean canOpenDoor(ElectricalDoor door) {
        return door instanceof SecurityDoor;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("securitykeecard", "card2.png", 1, 1, this);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "Can lock and unlock Security (red) doors.";
    }
}
