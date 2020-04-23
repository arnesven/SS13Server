package model.objects.general;

import java.util.ArrayList;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.NoPowerAction;
import model.items.NoSuchThingException;
import model.map.rooms.Room;
import model.objects.consoles.PowerSource;
import util.Logger;


public abstract class ElectricalMachinery extends BreakableObject 
			implements Repairable, PowerConsumer, Comparable<ElectricalMachinery> {

	private boolean inUse = false;
	private int powerPriority = 5; // lowest priority
	private PowerSource source = null;

	public ElectricalMachinery(String name, Room r) {
		super(name, 1.5, r);
	}

	public void setInUse(boolean b) {
		this.inUse = b;
	}
	
	public boolean isInUse() {
		return inUse;
	}

	public PowerSource getPowerSource() {
		return source;
	}

	public void setPowerSource(PowerSource src) {
		Logger.log(Logger.INTERESTING, getName() + " hooked into power source!");
		this.source = src;
	}

	public boolean isPowered() {
		if (source == null) {
			return false;
		}
		return !source.getNoPowerObjects().contains(this);
	}

	@Override
	public int compareTo(ElectricalMachinery arg0) {
		return this.powerPriority - arg0.powerPriority;
	}
	
	protected void setPowerPriority(int i) {
		this.powerPriority = i;
	}
	
	
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
		if (machine.getPosition() == null) {
			Logger.log(Logger.CRITICAL, "Warning, position for machine " + machine.getName() + " was null! Cannot determine if it is powered or not.");
			return false;
		}
        try {
            for (Room r : gameData.getMap().getRoomsForLevel(gameData.getMap().getLevelForRoom(machine.getPosition()).getName())) {
                for (GameObject obj : r.getObjects()) {
                    if (obj instanceof PowerSource) {
                        if (((PowerSource) obj).getNoPowerObjects().contains(machine)) {
                            return false;
                        }
                    }
                }
            }
            return true;
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        return true;
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
    public double getPowerConsumptionFactor() {
        return 1.0;
    }

	public boolean canBeOvercharged() {
		return true;
	}
}
