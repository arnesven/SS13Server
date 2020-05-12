package model.items.general;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.itemactions.PoisonFoodAction;
import model.characters.decorators.CharacterDecorator;
import model.mutations.PoisonMutation;

import java.util.ArrayList;

/**
 * Created by erini02 on 08/09/17.
 */
public class PoisonSyringe extends Syringe {

    private boolean outOfPoison = false;

    public PoisonSyringe() {
        setName("Poison Syringe");
        setFilled(true);
        setMutation(new PoisonMutation());
    }

    @Override
    public void empty() {
        super.empty();

        setOutOfPoison(true);
        outOfPoison = true;
    }

    @Override
    public String getPublicName(Actor whosAsking) {
        return "Syringe";
    }

    public void setOutOfPoison(boolean outOfPoison) {
        this.outOfPoison = outOfPoison;
        if (outOfPoison) {
            this.setName("Syringe");
        }
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        if (isFilled() && !outOfPoison) {
            PoisonFoodAction pfa = new PoisonFoodAction();
            if (pfa.getOptions(gameData, cl).numberOfSuboptions() > 0) {
                at.add(pfa);
            }
        }
    }

    @Override
    public CharacterDecorator getMutationDecorator(Actor forWhom, Actor maker) {
        return new PoisonMutation(maker).getDecorator(forWhom);
    }

    @Override
    public GameItem clone() {
        return new PoisonSyringe();
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "Good old fashion poison for a good old fashioned murder.";
    }
}
