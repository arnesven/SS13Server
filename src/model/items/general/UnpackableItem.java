package model.items.general;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.itemactions.UnpackItemAction;

import java.util.ArrayList;
import java.util.List;

public abstract class UnpackableItem extends GameItem {

    private List<GameItem> innerItem;


    public UnpackableItem(String string, double weight, boolean usableFromFloor, int cost) {
        super(string, weight, usableFromFloor, cost);
        innerItem = new ArrayList<>();
    }

    protected void addInnerItem(GameItem sample) {
        innerItem.add(sample);
    }

    public List<GameItem> getInners() {
        return innerItem;
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        at.add(getUnpackAction(gameData, at, cl));
    }

    protected abstract UnpackItemAction getUnpackAction(GameData gameData, ArrayList<Action> at, Actor cl);

    @Override
    public GameItem getTrueItem() {
        if (innerItem.isEmpty()) {
            return super.getTrueItem();
        }
        return innerItem.get(0);
    }


    protected double getTotalWeightOfInner() {
        double sum = 0.0;
        for (GameItem it : getInners()) {
            sum += it.getWeight();
        }
        return sum;
    }
}
