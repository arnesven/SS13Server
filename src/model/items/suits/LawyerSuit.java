package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.GameCharacter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 17/10/16.
 */
public class LawyerSuit extends OutFit {


    public LawyerSuit(GameCharacter chara) {
        super(chara);
    }


    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return makeOutfit("lawyersuit", "uniform2.png", 20, 17);
    }

    public static Sprite makeOutfit(String outfitname, String map, int i, int i1) {
        List<Sprite> list = new ArrayList<>();
        list.add(new Sprite("blackshoes", "feet.png", 5));
        list.add(new Sprite("briefcase", "items_righthand.png", 21, 28));
        return new Sprite(outfitname, map, i, i1, 32, 32, list);
    }
}
