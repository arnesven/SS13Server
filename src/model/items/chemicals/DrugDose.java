package model.items.chemicals;

import model.Actor;
import model.GameData;
import model.characters.decorators.AlterMovement;
import model.characters.decorators.PoisonedDecorator;
import model.items.foods.FoodItem;
import model.items.general.Chemicals;
import model.npcs.NPC;
import model.npcs.behaviors.CrazyBehavior;
import model.npcs.behaviors.MeanderingMovement;
import util.MyRandom;

public class DrugDose extends Chemicals {

    private static int type = MyRandom.nextInt(6);
    private final Actor madeBy;

    public DrugDose(Actor madeBy) {
        super("Drug Dose", type);
        this.madeBy = madeBy;
    }

    @Override
    public boolean isFlammable() {
        return false;
    }

    @Override
    public boolean isToxic() {
        return false;
    }

    @Override
    public String getFormula() {
        return "DRUGS";
    }

    @Override
    public FoodItem clone() {
        return new DrugDose(madeBy);
    }

    @Override
    protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
        super.triggerSpecificReaction(eatenBy, gameData);
        if (type > 3) {
            if (eatenBy instanceof NPC) {
                ((NPC) eatenBy).setActionBehavior(new CrazyBehavior());
                ((NPC) eatenBy).setMoveBehavior(new MeanderingMovement(0.8));
            }
        } else {
            eatenBy.setCharacter(new AlterMovement(eatenBy.getCharacter(), "Drugs", true, 3));
            eatenBy.setCharacter(new PoisonedDecorator(eatenBy.getCharacter(), madeBy));
        }
    }
}
