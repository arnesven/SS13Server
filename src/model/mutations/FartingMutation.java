package model.mutations;

import model.Actor;
import model.GameData;
import model.actions.general.ActionGroup;
import model.actions.general.FartWrapperAction;
import model.actions.general.Action;
import model.characters.decorators.CharacterDecorator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 03/09/16.
 */
public class FartingMutation extends Mutation {
    public FartingMutation() {
        super("Farting");
    }

    @Override
    public CharacterDecorator getDecorator(Actor forWhom) {
        return new CharacterDecorator(forWhom.getCharacter(), "Farting") {

            @Override
            public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
                super.addCharacterSpecificActions(gameData, at);

                ArrayList<Action> fartWrappedActions = new ArrayList<>();

                for (Action a : at) {
                    recursivelyAddFartWrappers(a, gameData, fartWrappedActions, forWhom);

                }
                at.clear();
                at.addAll(fartWrappedActions);

            }

            private void recursivelyAddFartWrappers(Action a, GameData gameData, List<Action> fartWrappedActions, Actor forWhom) {
                if (a instanceof ActionGroup) {
                    ActionGroup ag = new ActionGroup(a.getName());
                    List<Action> acts = new ArrayList<>();
                    for (Action a2 : ((ActionGroup) a).getActions()) {
                        recursivelyAddFartWrappers(a2, gameData, acts, forWhom);
                    }
                    ag.addAll(acts);
                    fartWrappedActions.add(ag);
                } else {
                    fartWrappedActions.add(new FartWrapperAction(a));
                }
            }
        };
    }
}
