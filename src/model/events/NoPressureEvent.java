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
import model.map.Room;
import model.objects.general.PressurePanel;

public class NoPressureEvent extends Event {

    private final boolean affectsAdjacent;
    private PressurePanel panelRef;
	private Room roomRef;
	private Actor performingClient;
    private List<LowPressureEvent> adjacentRoomEvents = new ArrayList<>();

    public NoPressureEvent(PressurePanel panelRef, Room roomRef,
                           Actor performingClient, boolean adjacent) {
		this.panelRef = panelRef;
		this.roomRef = roomRef;
		this.performingClient = performingClient;
        this.affectsAdjacent = adjacent;
		removeFires(roomRef);
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("nopressure", "decals.png", 1, 1, 32, 32);
    }

    private void removeFires(Room roomRef2) {
		// TODO Auto-generated method stub
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
        if (affectsAdjacent) {
            applyLowPressureInAdjacentRooms(gameData);
            addLowPressureToAdjacentRooms(gameData);
        }

		if (!shouldBeRemoved(gameData)) {
			for (Target t : roomRef.getTargets()) {
				t.beExposedTo(performingClient, new NoPressureDamage(t));
			}
		}

	}

    private void addLowPressureToAdjacentRooms(GameData gameData) {
        if (adjacentRoomEvents.isEmpty()) {
            for (Room r : roomRef.getNeighborList()) {
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
		return SensoryLevel.NO_SENSE;
	}

	@Override
	public boolean shouldBeRemoved(GameData gameData) {
        if (panelRef != null) {
            boolean res = panelRef.getPressure();
            if (res == true) {
                removeAdjacentEvents();
            }
            return res;
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
