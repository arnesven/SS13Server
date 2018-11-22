package model.items;

import model.Actor;

public class BodyPartFactory {
    public static BodyPart makeBodyPart(String bodyPart, Actor target) {
        if (bodyPart.equals("left arm")) {
            return new SeveredArm("left", target);
        }

        return new SeveredArm("right", target);
    }
}
