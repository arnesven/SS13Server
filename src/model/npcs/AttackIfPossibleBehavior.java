package model.npcs;

import java.util.ArrayList;
import java.util.List;

import util.MyRandom;
import model.GameData;
import model.Target;
import model.actions.AttackAction;
import model.items.GameItem;
import model.items.weapons.Weapon;
import model.objects.GameObject;

public class AttackIfPossibleBehavior implements ActionBehavior {

	private Weapon defaultWeapon;

	public AttackIfPossibleBehavior(Weapon w) {
		this.defaultWeapon = w;
	}
	
	@Override
	public void act(NPC npc, GameData gameData) {
		AttackAction atk = new AttackAction(npc);
		List<Target> targets = new ArrayList<Target>();
		targets.addAll(atk.getTargets());
		
		for (Target t : atk.getTargets()) {
			if (t instanceof NPC) {
				NPC targetAsNPC = (NPC)t;
				if (targetAsNPC.getName().equals(t.getName())) {
					targets.remove(t);
				}
			} else if (t instanceof GameObject) {
				targets.remove(t);
			}
		}

		if (targets.size() > 0) {
			Target randomTarget = targets.get(MyRandom.nextInt(targets.size()));
			List<String> args = new ArrayList<String>();
			Weapon weaponToUse = getWeapon(npc);
			atk.addWithWhat(weaponToUse);
			args.add(randomTarget.getName());
			args.add(weaponToUse.getName());
			atk.setArguments(args);
			System.out.println(npc.getPublicName() + " is attacking " + randomTarget.getName() + "!");
			atk.printAndExecute(gameData);
		}
	}

	private Weapon getWeapon(NPC npc) {
		for (GameItem it : npc.getItems()) {
			if (it instanceof Weapon) {
				if (((Weapon)it).isReadyToUse()) {
					return (Weapon)it;
				}
			}
		}

		return defaultWeapon;
	}

}
