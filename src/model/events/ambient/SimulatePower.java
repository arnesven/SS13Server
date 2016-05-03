package model.events.ambient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.events.Event;
import model.events.SpontaneousExplosionEvent;
import model.items.NoSuchThingException;
import model.map.Room;
import model.objects.consoles.GeneratorConsole;
import util.Logger;
import util.MyRandom;

public class SimulatePower extends Event {

	private Map<Room, Integer> roundsWithoutLS = new HashMap<>();
	private Map<Room, ColdEvent> lsMap = new HashMap<>();
	private boolean alreadyAddedDarkness = false;
	

	@Override
	public void apply(GameData gameData) {
		addDarknessEvent(gameData);
        GeneratorConsole gc;
        try {
            gc = gameData.findObjectOfType(GeneratorConsole.class);
        } catch (NoSuchThingException e) {
            Logger.log(Logger.CRITICAL, "No need to simulate power, no power console on station.");
            return;
        }
        if (gc != null) {
			gc.updateYourself(gameData);
			handleLifeSupport(gameData, gc);
			handleOvercharge(gameData, gc);
		}
	}

	

	private void addDarknessEvent(GameData gameData) {
		if (!alreadyAddedDarkness) {
			gameData.addMovementEvent(new DarknessEvent(gameData));
			alreadyAddedDarkness  = true;
		}
	}



	private void handleOvercharge(GameData gameData, GeneratorConsole gc) {
		double outputPct = gc.getPowerOutput();
		
		if (MyRandom.nextDouble() < outputPct - 1.0) {
            Logger.log(Logger.INTERESTING,
                    "Increased power output (>100%) caused fire in generator room");
			gameData.getGameMode().addFire(gc.getPosition());
		}
		
		if (MyRandom.nextDouble() < outputPct - 1.5) {
            Logger.log(Logger.INTERESTING,
                    "High power output (>150%) caused fire in room adjacent to generator room");
			Room r = MyRandom.sample(gc.getPosition().getNeighborList());
			gameData.getGameMode().addFire(r);
		}
		
		if (MyRandom.nextDouble() < outputPct - 1.5) {
            Logger.log(Logger.INTERESTING,
                      "High power output (>150%) caused explosion in generator room");
			SpontaneousExplosionEvent exp = new SpontaneousExplosionEvent();
			exp.explode(gc.getPosition());
		}
	}



	private void handleLifeSupport(GameData gameData, GeneratorConsole gc) {
		if (roundsWithoutLS.isEmpty()) {
			for (Room r : gameData.getRooms()) {
				roundsWithoutLS.put(r, 0);
			}
		}
		
		// Update how long rooms have been without life support
		List<Room> noLSRooms = gc.getNoLifeSupportRooms();
		for (Room r : gameData.getRooms()) {
			if (noLSRooms.contains(r)) {
				roundsWithoutLS.put(r, roundsWithoutLS.get(r) + 1);
			} else {
				roundsWithoutLS.put(r, Math.max(0, roundsWithoutLS.get(r) - 1));
			}
		}
		
		for (Room r : gameData.getRooms()) {
			if (roundsWithoutLS.get(r) > 1) {
				if (lsMap.get(r) == null) { // there was no previous cold event
					ColdEvent cold = new ColdEvent(r);
					r.addEvent(cold);
					lsMap.put(r, cold);
				}
			} else if (roundsWithoutLS.get(r) == 0) {
				if (lsMap.get(r) != null) { // there is a cold event there..
					r.removeEvent(lsMap.get(r));
					lsMap.remove(r);
				}
			}
		}
		
		for (ColdEvent ev : lsMap.values()) {
			ev.apply(gameData);
		}

	}





	@Override
	public String howYouAppear(Actor performingClient) {
		return "";
	}

	@Override
	public SensoryLevel getSense() {
		return SensoryLevel.NO_SENSE;
	}

}
