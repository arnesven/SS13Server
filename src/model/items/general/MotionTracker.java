package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.Target;
import model.characters.decorators.AnimalOverlayDecorator;
import model.characters.general.GameCharacter;
import model.map.rooms.Room;

/**
 * Created by erini02 on 20/10/16.
 */
public class MotionTracker extends GameItem {
    public MotionTracker() {
        super("Motion Tracker", 1.0, false, 190);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("motiontracker", "device.png", 4, this);
    }

    @Override
    public void gotGivenTo(Actor to, Target from) {
        super.gotGivenTo(to, from);
        to.setCharacter(new AnimalOverlayDecorator(to.getCharacter()));
        if (from instanceof Actor) {
           removeDecorator((Actor)from);
        }
    }

    private void removeDecorator(Actor from) {
        if (from.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof AnimalOverlayDecorator)) {
            from.removeInstance(((GameCharacter ch) -> ch instanceof AnimalOverlayDecorator));
        }
    }

    @Override
    public void gotAddedToRoom(Actor cameFrom, Room to) {
        super.gotAddedToRoom(cameFrom, to);
        if (cameFrom != null) {
            removeDecorator(cameFrom);
        }
    }

    @Override
    public GameItem clone() {
        return new MotionTracker();
    }
}
