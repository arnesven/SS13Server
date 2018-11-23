package model.items;

import model.Actor;

public class BodyPartFactory {
    public static BodyPart makeBodyPart(String bodyPart, Actor target) {
        if (bodyPart.equals("left arm")) {
            return new SeveredArm("left", target);
        } else if (bodyPart.equals("right arm")) {
            return new SeveredArm("right", target);
        } else if (bodyPart.equals("right leg")) {
            return new SeveredLeg("right", target);
        }  else if (bodyPart.equals("left leg")) {
            return new SeveredLeg("left", target);
        } else if (bodyPart.equals("buttocks")) {
            return new SeveredButt(target);
        }

        return new SeveredHead(target);
    }
}
