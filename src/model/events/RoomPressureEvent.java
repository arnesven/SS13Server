package model.events;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.events.ambient.ElectricalFire;
import model.events.ambient.PressureSimulation;
import model.events.damage.AsphyxiationDamage;
import model.events.damage.NoPressureDamage;
import model.map.rooms.Room;

import java.util.Iterator;

public class RoomPressureEvent extends Event {
    private static final Double THRESHOLD_HIGH = 0.95;
    private static final Double THRESHOLD_MEDIUM = 0.75;
    private static final Double THRESHOLD_LOW = 0.35;
    private static final Double THRESHOLD_VERY_LOW = 0.15;
    private final PressureSimulation simulation;
    private final Room room;

    public RoomPressureEvent(PressureSimulation pressureSimulation, Room r) {
        this.simulation = pressureSimulation;
        this.room = r;
    }



    @Override
    public void apply(GameData gameData) {

    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (simulation.getPressureFor(room) >= THRESHOLD_HIGH) {
            return new Sprite("airpressurehigh", "screen1.png", 9, 13, this);
        } else if (simulation.getPressureFor(room) >= THRESHOLD_MEDIUM) {
            return new Sprite("airpressuremed", "screen1.png", 10, 13, this);
        } else if (simulation.getPressureFor(room) >= THRESHOLD_LOW) {
            return new Sprite("airpressurelow", "screen1.png", 13, 13, this);
        } else if (simulation.getPressureFor(room) >= THRESHOLD_VERY_LOW) {
            return new Sprite("airpressureverylow", "screen1.png", 14, 13, this);
        }
        return new Sprite("airpressurenone", "screen1.png", 11, 13, this);
    }



    @Override
    public boolean showSpriteInTopPanel() {
        return true;
    }

    @Override
    public boolean showSpriteInRoom() {
        return false;
    }

    @Override
    public String howYouAppear(Actor performingClient) {
        return String.format("Airpressure here: %d", (int)(simulation.getPressureFor(room)*100)) + "%";
    }

    @Override
    public SensoryLevel getSense() {
        if (simulation.getPressureFor(room) >= THRESHOLD_MEDIUM) {
            return new SensoryLevel(SensoryLevel.VisualLevel.INVISIBLE,
                    SensoryLevel.AudioLevel.SAME_ROOM, SensoryLevel.OlfactoryLevel.UNSMELLABLE);
        }
        return new SensoryLevel(SensoryLevel.VisualLevel.INVISIBLE,
                SensoryLevel.AudioLevel.VERY_LOUD, SensoryLevel.OlfactoryLevel.UNSMELLABLE);
    }

    @Override
    public String getDistantDescription() {
        return "You feel a powerful wind whooshing past you.";
    }

    public static void exposeTargetToPressure(Double press, Target t, GameData gameData) {
        if (press >= THRESHOLD_HIGH) {
            // Nothing, it's OK...
        } else if (press >= THRESHOLD_MEDIUM) {
            // Nothing, it's kind of OK...
        } else if (press >= THRESHOLD_LOW) {
            t.beExposedTo(null, new AsphyxiationDamage(t, 1.0), gameData);
        } else if (press >= THRESHOLD_VERY_LOW) {
            t.beExposedTo(null, new AsphyxiationDamage(t, 1.0), gameData);
        } else {
            t.beExposedTo(null, new NoPressureDamage(t), gameData);
        }
    }

    public static void removeFireIfApplicable(Room r, Double press) {
        if (press < THRESHOLD_VERY_LOW) {
            removeFires(r);
        }

    }

    private static void removeFires(Room roomRef2) {
        for (Iterator<Event> it = roomRef2.getEvents().iterator(); it.hasNext(); ) {
            Event e = it.next();
            if (e instanceof ElectricalFire) {
                e.setShouldBeRemoved(true);
                it.remove();
            }
        }
    }

    public PressureSimulation getPressureSimulation() {
        return simulation;
    }

    public static RoomPressureEvent getPressureEventForRoom(Room r) {
        for (Event e : r.getEvents()) {
            if (e instanceof RoomPressureEvent) {
                return (RoomPressureEvent) e;
            }
        }
        return null;
    }
}
