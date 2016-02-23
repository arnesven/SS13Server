package model.npcs;

import java.util.ArrayList;
import java.util.List;

import util.MyRandom;
import model.GameData;
import model.actions.AttackAction;
import model.actions.Target;
import model.items.Weapon;
import model.objects.GameObject;

public class AttackIfPossibleBehavior implements ActionBehavior {

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
			atk.addWithWhat(new Weapon("Claws", 0.75, 0.5, false));
			args.add(randomTarget.getName());
			args.add("Claws");
			atk.setArguments(args);
			System.out.println("A parasite is attacking " + randomTarget.getName() + "!");
			atk.printAndExecute(gameData);
		}
	}

}
