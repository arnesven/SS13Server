package model.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.events.ambient.ElectricalFire;
import model.events.ambient.LowPressureEvent;
import model.events.damage.NoPressureDamage;
import model.map.rooms.AirLockRoom;
import model.map.rooms.Room;
import model.objects.general.AirlockPanel;

public class NoPressureEvent extends Event {

    private final boolean affectsAdjacent;
  	private Room roomRef;
	private Actor performingClient;
    private List<LowPressureEvent> adjacentRoomEvents = new ArrayList<>();

    public NoPressureEvent(Room roomRef, Actor performingClient, boolean adjacent) {
		this.roomRef = roomRef;
		this.performingClient = performingClient;
        this.affectsAdjacent = adjacent;
		removeFires(roomRef);
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("nopressure", "screen2.png", 6, 2, this);
    }

    @Override
    public boolean showSpriteInRoom() {
        return false;
    }

    private void removeFires(Room roomRef2) {
		for (Iterator<Event> it = roomRef2.getEvents().iterator(); it.hasNext(); ) {
			Event e = it.next();
			if (e instanceof ElectricalFire) {
				e.setShouldBeRemoved(true);
				it.remove();
			}
		}
	}


	@Override
	public void apply(GameData gameData) {
        //Logger.log("Running NO PRESSURE event in " + roomRef.getName());
        if (affectsAdjacent) {
            applyLowPressureInAdjacentRooms(gameData);
            addLowPressureToAdjacentRooms(gameData);
        }

		if (!shouldBeRemoved(gameData)) {
			for (Target t : roomRef.getTargets(gameData)) {
               // Logger.log(" hit " + t.getName());
				t.beExposedTo(performingClient, new NoPressureDamage(t), gameData);
			}
		}

	}

    private void addLowPressureToAdjacentRooms(GameData gameData) {
            for (Room r : roomRef.getNeighborList()) {
                if (!LowPressureEvent.roomHasLowPressure(r)) {
                    LowPressureEvent e = new LowPressureEvent(r);
                    adjacentRoomEvents.add(e);
                    r.addEvent(e);
                }
            }
    }

    private void applyLowPressureInAdjacentRooms(GameData gameData) {
        for (LowPressureEvent e: adjacentRoomEvents) {
            e.apply(gameData);
        }
    }

    private void removeAdjacentEvents() {
        Iterator<LowPressureEvent> it = adjacentRoomEvents.iterator();
        while (it.hasNext()) {
            LowPressureEvent ev = it.next();
            ev.getRoom().removeEvent(ev);
            it.remove();
        }
    }

    @Override
	public String howYouAppear(Actor performingClient) {
		return "No Pressure!";
	}

	@Override
	public SensoryLevel getSense() {
		return new SensoryLevel(SensoryLevel.VisualLevel.INVISIBLE,
                SensoryLevel.AudioLevel.VERY_LOUD,
                SensoryLevel.OlfactoryLevel.UNSMELLABLE);
	}

	@Override
	public boolean shouldBeRemoved(GameData gameData) {
        if (roomRef != null) {
            boolean roomHasPressure = !hasNoPressureEvent(roomRef);
            if (roomHasPressure) {
                removeAdjacentEvents();
            }
            return roomHasPressure;
        } else {
            return false;
        }
	}



    public static boolean hasNoPressureEvent(Room r) {
        for (Event e : r.getEvents()) {
            if (e instanceof NoPressureEvent) {
                return true;
            }
        }
        return false;
    }


}
