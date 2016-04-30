package model.objects.general;

import java.util.ArrayList;

import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.NoPowerAction;
import model.items.NoSuchThingException;
import model.map.Room;
import model.objects.consoles.GeneratorConsole;
import util.Logger;


public abstract class ElectricalMachinery extends BreakableObject 
			implements Repairable, Comparable<ElectricalMachinery> {

	private boolean inUse = false;
	private int powerPriority = 5; // lowest priority

	public ElectricalMachinery(String name, Room r) {
		super(name, 1.5, r);
	}

	public void setInUse(boolean b) {
		this.inUse = b;
	}
	
	public boolean isInUse() {
		return inUse;
	}
	
	@Override
	public int compareTo(ElectricalMachinery arg0) {
		return this.powerPriority - arg0.powerPriority;
	}
	
	protected void setPowerPriority(int i) {
		this.powerPriority = i;
	}
	
	
	@Override
	public void addSpecificActionsFor(GameData gameData, Player cl,
			ArrayList<Action> at) {
		Logger.log("## adding specific action for electrical");
		ArrayList<Action> at2 = new ArrayList<>();
		if (!isPowered(gameData, this)) {
			Logger.log("####" + this.getName() + " isn't powered! no power actions!");
			super.addSpecificActionsFor(gameData, cl, at2);
			for (Action a : at2) {
				NoPowerAction npa = new NoPowerAction(a);
				at.add(npa);
			}
		} else {
			super.addSpecificActionsFor(gameData, cl, at);
		}
	}

	public static boolean isPowered(GameData gameData, ElectricalMachinery machine) {
        try {
            return !(GeneratorConsole.find(gameData).getNoPowerObjects().contains(machine));
        } catch (NoSuchThingException e) {
            Logger.log(Logger.CRITICAL, "No power console found. Electrical machinery has power.");
            return true;
        }
    }
	
	public boolean isPowered(GameData gameData) {
		return isPowered(gameData, this);
	}
	
	public void onPowerOn(GameData gameData) {  }
	
	public void onPowerOff(GameData gameData) {  }
	
}