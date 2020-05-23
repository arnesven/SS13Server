package model.items.spellbooks;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.general.WizardCharacter;
import model.items.foods.FoodItem;

public class MagickaPotion extends WizardPotion {
    public MagickaPotion() {
        super("Magicka Potion", 495, "blue");
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("magickapotion", "weapons2.png", 29, 24, this);
    }


    @Override
    public FoodItem clone() {
        return new MagickaPotion();
    }

    @Override
    protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
        if (eatenBy.getInnermostCharacter() instanceof WizardCharacter) {
            WizardCharacter wizChar = (WizardCharacter)eatenBy.getInnermostCharacter();
            wizChar.setMagicka(wizChar.getMagicka() + 45);
            eatenBy.addTolastTurnInfo("You feel magic running through your veins!");
        }
    }


    @Override
    protected String getPotionDescription() {
        return "A potion which restores up to 45 magicka.";
    }
}
