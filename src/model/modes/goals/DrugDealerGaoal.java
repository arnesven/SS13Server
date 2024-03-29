package model.modes.goals;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.DealDrugsAction;
import model.actions.characteractions.AlsoDealDrugsAction;
import model.actions.general.Action;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.chemicals.CaseOfDrugs;
import model.items.chemicals.DrugDose;
import util.MyRandom;

import java.util.*;

public class DrugDealerGaoal extends PersonalGoal {

    private Set<Actor> dealtTo = new HashSet<>();

    @Override
    public String getText() {
        return "You're a drug dealer! Sell drugs to at least 3 Crew members (NPCs).";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return dealtTo.size() >= 3;
    }

    @Override
    public void setBelongsTo(Actor belongingTo) {
        super.setBelongsTo(belongingTo);
        belongingTo.setCharacter(new DrugDealerDecorator(belongingTo.getCharacter()));
//        for (int i = 0; i < MyRandom.nextInt(6); ++i) {
//            belongingTo.getCharacter().giveItem(new DrugDose(belongingTo), belongingTo.getAsTarget());
//        }
        belongingTo.getCharacter().giveItem(new CaseOfDrugs(), belongingTo.getAsTarget());
    }

    private class DrugDealerDecorator extends CharacterDecorator {
        public DrugDealerDecorator(GameCharacter chara) {
            super(chara, "Drug dealer");
        }

        @Override
        public String getFullName() {
            return super.getFullName() + "(Drug Dealer)";
        }

        @Override
        public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
            super.addCharacterSpecificActions(gameData, at);
            Action a = new AlsoDealDrugsAction(getActor());
            if (a.getOptions(gameData, getActor()).numberOfSuboptions() > 0) {
                at.add(a);
            }
        }

        @Override
        public void doAfterActions(GameData gameData) {
            super.doAfterActions(gameData);
            if (getActor() instanceof Player) {
                Player p = (Player)getActor();
                Action a = p.getNextAction();
                if (a instanceof AlsoDealDrugsAction) {
                    dealtTo.add((Actor)((AlsoDealDrugsAction) a).getTarget());
                }
            }
        }
    }
}
