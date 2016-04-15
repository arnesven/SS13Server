package model.npcs;

import model.Actor;
import model.characters.general.GameCharacter;
import model.events.AsphyxiationDamage;
import model.events.ColdDamage;
import model.events.Damager;
import model.events.RadiationDamage;
import model.map.Room;
import model.npcs.behaviors.ActionBehavior;
import model.npcs.behaviors.MovementBehavior;
import model.objects.general.Repairable;

import java.util.ArrayList;
import java.util.List;

public class RobotNPC extends NPC implements Repairable {

    private static List<String> availableRobotNames;

	public RobotNPC(GameCharacter chara, MovementBehavior m, ActionBehavior a,
			Room r) {
		super(chara, m, a, r);
		this.setHealth(4.0);
		this.setMaxHealth(4.0);
	}

    public static List<String> getAvailableRobotNames() {
        if (availableRobotNames == null) {
            availableRobotNames = addDefaultRobotNames();
        }
        return availableRobotNames;
    }

    private static List<String> addDefaultRobotNames() {
        List<String> names = new ArrayList<>();
        names.add("CASE");
        names.add("BART");
        names.add("DALE");
        names.add("FRAN");
        names.add("GREG");
        names.add("HANK");
        names.add("JEAN");
        names.add("KIPP");
        names.add("LUKE");
        names.add("MORT");
        names.add("NICK");
        names.add("PATT");
        names.add("QUIN");
        names.add("ROLF");
        names.add("SEAN");
        names.add("VICK");
        names.add("ZACK");
        return names;
    }


    @Override
	public boolean hasInventory() {
		return false;
	}

	@Override
	public boolean isDamaged() {
		return getCharacter().getHealth() < this.getMaxHealth();
	}

	@Override
	public boolean isBroken() {
		return isDead();
	}
	
	@Override
	public boolean isHealable() {
		return false;
	}
	
	@Override
	public void beExposedTo(Actor performingClient, Damager damage) {
		if (damage instanceof AsphyxiationDamage || damage instanceof RadiationDamage || damage instanceof ColdDamage) {
			return;
		}
		super.beExposedTo(performingClient, damage);
	}

    public static void removeRobotName(String chosenName) {
        if (availableRobotNames != null) {
            availableRobotNames.remove(chosenName);
        }
    }
}
