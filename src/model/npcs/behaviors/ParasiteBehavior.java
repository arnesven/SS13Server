package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.characteractions.FaceHuggingAction;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.characters.MouseCharacter;
import model.characters.general.AnimalCharacter;
import model.characters.general.CatCharacter;
import model.characters.general.ChangelingCharacter;
import model.characters.general.ParasiteCharacter;
import model.characters.special.AlienCharacter;
import model.events.Event;
import model.events.ambient.SoundInRoomEvent;
import sounds.Sound;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 30/11/16.
 */
public class ParasiteBehavior implements ActionBehavior {

    private final AttackAllActorsButNotTheseClasses attackBehavior;

    public ParasiteBehavior() {
        this.attackBehavior = new ParasiteAttackBehavior();
    }

    @Override
    public void act(Actor npc, GameData gameData) {
         TargetingAction act = new FaceHuggingAction(npc);
        if (MyRandom.nextDouble() < 0.03 && act.getNoOfTargets() > 0) {
            Target t = MyRandom.sample(act.getTargets());
            List<String> args = new ArrayList<>();
            args.add(t.getName());
            act.setArguments(args, npc);
            act.doTheAction(gameData, npc);
        } else {
            attackBehavior.act(npc, gameData);
        }
        npc.getPosition().addToEventsHappened(new ParasiteNoiseEvent());
    }

    private class ParasiteNoiseEvent extends SoundInRoomEvent {
        @Override
        public Sound getRealSound() {
            return new Sound("rattle");
        }
    }
}
