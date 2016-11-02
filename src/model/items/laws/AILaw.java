package model.items.laws;

import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;

/**
 * Created by erini02 on 26/10/16.
 */
public class AILaw extends GameItem {
    private final String text;
    private final int number;

    public AILaw(int number, String string) {
        super(string, 0.0, false);
        this.number = number;
        this.text = string;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("law" + text + number, "numbers.png", number);
    }

    @Override
    public GameItem clone() {
        return new AILaw(number, text);
    }

    public int getNumber() {
        return number;
    }
}
