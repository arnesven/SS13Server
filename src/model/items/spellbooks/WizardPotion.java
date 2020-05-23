package model.items.spellbooks;

import model.Actor;
import model.GameData;
import model.Player;
import model.characters.general.WizardCharacter;
import model.items.foods.FoodItem;

public abstract class WizardPotion extends FoodItem {
    private final String color;

    public WizardPotion(String string, int cost, String color) {
        super(string, 0.4, cost);
        this.color = color;
    }

    @Override
    public String getPublicName(Actor whosAsking) {
        if (whosAsking.getInnermostCharacter() instanceof WizardCharacter) {
            return super.getPublicName(whosAsking);
        }
        return "Some Kind of Potion";
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        if (performingClient.getInnermostCharacter() instanceof WizardCharacter) {
            return getPotionDescription();
        }
        return "A thick " + color + " liquid.";
    }

    protected abstract String getPotionDescription();

    @Override
    public double getFireRisk() {
        return 0;
    }

}
