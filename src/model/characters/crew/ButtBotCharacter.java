package model.characters.crew;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.RobotCharacter;

public class ButtBotCharacter extends RobotCharacter {
    public ButtBotCharacter(String chosenName, int i, double v) {
        super(chosenName, i, v);
    }

    @Override
    public Sprite getNormalSprite(Actor whosAsking) {
        return new Sprite("robot", "robots.png", 50, getActor());
    }
}
