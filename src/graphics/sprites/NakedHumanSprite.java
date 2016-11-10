package graphics.sprites;

import util.Logger;
import util.MyRandom;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 26/04/16.
 */
public class NakedHumanSprite extends Sprite {

    private static List<Sprite> hairSprites = makeHairSpriteList();

    public NakedHumanSprite(boolean gender) {
        super("nakedman"+MyRandom.nextInt(99999999), "naked.png", (gender?0:2), getRandomHair());
    }

    private static List<Sprite> getRandomHair() {
        List<Sprite> res = new ArrayList<>();
        res.add(MyRandom.sample(hairSprites));
       // Logger.log("Ran Random Hair");
        res.get(0).setColor(new Color(MyRandom.nextInt(156), MyRandom.nextInt(156), MyRandom.nextInt(156)));
        return res;
    }

    private static List<Sprite> makeHairSpriteList() {
        List<Sprite> list = new ArrayList<>();

        List<Integer> col = new ArrayList<>();
        List<Integer> row = new ArrayList<>();
        for (int i = 5; i < 310; i +=4) {
            col.add(i % 31);
            row.add(i / 31);
            //   Logger.log("Hair [col, row]=[" + col.get(col.size()-1) + "," + row.get(row.size()-1) + "]");
        }

        for (int i = 0; i < col.size(); ++i) {
            list.add(new Sprite("hair1", "human_face.png", col.get(i), row.get(i)));
        }
        return list;
    }


}
