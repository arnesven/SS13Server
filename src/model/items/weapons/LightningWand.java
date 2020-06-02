package model.items.weapons;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.events.animation.AnimatedSprite;

import java.awt.*;

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

    @Override
    protected boolean hasExtraEffect() {
        return true;
    }

    @Override
    protected AnimatedSprite getExtraEffectSprite() {
        AnimatedSprite anms = new AnimatedSprite("lightningwandbolts", "laser.png",
                0, 5, 32, 32, null, 15, false);
        anms.setColor(Color.YELLOW);
        return anms;
    }
}
