package model.modes.goals;

import model.Actor;
import model.GameData;
import model.actions.DealDrugsAction;
import model.actions.general.Action;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.chemicals.CaseOfDrugs;
import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;

public class DrugDealerGaoal extends DidAnActionGoal {
    public DrugDealerGaoal() {
        super(3, DealDrugsAction.class);
    }

    @Override
    public String getText() {
        return "You're a drug dealer! Sell drugs to at least 3 Crew members (NPCs).";
    }

    @Override
    protected String getNoun() {
        return "";
    }

    @Override
    protected String getVerb() {
        return "";
    }

    @Override
    public void setBelongsTo(Actor belongingTo) {
        super.setBelongsTo(belongingTo);
        belongingTo.setCharacter(new DrugDealerDecorator(belongingTo.getCharacter()));
    }

    private class DrugDealerDecorator extends CharacterDecorator {
        public DrugDealerDecorator(GameCharacter chara) {
            super(chara, "Drug dealer");
        }

        @Override
        public String getFullName() {
            return super.getFullName() + "(Drug Dealer)";
        }
    }
}
