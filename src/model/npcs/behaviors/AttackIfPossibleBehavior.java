package model.npcs.behaviors;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import util.Logger;
import util.MyRandom;
import model.GameData;
import model.Target;
import model.actions.general.AttackAction;
import model.items.general.GameItem;
import model.items.weapons.Weapon;
import model.npcs.NPC;

public abstract class AttackIfPossibleBehavior implements ActionBehavior {

	

	public AttackIfPossibleBehavior() {

	}
	
	@Override
	public void act(Actor npc, GameData gameData) {
		AttackAction atk = new AttackAction(npc);
		List<Target> targets = getTargets(npc, gameData, atk);

		if (targets.size() > 0) {
			Target randomTarget = targets.get(MyRandom.nextInt(targets.size()));
			List<String> args = new ArrayList<String>();
			Weapon weaponToUse = getWeapon(npc);
			atk.addWithWhat(weaponToUse);
			args.add(randomTarget.getName());
			args.add(weaponToUse.getFullName(npc));
			atk.setArguments(args, npc);
			Logger.log(npc.getPublicName() + " is attacking " + randomTarget.getName() + "!");
			atk.printAndExecute(gameData);

		}
	}

    protected List<Target> getTargets(Actor npc, GameData gameData, AttackAction atk) {
        List<Target> targets = new ArrayList<Target>();
        targets.addAll(atk.getTargets());

//        for (Target t : atk.getTargets()) {
//            if (t instanceof NPC) {
//                NPC targetAsNPC = (NPC)t;
//                if (targetAsNPC.getName().equals(t.getName())) {
//                    targets.remove(t);
//                }
//            } else if (t instanceof GameObject) {
//                targets.remove(t);
//            }
//        }
        return targets;
    }

    private Weapon getWeapon(Actor npc) {
		for (GameItem it : npc.getItems()) {
			if (it instanceof Weapon) {
				if (((Weapon)it).isReadyToUse()) {
					return (Weapon)it;
				}
			}
		}
		return npc.getCharacter().getDefaultWeapon();
	}

}
