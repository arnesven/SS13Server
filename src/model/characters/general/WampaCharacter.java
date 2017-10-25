package model.characters.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.weapons.Weapon;

public class WampaCharacter extends AnimalCharacter {

    private static Weapon wampaClaw = new WampaClaw();

    public WampaCharacter(int roomid) {
        super("Wampa", roomid, 14.5);
    }

    @Override
    public GameCharacter clone() {
        return new WampaCharacter(getStartingRoom());
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("wampa", "wampa.png", 0, 0, 32, 69);
    }

    @Override
    public Weapon getDefaultWeapon() {
        return wampaClaw;
    }

    @Override
    public boolean isVisibileFromAdjacentRoom() {
        return true;
    }
}
