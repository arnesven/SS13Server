package model.mutations;

import model.Actor;
import model.Player;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.events.Event;
import model.events.ambient.LocalRadiationEvent;
import model.events.ambient.RadiationStorm;
import model.map.Room;

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
            public char getIcon(Player whosAsking) {
                return ',';
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
