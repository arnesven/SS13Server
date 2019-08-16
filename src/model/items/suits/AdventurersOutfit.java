package model.items.suits;

import graphics.sprites.RegularBlackShoesSprite;
import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.Actor;
import model.characters.general.GameCharacter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 18/10/16.
 */
public class AdventurersOutfit extends OutFit {
    public AdventurersOutfit(GameCharacter chara) {
        super(chara);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return makeOutfit("kimono", "uniform2.png", 30, 10, this);
    }

    public static Sprite makeOutfit(String outfitname, String map, int i, int i1, SpriteObject objRef) {
        List<Sprite> list = new ArrayList<>();
        list.add(new RegularBlackShoesSprite());
        return new Sprite(outfitname, map, i, i1, 32, 32, list, objRef);
    }
}
