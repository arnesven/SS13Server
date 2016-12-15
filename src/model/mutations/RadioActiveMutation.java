package model.mutations;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.decorators.CharacterDecorator;
import model.events.Event;
import model.events.ambient.LocalRadiationEvent;
import model.map.rooms.Room;

/**
 * Created by erini02 on 19/04/16.
 */
public class RadioActiveMutation extends Mutation {
    public RadioActiveMutation() {
        super("Radioactive");
    }

    @Override
    public CharacterDecorator getDecorator(final Actor forWhom) {
        return new CharacterDecorator(forWhom.getCharacter(), "Radioactive") {
            private Event ev;

            @Override
            public Sprite getSprite(Actor whosAsking) {
                return new Sprite("radioactiveguy", "genetics.png", 12);
            }

            @Override
            public void setPosition(Room room) {
                if (ev != null) {
                    getPosition().getEvents().remove(ev);
                    ev.setShouldBeRemoved(true);
                }
                ev = new LocalRadiationEvent(forWhom.getPosition(), forWhom);
                super.setPosition(room);
                getPosition().getEvents().add(ev);
            }
        };
    }
}
