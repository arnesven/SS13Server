package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.GameCharacter;
import model.characters.visitors.BirdManCharacter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 08/09/17.
 */
public class BirdManSuit extends OutFit {
    private final GameCharacter chara;

    public BirdManSuit(GameCharacter chara) {
        super(chara);
        this.chara = chara;
    }

    @Override
    public OutFit clone() {
        return new BirdManSuit(chara);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return makeOutfit("birdmansuit", "uniform2.png", 01, 11);
    }

    public static Sprite makeOutfit(String outfitname, String map, int i, int i1) {
        List<Sprite> list = new ArrayList<>();
        list.add(new Sprite("birdmanshoes", "feet.png", 10));
        return new Sprite(outfitname, map, i, i1, 32, 32, list);
    }

    @Override
    public boolean permitsOver() {
        return false;
    }

}
