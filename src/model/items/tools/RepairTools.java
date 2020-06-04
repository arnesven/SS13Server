package model.items.tools;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.Action;
import model.actions.itemactions.RepairAction;
import model.actions.itemactions.SealHullBreachAction;
import model.items.general.GameItem;
import model.items.general.Tools;
import model.map.rooms.Room;
import model.objects.general.GameObject;
import model.objects.general.Repairable;

import java.util.ArrayList;

public class RepairTools extends Tools {
    public RepairTools() {
        super("Repair", 80);
    }

    @Override
    public Sprite getHandHeldSprite() {
        return new Sprite("repairtoolsheldinhand", "items_righthand.png", 9, 42, null);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("repairtools", "storage.png", 15, this);
    }


    @Override
    protected String getToolsDescription(GameData gameData, Player performingClient) {
        return "Tool kit containing a wrench, a screwdriver, a pair of pliers, a welding torch and a roll of vacu-tape. Good for repairing stuff.";
    }


    @Override
	public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
		super.addYourActions(gameData, at, cl);
		if (hasBrokenObjects(cl.getPosition())) {
			at.add(new RepairAction(cl));
		}
		if (cl.getPosition().hasHullBreach()) {
			at.add(new SealHullBreachAction());
		}
  	}


    private boolean hasBrokenObjects(Room position) {
		for (GameObject ob : position.getObjects()) {
			if (ob instanceof Target) {
				if (ob instanceof Repairable) {
					if (((Repairable)ob).isDamaged() || ((Repairable)ob).isBroken()) {
						return true;
					}
				}
			}
		}
		return false;
	}



    @Override
    public GameItem clone() {
        return new RepairTools();
    }
}
