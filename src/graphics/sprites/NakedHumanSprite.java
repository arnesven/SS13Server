package graphics.sprites;

import util.MyRandom;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 26/04/16.
 */
public class NakedHumanSprite extends Sprite {

    private static List<Sprite> hairSprites = makeHairSpriteList();
    private static long count = 0;

    public NakedHumanSprite(boolean gender) {
        super("nakedman"+(count++), "naked.png", (gender?0:2), getRandomHair());
    }

    public NakedHumanSprite(boolean gender, int hairNum, Color color) {
        super("nakedman"+(count++), "naked.png", (gender?0:2),
                getHairNumber(hairNum, color));
    }

    private static List<Sprite> getHairNumber(int hairNum, Color color) {
        List<Sprite> res = new ArrayList<>();
        res.add(hairSprites.get(hairNum));
        // Logger.log("Ran Random Hair");
        res.get(0).setColor(color);
        return res;
    }

    private static List<Sprite> getRandomHair() {
        List<Sprite> res = new ArrayList<>();
        res.add(MyRandom.sample(hairSprites));
       // Logger.log("Ran Random Hair");
        Color c = new Color(MyRandom.nextInt(255), MyRandom.nextInt(255), MyRandom.nextInt(255));
        Sprite sp = new Sprite(res.get(0).getName() + c.getRed() + "x" + c.getBlue() + "x" + c.getGreen(), "human.png", 0, res);
        sp.setColor(c);
        List<Sprite> sprs = new ArrayList<>();
        return sprs;
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
            list.add(new Sprite("hair"+col.get(i)+"x"+row.get(i), "human_face.png", col.get(i), row.get(i)));
        }
        return list;
    }


    public static Sprite getHairSprite(int i) {
        return hairSprites.get(i);
    }

    public static int noOfHairs() {
        return hairSprites.size();
    }
}
