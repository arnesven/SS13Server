package model.items.chemicals;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.DealDrugsAction;
import model.actions.general.Action;
import model.items.general.GameItem;

import java.util.ArrayList;

public class CaseOfDrugs extends GameItem {
    private int dosesLeft = 40;

    public CaseOfDrugs() {
        super("Case of Drugs", 1.5, false, 20000);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("caseofdrugs", "storage2.png", 6, 3);
    }

    @Override
    public GameItem clone() {
        return new CaseOfDrugs();
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        at.add(new DealDrugsAction(cl));
    }

    public DrugDose extractDose() {
        if (dosesLeft >= 0) {
            dosesLeft--;
            return new DrugDose(getHolder().getActor());
        } else {
            throw new NoDrugsException();
        }
    }

    public class NoDrugsException extends RuntimeException {
    }
}
