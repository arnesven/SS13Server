package model.items.foods;

import model.Actor;

public abstract class JunkHealingFood extends HealingFood {
    public JunkHealingFood(String string, double weight, Actor maker, int cost) {
        super(string, weight, maker, cost);
        setPoisonChance(0.02);
    }

}