package model.items;

import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.Action;
import model.actions.itemactions.MindControlAction;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import util.Logger;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 19/10/16.
 */
public class CosmicArtifact extends GameItem {
    private final Sprite sprite;

    public CosmicArtifact() {
        super("Cosmic Artifact", 1.0, false, 1000);
        this.sprite = MyRandom.sample(spritelist(this));
        Logger.log(Logger.INTERESTING, "Cosmic Artifact Sprite is " + getSprite(null).getName());
    }

    private static List<Sprite> spritelist(SpriteObject obj) {
        List<Sprite> sprs = new ArrayList<>();
        for (int i = 17; i < 29; ++i) {
            sprs.add(new Sprite("cosmicartifact"+i, "weapons2.png", i, 24, 32, 32, obj));
        }
        sprs.add(new Sprite("cosmicartifacta", "weapons2.png", 7, 24, 32, 32, obj));
        sprs.add(new Sprite("cosmicartifactb", "weapons2.png", 11, 24, 32, 32, obj));
        return sprs;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return sprite;
    }

    @Override
    public GameItem clone() {
        return new CosmicArtifact();
    }

    @Override
    public void gotGivenTo(Actor to, Target from) {
        to.setCharacter(new SpeedChangeDecorator(to.getCharacter(), 9999.0));
        if (from instanceof Actor) {
            if (((Actor) from).getCharacter().checkInstance((GameCharacter ch) -> ch instanceof SpeedChangeDecorator)) {
                ((Actor) from).removeInstance((GameCharacter ch) -> ch instanceof SpeedChangeDecorator);
            }
        }
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        Action a = new MindControlAction(cl, gameData);
        if (a.getOptions(gameData, cl).numberOfSuboptions() > 0) {

            at.add(a);
        }
    }

    private class SpeedChangeDecorator extends CharacterDecorator {

        private final double newSpeed;

        public SpeedChangeDecorator(GameCharacter character, double v) {
            super(character, "Speed Change");
            this.newSpeed = v;
        }

        @Override
        public double getSpeed() {
            return newSpeed;
        }

    }
}
