package model.objects.general;

import java.util.ArrayList;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.NoPowerAction;
import model.events.ambient.SimulatePower;
import model.map.rooms.Room;

public abstract class ElectricalMachinery extends BreakableObject
			implements Repairable, PowerConsumer {

	private boolean inUse = false;
	private int powerPriority = 5; // lowest priority
	private SimulatePower powerSim = null;

	public ElectricalMachinery(String name, Room r) {
		super(name, 1.5, r);
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
		return !powerSim.getNoPowerObjects().contains(this);
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
		//Logger.log("## adding specific action for electrical");
		ArrayList<Action> at2 = new ArrayList<>();
		if (!isPowered()) {
			//Logger.log("####" + this.getName() + " isn't powered! no power actions!");
			super.addSpecificActionsFor(gameData, cl, at2);
			for (Action a : at2) {
				NoPowerAction npa = new NoPowerAction(a);
				at.add(npa);
			}
		} else {
			super.addSpecificActionsFor(gameData, cl, at);
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

}
