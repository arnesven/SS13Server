package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.Action;
import model.actions.general.AttackAction;
import model.characters.general.EyeballAlienCharacter;
import model.characters.general.MitosisAction;
import model.npcs.EyeballAlienNPC;
import model.objects.general.ElectricalMachinery;
import model.objects.general.GameObject;
import util.Logger;
import util.MyRandom;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EyeballAlienBehavior implements ActionBehavior {
    private static final double ATTACK_ELECTRONICS_CHANCE = 0.25;
    private double MITOSIS_CHANCE = 0.02;

    @Override
    public void act(Actor npc, GameData gameData) {
        MitosisAction ma = new MitosisAction();
        if (MyRandom.nextDouble() < MITOSIS_CHANCE) {
            ma.doTheAction(gameData, npc);
        } else if (EyeballAlienCharacter.getAngryAt().size() > 0) {
            if (npc.getInnermostCharacter() instanceof EyeballAlienCharacter) {
                EyeballAlienCharacter chara = (EyeballAlienCharacter)npc.getInnermostCharacter();
                if (inSameRoomAsAngryAt(chara)) {
                    Actor angerTarget = getRandomAngerTarget(chara);
                    attackThisTarget(gameData, npc, angerTarget.getAsTarget());
                }
            }
        } else if (MyRandom.nextDouble() < ATTACK_ELECTRONICS_CHANCE && currentRoomHasElectronics(npc)) {
                ElectricalMachinery brobj = getRandomElectroincs(npc);
                attackThisTarget(gameData, npc, (Target)brobj);
        } else if (npc.getPosition().getActors().size() > 1) {
            Actor target = getRandomFeelingTarget(npc);
            if (target != null) {
                Action fua = new FeelUpWithTentacleAction(getRandomFeelingTarget(npc));
                fua.doTheAction(gameData, npc);
            }
        }
    }

    private Actor getRandomFeelingTarget(Actor npc) {
        Set<Actor> actorsInRoom = new HashSet<>();
        actorsInRoom.addAll(npc.getPosition().getActors());
        actorsInRoom.removeIf((Actor a) -> a instanceof EyeballAlienNPC);
        if (actorsInRoom.isEmpty()) {
            return null;
        }
        return MyRandom.sample(actorsInRoom);
    }

    private Actor getRandomAngerTarget(EyeballAlienCharacter chara) {
        Set<Actor> actorsInRoom = new HashSet<>();
        actorsInRoom.addAll(chara.getPosition().getActors());
        actorsInRoom.retainAll(EyeballAlienCharacter.getAngryAt());
        return MyRandom.sample(actorsInRoom);
    }

    private void attackThisTarget(GameData gameData, Actor npc, Target t) {
        AttackAction atk = new AttackAction(npc);
        atk.addWithWhat(npc.getCharacter().getDefaultWeapon());
        atk.stripAllTargetsBut(t);
        Logger.log("Eyeball alien is trying to attack " + t.getName());
        List<String> args = new ArrayList<String>();
        args.add(t.getName());
        args.add(npc.getCharacter().getDefaultWeapon().getFullName(npc));
        atk.setActionTreeArguments(args, npc);
        atk.doTheAction(gameData, npc);
    }

    private boolean inSameRoomAsAngryAt(EyeballAlienCharacter chara) {
        for (Actor a : chara.getPosition().getActors()) {
            if (EyeballAlienCharacter.getAngryAt().contains(a)) {
                return true;
            }
        }
        return false;
    }

    private ElectricalMachinery getRandomElectroincs(Actor npc) {
        List<GameObject> objs = new ArrayList<>();
        objs.addAll(npc.getPosition().getObjects());
        objs.removeIf((GameObject ob) -> !(ob instanceof ElectricalMachinery) || ((ElectricalMachinery) ob).isBroken());
        return (ElectricalMachinery)MyRandom.sample(objs);
    }

    private boolean currentRoomHasElectronics(Actor npc) {
        for (GameObject obj : npc.getPosition().getObjects()) {
            if (obj instanceof ElectricalMachinery && !((ElectricalMachinery)obj).isBroken()) {
                return true;
            }
        }
        return false;
    }
}
