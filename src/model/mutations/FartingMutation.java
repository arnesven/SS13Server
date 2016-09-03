package model.mutations;

import model.Actor;
import model.GameData;
import model.actions.general.FartWrapperAction;
import model.actions.general.Action;
import model.characters.decorators.CharacterDecorator;

import java.util.ArrayList;

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
                    fartWrappedActions.add(new FartWrapperAction(a));
                }
                at.clear();
                at.addAll(fartWrappedActions);

            }
        };
    }
}
