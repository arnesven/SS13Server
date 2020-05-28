package model.objects.general;

import java.util.ArrayList;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.NoPowerAction;
import model.characters.general.ChimpCharacter;
import model.events.ambient.SimulatePower;
import model.map.doors.PowerCord;
import model.map.rooms.Room;
import model.objects.power.ElectricalMachineryAPCWire;

public abstract class ElectricalMachinery extends BreakableObject
			implements Repairable, PowerConsumer {

	private final ElectricalMachineryAPCWire apcWire;
	private boolean inUse = false;
	private int powerPriority = 5; // lowest priority
	private SimulatePower powerSim = null;

	public ElectricalMachinery(String name, Room r) {
		super(name, 1.5, r);
		this.apcWire = new ElectricalMachineryAPCWire();
	}

	public void setInUse(boolean b) {
		this.inUse = b;
	}
	
	public boolean isInUse() {
		return inUse;
	}

	public SimulatePower getPowerSimulation() {
		return powerSim;
	}

	public boolean isPowered() {
		if (powerSim == null) {
			return false;
		}
		return !powerSim.getNoPowerObjects().contains(this) && !apcWire.isCut();
	}


	public int getPowerPriority() { return this.powerPriority; }

	public void setPowerPriority(int i) {
		this.powerPriority = i;
	}

	@Override
    public void receiveEnergy(GameData gameData, double energy) {}
	
	@Override
	public void addSpecificActionsFor(GameData gameData, Actor cl,
                                      ArrayList<Action> at) {
		if (cl.isIntelligentCreature()) {
			ArrayList<Action> at2 = new ArrayList<>();
			if (!isPowered()) {
				super.addSpecificActionsFor(gameData, cl, at2);
				for (Action a : at2) {
					NoPowerAction npa = new NoPowerAction(a);
					at.add(npa);
				}
			} else {
				super.addSpecificActionsFor(gameData, cl, at);
			}
		}
	}



	@Deprecated
	private static boolean isPowered(GameData gameData, ElectricalMachinery machine) {
		if (machine.getPowerSimulation() == null) {
			return false;
		}
		return machine.getPowerSimulation().getNoPowerObjects().contains(machine);
    }

    @Deprecated
	private boolean isPowered(GameData gameData) {
		return isPowered(gameData, this);
	}
	
	public void onPowerOn(GameData gameData) {  }
	
	public void onPowerOff(GameData gameData) {  }

    @Override
    public void doWhenRepaired(GameData gameData) {

    }

    @Override
	public double getPowerConsumption() {
		return 0.001000; // default consumption = 1kW
	}

	public boolean canBeOvercharged() {
		return true;
	}

	public void setPowerSimulation(SimulatePower simulatePower) {
		this.powerSim = simulatePower;
	}

	@Override
	public String getTypeName() {
		return "Equipment";
	}

	public PowerCord getAPCWire() {
		return apcWire;
	}
}
