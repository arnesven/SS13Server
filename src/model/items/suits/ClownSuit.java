package model.items.suits;

import graphics.sprites.RegularBlackShoesSprite;
import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.GameCharacter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 17/10/16.
 */
public class ClownSuit extends OutFit {

    private final GameCharacter chara;

    public ClownSuit(GameCharacter chara) {
        super(chara);
        this.chara = chara;
    }

    @Override
    public OutFit clone() {
        return new ClownSuit(chara);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return makeOutfit("clownsuit", "uniform2.png", 17, 11);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("clownsuitfloor", "uniforms.png", 1, 5);
    }

    public static Sprite makeOutfit(String outfitname, String map, int i, int i1) {
        List<Sprite> list = new ArrayList<>();
        list.add(new Sprite("clownshoes", "feet.png", 20));
        return new Sprite(outfitname, map, i, i1, 32, 32, list);
    }

    @Override
    public boolean permitsOver() {
        return true;
    }
}
