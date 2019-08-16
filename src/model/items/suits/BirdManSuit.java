package model.items.suits;

import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
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
        return makeOutfit("birdmansuit", "uniform2.png", 01, 11, this);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("birdmansuitfloor", "uniforms.png", 7, 4, this);
    }

    public static Sprite makeOutfit(String outfitname, String map, int i, int i1, SpriteObject objectRef) {
        List<Sprite> list = new ArrayList<>();
        list.add(new Sprite("birdmanshoes", "feet.png", 10, objectRef));
        return new Sprite(outfitname, map, i, i1, 32, 32, list, objectRef);
    }

    @Override
    public boolean permitsOver() {
        return false;
    }

}
