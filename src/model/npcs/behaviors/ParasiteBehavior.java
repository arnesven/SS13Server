package model.npcs.behaviors;

import model.GameData;
import model.Target;
import model.actions.characteractions.FaceHuggingAction;
import model.actions.general.Action;
import model.actions.general.TargetingAction;
import model.npcs.NPC;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 30/11/16.
 */
public class ParasiteBehavior implements ActionBehavior {

    private final AttackAllActorsNotSameClassBehavior attackBehavior;

    public ParasiteBehavior() {
        this.attackBehavior = new AttackAllActorsNotSameClassBehavior();
    }

    @Override
    public void act(NPC npc, GameData gameData) {
         TargetingAction act = new FaceHuggingAction(npc);
        

        if (MyRandom.nextDouble() < 0.5 && act.getNoOfTargets() > 0) {
            Target t = MyRandom.sample(act.getTargets());
            List<String> args = new ArrayList<>();
            args.add(t.getName());
            act.setArguments(args, npc);
            act.doTheAction(gameData, npc);
        } else {
            attackBehavior.act(npc, gameData);
        }
    }
}
