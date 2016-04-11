package model.objects.consoles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.objectactions.PowerConsoleAction;
import model.map.Room;
import model.objects.ElectricalMachinery;
import model.objects.GameObject;

public class GeneratorConsole extends Console {

	private interface PowerUpdater {
		double update(double currentPower, double lsPer, double liPer, double eqPer, 
				List<Room> ls, List<Room> lights, List<ElectricalMachinery> eq);
	}
	
	public static final double STARTING_POWER = 45.0;
	private static final double FIXED_INCREASE = STARTING_POWER * 0.15;
	private static final double ONGOING_INCREASE = STARTING_POWER * 0.08;
	
	private static final double EQUIPMENT_POWER_USE_PCT = 0.45;
	private static final double LIFE_SUPPORT_POWER_USE_PCT = 0.45;
	private static final double LIGHTING_POWER_USE_PCT = 0.10;
	
	private Map<String, PowerUpdater> updaters;
	private double level = STARTING_POWER;
	private double dlevel = 0.0;
	private List<String> prios;
	private ArrayList<Room> noLifeSupport = new ArrayList<>();
	private ArrayList<Room> noLight = new ArrayList<>();
	private ArrayList<ElectricalMachinery> noPower = new ArrayList<>();
	private int lightBefore;
	private int lsBefore;
	private int eqBefore;
	

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
		List<ElectricalMachinery> oldNoPower = noPower;
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

		Collections.sort(noPower);
		
		lightBefore = noLight.size();
		lsBefore = noLifeSupport.size();
		eqBefore = noPower.size();
		
		double currentPower = this.level + STARTING_POWER*0.01;
		for (String type : prios) {
			currentPower = updaters.get(type).update(currentPower, 
					lsPowerPerRoom, lightPowerPerRoom, eqPowerPer, 
					noLifeSupport, noLight, noPower);
		}
		
		System.out.println(getLSString());
		for (Room r : noLifeSupport) {
			System.out.println("     " + r.getName());
		}
		
		System.out.println(getLightString());
		
		System.out.println(getEquipmentString());
		runPowerOnOrOffFunctions(gameData, oldNoPower);
	}

	private void runPowerOnOrOffFunctions(GameData gameData,
			List<ElectricalMachinery> oldNoPower) {
		for (GameObject obj : gameData.getObjects()) {
			if (obj instanceof ElectricalMachinery) {
				ElectricalMachinery em = (ElectricalMachinery) obj;
				if (oldNoPower.contains(em) && ! getNoPowerObjects().contains(em)) {
					em.onPowerOn(gameData);
				} else if (!oldNoPower.contains(em) && getNoPowerObjects().contains(em)) {
					em.onPowerOff(gameData);
				}
			}
		}
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

	public List<Room> getNoLightRooms() {
		return noLight;
	}
	
	public static GeneratorConsole find(GameData gameData) {
		GeneratorConsole gc = null;
		for (GameObject o : gameData.getObjects()) {
			if (gc != null) {
				throw new IllegalStateException("More than one GeneratorConsole found!");
			} else if (o instanceof GeneratorConsole) {
				gc = (GeneratorConsole) o;
				break;
			}
		}
		if (gc == null) {
			throw new NoSuchElementException("Could not find a generator console on station!");
		}
		return gc;
	}

	public void addToLevel(double d) {
		level = Math.max(0.0, level + d);
	}

	public String getLSString() {
		return "Life support units (" + (lsBefore - noLifeSupport.size()) + "/" + lsBefore + ")";
	}

	public String getLightString() {
		return "Lights (" + (lightBefore - noLight.size()) + "/" + lightBefore + ")";
	}

	public String getEquipmentString() {
		return "Electrical equipment (" + (eqBefore - noPower.size()) + "/" + eqBefore + ")";
	}

	

}
