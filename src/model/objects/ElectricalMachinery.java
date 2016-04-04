package model.objects;

import java.util.ArrayList;

import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.NoPowerAction;
import model.actions.objectactions.PowerConsoleAction;
import model.map.Room;


public abstract class ElectricalMachinery extends BreakableObject implements Repairable {

	private boolean inUse = false;

	public ElectricalMachinery(String name, Room r) {
		super(name, 1.5, r);
	}

	public void setInUse(boolean b) {
		System.out.println("in use = " + b);
		this.inUse = b;
	}
	
	public boolean isInUse() {
		return inUse;
	}
	
	
	@Override
	public void addSpecificActionsFor(GameData gameData, Player cl,
			ArrayList<Action> at) {
		System.out.println("## adding specific action for electrical");
		ArrayList<Action> at2 = new ArrayList<>();
		if (!isPowered(gameData, this)) {
			System.out.println("####" + this.getName() + " isn't powered! no power actions!");
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
		return !(GeneratorConsole.find(gameData).getNoPowerObjects().contains(machine));
	}
	
}
