package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.Target;
import model.characters.decorators.AnimalOverlayDecorator;
import model.characters.general.GameCharacter;

/**
 * Created by erini02 on 20/10/16.
 */
public class MotionTracker extends GameItem {
    public MotionTracker() {
        super("Motion Tracker", 1.0, false, 190);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("motiontracker", "device.png", 4);
    }

    @Override
    public void gotGivenTo(Actor to, Target from) {
        super.gotGivenTo(to, from);
        to.setCharacter(new AnimalOverlayDecorator(to.getCharacter()));
        if (from instanceof Actor) {
            ((Actor)from).removeInstance(((GameCharacter ch) -> ch instanceof AnimalOverlayDecorator));
        }
    }

    @Override
    public GameItem clone() {
        return new MotionTracker();
    }
}
