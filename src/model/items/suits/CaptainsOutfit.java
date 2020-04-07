package model.items.suits;

import graphics.sprites.RegularBlackShoesSprite;
import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.GameCharacter;

import java.util.HashMap;
import java.util.Map;

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
        return new Sprite("captainsuniform", "uniform2.png", 26, 32, this);
    }

    @Override
    public Map<Integer, Sprite> getAdditionalSprites() {
        Map<Integer, Sprite> map = new HashMap<>();
        map.put(Equipment.HEAD_SLOT, new CaptainsHat().getSprite(getType().getActor()));
        map.put(Equipment.FEET_SLOT, new RegularBlackShoesSprite());
        return map;
    }
}
