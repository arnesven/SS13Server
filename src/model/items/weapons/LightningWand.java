package model.items.weapons;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;

public class LightningWand extends StunBaton {

    public LightningWand() {
        setShots(8);
        setName("Lightning Wand");
        setCost(855);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("lightningwand", "weapons2.png", 18, 26, this);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A wand which shoots powerful electric jolts which paralyze its victims.";
    }
}
