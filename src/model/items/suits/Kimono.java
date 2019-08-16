package model.items.suits;

import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.Actor;
import model.characters.visitors.GeishaCharacter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 17/10/16.
 */
public class Kimono extends OutFit {
    public Kimono(GeishaCharacter geishaCharacter) {
        super(geishaCharacter);
    }

    @Override
    public String getPublicName(Actor whosAsking) {
        return "Kimono";
    }

    @Override
    public String getBaseName() {
        return "Kimono";
    }

    @Override
    public String getFullName(Actor whosAsking) {
        return "Kimono";
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return makeOutfit("kimono", "uniform2.png", 3, 27, this);
    }

    public static Sprite makeOutfit(String outfitname, String map, int i, int i1, SpriteObject objRef) {
        List<Sprite> list = new ArrayList<>();
        list.add(new Sprite("geishashoes", "feet.png", 5, objRef));
        return new Sprite(outfitname, map, i, i1, 32, 32, list, objRef);
    }
}
