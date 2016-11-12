package model.npcs.behaviors;

import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionGroup;
import model.actions.general.ActionOption;
import model.actions.general.AttackAction;
import model.npcs.NPC;
import util.Logger;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 19/10/16.
 */
public class RandomActionBehavior implements ActionBehavior {
    @Override
    public void act(NPC npc, GameData gameData) {


        List<String> args = new ArrayList<>();
        Action selected;
        do {
            args.clear();
            selected = randomAction(args, gameData, npc);
            Logger.log("Random behavior gave action which attacked itself.");
        } while (selected instanceof AttackAction && args.contains(npc.getBaseName()));

        Logger.log("Random behavior selected " + selected.getName() + " with args " + args.toString());
        selected.setArguments(args, npc);
        selected.doTheAction(gameData, npc);
    }

    private Action randomAction(List<String> args, GameData gameData, NPC npc) {

        Player pl = new Player(gameData);
        pl.setCharacter(npc.getCharacter());
        List<Action> availableActions = pl.getActionList(gameData);
        Action selected = MyRandom.sample(availableActions);

        if (selected instanceof ActionGroup) {
            selected = MyRandom.sample(((ActionGroup) selected).getActions());
        }


        getRandomArgsForOptions(args, selected.getOptions(gameData, npc));
        return selected;
    }

    private void getRandomArgsForOptions(List<String> args, ActionOption options) {
        if (options.numberOfSuboptions() == 0) {
            return;
        }
        ActionOption selected = MyRandom.sample(options.getSuboptions());
        args.add(selected.getName());
        getRandomArgsForOptions(args, selected);
    }
}