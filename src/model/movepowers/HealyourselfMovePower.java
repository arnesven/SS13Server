package model.movepowers;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.MovementData;
import model.actions.itemactions.ConsumeAction;
import model.actions.itemactions.HealWithMedKitAction;
import model.items.foods.FoodItem;
import model.items.foods.HealingFood;
import model.items.general.GameItem;
import model.items.general.MedKit;

import java.util.ArrayList;
import java.util.List;

public class HealyourselfMovePower extends MovePower {
    public HealyourselfMovePower() {
        super("Heal Yourself");
    }

    @Override
    public void activate(GameData gameData, Actor performingClient, MovementData moveData) {
        for (GameItem it : performingClient.getItems()) {
            if (it instanceof MedKit) {
                HealWithMedKitAction hwm = new HealWithMedKitAction(performingClient, (MedKit)it);
                List<String> args = new ArrayList<>();
                args.add("Yourself");
                hwm.setArguments(args, performingClient);
                hwm.doTheAction(gameData, performingClient);
                break;
            } else if (it instanceof HealingFood) {
                ConsumeAction ca = new ConsumeAction((FoodItem)it, performingClient);
                ca.doTheAction(gameData, performingClient);
                break;
            }
        }
    }

    @Override
    public boolean isApplicable(GameData gameData, Actor performingClient) {
        for (GameItem it : performingClient.getItems()) {
            if (it instanceof MedKit || it instanceof HealingFood) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Sprite getButtonSprite() {
        return new Sprite("healyourselfbutton", "buttons1.png", 4, 1);
    }
}
