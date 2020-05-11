package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.objects.consoles.AIConsole;

import java.util.ArrayList;
import java.util.List;

public class Pizza extends HealingFood {
    public Pizza(Actor maker) {
        super("Pizza", 0.6, maker, 25);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("fullpizza", "food.png", 1, 14, this);
    }

    @Override
    public double getFireRisk() {
        return 0.2;
    }

    @Override
    public FoodItem clone() {
        return new Pizza(getMaker());
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        at.add(new SplitPizzaAction(this));
    }

    @Override
    protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
        for (int i = 4; i > 0; --i) {
            super.triggerSpecificReaction(eatenBy, gameData);
        }
    }

    private class SplitPizzaAction extends Action {
        private final Pizza pizza;

        public SplitPizzaAction(Pizza piz) {
            super("Cut into Slices", SensoryLevel.OPERATE_DEVICE);
            this.pizza = piz;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "cut the pizza into slices";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (performingClient.getItems().contains(pizza)) {
                performingClient.getItems().remove(pizza);
                for (int i = 6; i > 0; --i) {
                    performingClient.addItem(new SliceOfPizza(performingClient), performingClient.getAsTarget());
                }
                performingClient.addTolastTurnInfo("You cut the pizza into six slices!");
            } else {
                performingClient.addTolastTurnInfo("What, the pizza wasn't there? " + failed(gameData, performingClient));
            }
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }
}
