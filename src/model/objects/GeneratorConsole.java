package model.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.objectactions.PowerConsoleAction;
import model.map.Room;

public class GeneratorConsole extends ElectricalMachinery {

	private interface PowerUpdater {
		double update(double currentPower, double lsPer, double liPer, double eqPer, 
				List<Room> ls, List<Room> lights, List<ElectricalMachinery> eq);
	}
	
	private static final double STARTING_POWER = 45.0;
	private static final double FIXED_INCREASE = STARTING_POWER * 0.15;
	private static final double ONGOING_INCREASE = STARTING_POWER * 0.08;
	
	private static final double EQUIPMENT_POWER_USE_PCT = 0.45;
	private static final double LIFE_SUPPORT_POWER_USE_PCT = 0.45;
	private static final double LIGHTING_POWER_USE_PCT = 0.10;
	
	private Map<String, PowerUpdater> updaters;
	private double level = STARTING_POWER;
	private double dlevel = 0.0;
	private List<String> prios;
	private boolean stateChanged = false;
	private ArrayList<Room> noLifeSupport = new ArrayList<>();
	private ArrayList<Room> noLight = new ArrayList<>();
	private ArrayList<ElectricalMachinery> noPower = new ArrayList<>();
	

	public GeneratorConsole(Room r) {
		super("Power Console", r);
		setupPrio();
		setupUpdaters();
	}

	private double internalUpdater(double cp, double pp, List<?> list) {
		while (cp - pp > 0 && list.size() > 0) {
			list.remove(0);
			cp -= pp;
		}
		return cp;
	}
	
	private void setupUpdaters() {
		updaters = new HashMap<>();
		updaters.put("Life Support", new PowerUpdater() {

			@Override
			public double update(double currentPower, double lsPer,
					double liPer, double eqPer, List<Room> ls,
					List<Room> lights, List<ElectricalMachinery> eq) {
				return internalUpdater(currentPower, lsPer, ls);
			}
			
		});
		updaters.put("Lighting", new PowerUpdater() {

			@Override
			public double update(double currentPower, double lsPer,
					double liPer, double eqPer, List<Room> ls,
					List<Room> lights, List<ElectricalMachinery> eq) {
				return internalUpdater(currentPower, liPer, lights);
			}
			
		});
		updaters.put("Equipment", new PowerUpdater() {

			@Override
			public double update(double currentPower, double lsPer,
					double liPer, double eqPer, List<Room> ls,
					List<Room> lights, List<ElectricalMachinery> eq) {
				return internalUpdater(currentPower, eqPer, eq);
			}
		});
	}

	private void setupPrio() {
		this.prios = new ArrayList<>();
		prios.add("Life Support");
		prios.add("Lighting");
		prios.add("Equipment");
	}

	@Override
	protected void addActions(GameData gameData, Player cl, ArrayList<Action> at) {
		at.add(new PowerConsoleAction(this));
	}

	public void setLevel(boolean fixed, boolean increase) {
		if (fixed) {
			level = Math.max(0.0, level + FIXED_INCREASE * (increase?+1.0:-1.0));
			dlevel = 0.0;
		} else {
			dlevel = ONGOING_INCREASE * (increase?+1.0:-1.0);
		}
		stateChanged = true;
		System.out.println("Powerlevel is now " + level);
	}

	public List<String> getPrios() {
		return prios;
	}

	public void setHighestPrio(String selected) {
		Iterator<String> it = prios.iterator();
		while(it.hasNext()) {
			if (it.next().equals(selected)) {
				it.remove();
			}
		}
		prios.add(0, selected);
		stateChanged = true;
		System.out.println("Prios are now: " + prios);
	}

	public double getPowerLevel() {
		return level;
	}

	public void updateYourself(GameData gameData) {
		level = Math.max(0.0, level+dlevel);
		System.out.println("Current power: " + level);
		
		noLifeSupport = new ArrayList<Room>();
		noLight       = new ArrayList<Room>();
		noPower       = new ArrayList<ElectricalMachinery>();
		
		List<Room> allRooms = new ArrayList<>();
		allRooms.addAll(gameData.getRooms());
				
		double noOfRooms = allRooms.size();
		double lsPowerPerRoom    = STARTING_POWER * LIFE_SUPPORT_POWER_USE_PCT / noOfRooms;
		double lightPowerPerRoom = STARTING_POWER * LIGHTING_POWER_USE_PCT / noOfRooms;	
		
		for (GameObject obj : gameData.getObjects()) {
			if (obj instanceof ElectricalMachinery) {
				noPower.add((ElectricalMachinery) obj);
			}
		}
		noPower.remove(this); // the generator console has its own power source and can never be unpowered.
		Collections.shuffle(noPower);

		double eqPowerPer = STARTING_POWER * EQUIPMENT_POWER_USE_PCT / (double)(noPower.size());
		System.out.println(" POWER: Priority is " + prios);
		System.out.println(" POWER: Life support (MW per room): " + lsPowerPerRoom);
		System.out.println(" POWER: Lighting (MW per room)    : " + lightPowerPerRoom);
		System.out.println(" POWER: Equipment (MW per machine): " + eqPowerPer);
		
		noLight.addAll(gameData.getRooms());
		Collections.shuffle(noLight);
		noLifeSupport.addAll(gameData.getRooms());
		Collections.shuffle(noLifeSupport);

		int lightBefore = noLight.size();
		int lsBefore = noLifeSupport.size();
		int eqBefore = noPower.size();
		
		double currentPower = this.level;
		for (String type : prios) {
			currentPower = updaters.get(type).update(currentPower, 
					lsPowerPerRoom, lightPowerPerRoom, eqPowerPer, 
					noLifeSupport, noLight, noPower);
		}
		
		System.out.println(" POWER: Rooms without life support (" + noLifeSupport.size() + "/" + lsBefore + "):");
		for (Room r : noLifeSupport) {
			System.out.println("     " + r.getName());
		}
		
		System.out.println(" POWER: Rooms without lighting (" + noLight.size() + "/" + lightBefore + "):");
//		for (Room r : noLight) {
//			System.out.println("     " + r.getName());
//		}
		
		System.out.println(" POWER: Machines without power (" + noPower.size() + "/" + eqBefore + "):");
//		for (ElectricalMachinery r : noPower) {
//			System.out.println("     " + r.getName());
//		}
	}

	public List<Room> getNoLifeSupportRooms() {
		return noLifeSupport;
	}

	public double getPowerOutput() {
		return (level/GeneratorConsole.STARTING_POWER);
	}

	public List<ElectricalMachinery> getNoPowerObjects() {
		return noPower;
	}

}
