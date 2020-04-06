package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.GameCharacter;

/**
 * Created by erini02 on 01/05/16.
 */
public class CaptainsOutfit extends OutFit {

    public CaptainsOutfit(GameCharacter chara) {
        super(chara);
    }

    @Override
    public boolean permitsOver() {
        return false;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("captainsuniform", "uniform2.png", 26, 32, null);
    }
}
