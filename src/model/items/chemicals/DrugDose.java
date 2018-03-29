package model.items.chemicals;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.crew.CrewCharacter;
import model.characters.decorators.AlterMovement;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.PoisonedDecorator;
import model.characters.general.GameCharacter;
import model.characters.general.PirateCharacter;
import model.items.foods.FoodItem;
import model.items.general.Chemicals;
import model.items.general.GameItem;
import model.npcs.NPC;
import model.npcs.behaviors.CrazyBehavior;
import model.npcs.behaviors.MeanderingMovement;
import util.MyRandom;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

        for (Actor a : gameData.getActors()) {
            if (a != eatenBy) {
                if (a.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof CrewCharacter)) {
                    a.setCharacter(new HallucinationDecorator(a.getCharacter(), eatenBy));
                }
            }
        }


    }


    private class HallucinationDecorator extends CharacterDecorator {
        private final Actor hallucinator;
        private final Sprite mask;

        public HallucinationDecorator(GameCharacter character, Actor hallucinator) {
            super(character, "hallucination");
            this.hallucinator = hallucinator;
            this.mask = randomMask();
        }

        @Override
        public Sprite getSprite(Actor whosAsking) {
            if (whosAsking == hallucinator) {
                List<Sprite> sp = new ArrayList<>();
                Sprite base = super.getSprite(whosAsking);
                sp.add(base);
                sp.add(mask);
                Sprite sprt = new Sprite("hallucinatorhorse"+getFullName()+base.getName(), "human.png", 0, sp);
                return sprt;
            } else {
                return super.getSprite(whosAsking);
            }
        }


        private Sprite randomMask() {
            int i = MyRandom.nextInt(3);
            if (i == 0) {
                return new Sprite("horsemask", "mask.png", 8, 8);
            } else if (i == 1) {
                return new Sprite("wackymask", "mask.png", 13, 17);
            } else {
                return new Sprite("clownmask", "mask.png", 2, 2);
            }
        }
    }
}
