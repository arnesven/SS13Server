package model.items.foods;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.characters.decorators.PoisonedDecorator;
import model.items.general.GameItem;

import java.awt.*;

/**
 * Created by erini02 on 08/09/17.
 */
public class PoisonedConsumable extends FoodItem {
    private final FoodItem innerItem;
    private final Actor maker;
    private final Sprite poisonSprite;

    public PoisonedConsumable(FoodItem selectedItem, Actor maker) {
        super(selectedItem.getBaseName(), selectedItem.getWeight(), selectedItem.getCost());
        innerItem = selectedItem;
        this.maker = maker;
        poisonSprite = new Sprite(innerItem.getSprite(maker), "poisoned");
        poisonSprite.setColor(Color.GREEN);

    }

    @Override
    public double getFireRisk() {
        return 0;
    }

    @Override
    public String getFullName(Actor whosAsking) {
        return "Poisoned " + super.getFullName(whosAsking);
    }

    @Override
    public FoodItem clone() {
        return new PoisonedConsumable(innerItem, maker);
    }

    @Override
    protected void triggerSpecificReaction(Actor eatenBy, GameData gameData) {
        innerItem.triggerSpecificReaction(eatenBy, gameData);
        eatenBy.setCharacter(new PoisonedDecorator(eatenBy.getCharacter(), maker));
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (whosAsking == maker) {
            return poisonSprite;
        }

        return innerItem.getSprite(whosAsking);
    }
}
