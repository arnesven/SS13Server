package graphics.sprites;

import model.Actor;
import model.items.BodyPart;
import util.MyRandom;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by erini02 on 26/04/16.
 */
public class PhysicalBody implements SpriteObject, Serializable {

    private static List<Sprite> hairSprites = collectHairSprites();
    private static List<Sprite> facialHairSprites = collectFacialHairSprites();
    private boolean hasABrain;
    //private static List<Sprite> nakedSprites = nakedSpriteList();

    private boolean gender;
    private Color hairColor;
    private int hairNum;
    private Color facialHairColor;
    private int facialHairNum;
    private int nakedSpriteNum;

    private Map<String, Boolean> hasBodyParts;
    private static Map<String, Sprite> bodyPartSprites = makeNakedMap();



    public PhysicalBody(boolean gender, int hairNum, Color color, int facialHairNum, Color facialHairColor) {
        this.gender = gender;
        this.hairColor = color;
        this.hairNum = hairNum;
        this.facialHairColor =  facialHairColor;
        this.facialHairNum = facialHairNum;
        hasBodyParts = new HashMap<>();
        for (String part : BodyPart.getAllParts()) {
            hasBodyParts.put(part, true);
        }
        this.hasABrain = true;
    }

    public PhysicalBody(boolean gender) {
        this(gender, MyRandom.nextInt(hairSprites.size()), MyRandom.randomHairColor(),
                MyRandom.nextInt(facialHairSprites.size()), MyRandom.randomHairColor());
    }

//    private static List<Sprite> getHairNumber(int hairNum, Color color) {
//        List<Sprite> res = new ArrayList<>();
//        res.add(hairSprites.get(hairNum));
//        // Logger.log("Ran Random Hair");
//        res.get(0).setColor(color);
//        return res;
//    }
//
//    private static List<Sprite> getRandomHair() {
//        List<Sprite> res = new ArrayList<>();
//        res.add(MyRandom.sample(hairSprites));
//       // Logger.log("Ran Random Hair");
//        Color c = new Color(MyRandom.nextInt(255), MyRandom.nextInt(255), MyRandom.nextInt(255));
//        Sprite sp = new Sprite(res.get(0).getName() + c.getRed() + "x" + c.getBlue() + "x" + c.getGreen(), "human.png", 0, res);
//        sp.setColor(c);
//        List<Sprite> sprs = new ArrayList<>();
//        return sprs;
//    }



    private static List<Sprite> collectHairSprites() {
        List<Sprite> list = new ArrayList<>();

        list.addAll(collectSprites(5, 17));
        list.addAll(collectSprites(57, 69));
        list.addAll(collectSprites(89, 102));
        list.addAll(collectSprites(109, 305));
        list.addAll(collectSprites(777, 778));
        list.addAll(collectSprites(805, 895+31));

        return list;
    }


    private static List<Sprite> collectFacialHairSprites() {
        List<Sprite> list = new ArrayList<>();
        list.add(new Sprite("nofacialhair", "human_face.png", 2, null));
        list.addAll(collectSprites(17, 53));
        list.addAll(collectSprites(69, 86));
        list.addAll(collectSprites(105, 106));
        list.addAll(collectSprites(781, 802));
        return list;
    }


    private static List<Sprite> collectSprites(int offset, int to) {
        List<Sprite> list = new ArrayList<>();

        List<Integer> col = new ArrayList<>();
        List<Integer> row = new ArrayList<>();
        for (int i = offset; i < to; i +=4) {
            col.add(i % 31);
            row.add(i / 31);
        }

        for (int i = 0; i < col.size(); ++i) {
            list.add(new Sprite("hair"+col.get(i)+"x"+row.get(i), "human_face.png", col.get(i), row.get(i), null));
        }
        return list;
    }


    public static Sprite getHairSprite(int i) {
        return hairSprites.get(i);
    }

    public static int noOfHairs() {
        return hairSprites.size();
    }


    public static int noOfFacials() {
        return facialHairSprites.size();
    }

//    public static List<Sprite> getNakedSprites() {
//        return nakedSprites;
//    }

    public Sprite getSprite() {
        List<Sprite> list = new ArrayList<>();
        Sprite hair = new Sprite("hair" + hairNum+colorToString(hairColor), "human_face.png",
                hairSprites.get(hairNum).getColumn(), hairSprites.get(hairNum).getRow(), this);
        hair.setColor(hairColor);
        Sprite facial = new Sprite("facialhair" + facialHairNum + colorToString(facialHairColor), "human_face.png",
                facialHairSprites.get(facialHairNum).getColumn(), facialHairSprites.get(facialHairNum).getRow(), this);
        facial.setColor(facialHairColor);
        //list.add(nakedSprites.get(nakedSpriteNum));
        list.add(makeNakedSpriteFromParts());
        if (hasBodyParts.get("head")) {
            list.add(hair);
            list.add(facial);
        }


        Sprite naked = new Sprite("nakedmanbase" + getNameString(), "human.png", 0, list, this);
        return naked;
    }

    private Sprite makeNakedSpriteFromParts() {
        List<Sprite> list = new ArrayList<>();
        String nameString = "";



        if (hasBodyParts.get("left leg")) {
            list.add(bodyPartSprites.get("left leg"));
            nameString += "lleg";
        }

        if (hasBodyParts.get("right leg")) {
            list.add(bodyPartSprites.get("right leg"));
            nameString += "rleg";
        }



        if (!gender) {
            nameString += "mantorso";
            list.add(bodyPartSprites.get("man torso"));
            if (hasBodyParts.get("head")) {
                list.add(bodyPartSprites.get("man head"));
                nameString += "hd";
            }
        } else {
            nameString += "womantorso";
            list.add(bodyPartSprites.get("woman torso"));
            if (hasBodyParts.get("head")) {
                list.add(bodyPartSprites.get("woman head"));
                nameString += "hd";
            }
        }

        if (hasBodyParts.get("left arm")) {
            list.add(bodyPartSprites.get("left arm"));
            nameString += "lrm";
        }

        if (hasBodyParts.get("right arm")) {
            list.add(bodyPartSprites.get("right arm"));
            nameString += "rrm";
        }

        Sprite total = new Sprite("nakedcomposition" + nameString, "human.png", 0, list, this);
        return total;
    }

    public String getNameString() {
        return (gender ? "woman" : "man") + hairNum +"-"+colorToString(hairColor)+"-"
                + facialHairNum + colorToString(facialHairColor);
    }

    private String colorToString(Color hairColor) {
        return "r" + hairColor.getRed() + "g" + hairColor.getGreen() + "b"+hairColor.getBlue();
    }

    public void setHairNumber(int hairNumber) {
        this.hairNum = hairNumber;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
        this.facialHairColor = hairColor;
    }

    public static Sprite getFacialHairSprite(int target) {
        return facialHairSprites.get(target);
    }

    public void setFacialHairNumber(int facialHairNumber) {
        this.facialHairNum = facialHairNumber;
    }


//    private static List<Sprite> nakedSpriteList() {
//        List<Sprite> sprs = new ArrayList<>();
//        sprs.add(new Sprite("nakedman",       "naked.png", 0));
//        sprs.add(new Sprite("nakedwoman",     "naked.png", 2));
//        sprs.add(new Sprite("nakednakedman",  "human.png", 3));
//        sprs.add(new Sprite("nakednakedwoam", "human.png", 8));
//        sprs.add(new Sprite("nakedfatman",    "human.png", 128));
//        return sprs;
//    }

//
//    public static int noOfHumans() {
//        return nakedSprites.size();
//    }

    public void setNakedSpriteNum(int nakedSpriteNum) {
        this.nakedSpriteNum = nakedSpriteNum;
    }

    public boolean hasBodyPart(String part) {
        return hasBodyParts.get(part);
    }

    public void removeBodyPart(String bodyPart) {
        hasBodyParts.put(bodyPart, false);
    }

    private static Map<String,Sprite> makeNakedMap() {
        Map<String, Sprite> bodyPartSprites = new HashMap<>();
        bodyPartSprites.put("man head", new Sprite("nakedmanhead", "body_parts.png", 0, 1, null));
        bodyPartSprites.put("woman head", new Sprite("nakedwomanhead", "body_parts.png", 7, 1, null));
        bodyPartSprites.put("left arm", new Sprite("nakedleftarm", "body_parts.png", 2, 1, null));
        bodyPartSprites.put("right arm", new Sprite("nakedrightarm", "body_parts.png", 3, 1, null));
        bodyPartSprites.put("man torso", new Sprite("nakedmantorso", "body_parts.png", 1, 1, null));
        bodyPartSprites.put("woman torso", new Sprite("nakedwomantorso", "body_parts.png", 6, 1, null));
        bodyPartSprites.put("left leg", new Sprite("nakedleftleg", "body_parts.png", 4, 1, null));
        bodyPartSprites.put("right leg", new Sprite("nakedrightleg", "body_parts.png", 5, 1, null));
        return bodyPartSprites;
    }

    public void addBodyPart(String bodyPart) {
        hasBodyParts.put(bodyPart, true);
    }

    public void removeBrain() {
        this.hasABrain = false;
    }

    public boolean hasABrain() {
        return hasABrain;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return getSprite();
    }

    @Override
    public String getPublicName(Actor whosAsking) {
        return "Physical Body";
    }
}
