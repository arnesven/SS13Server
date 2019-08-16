package model.items.chemicals;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.DealDrugsAction;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;

public class CaseOfDrugs extends GameItem {
    private int dosesLeft = 40;

    public CaseOfDrugs() {
        super("Case of Drugs", 1.5, false, 20000);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("caseofdrugs", "storage2.png", 6, 3, this);
    }

    @Override
    public String getFullName(Actor whosAsking) {
        return super.getFullName(whosAsking) + ((dosesLeft==0)?" (empty)":"");
    }

    @Override
    public GameItem clone() {
        return new CaseOfDrugs();
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        at.add(new DealDrugsAction(cl));
        at.add(new ExtractDose(this));
    }

    public DrugDose extractDose() throws NoDrugsException {
        if (dosesLeft >= 0) {
            dosesLeft--;
            return new DrugDose(getHolder().getActor());
        } else {
            throw new NoDrugsException();
        }
    }

    public class NoDrugsException extends Exception {
    }

    private class ExtractDose extends Action {

        private final CaseOfDrugs drugCase;

        public ExtractDose(CaseOfDrugs caseOfDrugs) {
            super("Extract drugs", SensoryLevel.NO_SENSE);
            this.drugCase = caseOfDrugs;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "extracted drugs from case";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (performingClient.getItems().contains(drugCase)) {
                try {
                    performingClient.getCharacter().giveItem(drugCase.extractDose(), performingClient.getAsTarget());
                } catch (NoDrugsException e) {
                    performingClient.addTolastTurnInfo("The drug case was empty!");
                }
            } else {
                performingClient.addTolastTurnInfo("What, the case is gone? " + Action.FAILED_STRING);
            }
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }
}
