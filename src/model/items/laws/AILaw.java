package model.items.laws;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 26/10/16.
 */
public class AILaw extends GameItem {
    private final String text;
    private final int number;

    public AILaw(int number, String string) {
        super(string, 0.0, false, 0);
        this.number = number;
        this.text = string;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        List<Sprite> sprs = new ArrayList<>();
        sprs.add(new Sprite("law" + text + number, "numbers.png", number, this));
        sprs.add(new Sprite("lawtext", "numbers.png", 0, 2, this));
        return new Sprite("lawnumberandtext" + number, "human.png", 0, sprs, this);
    }

    @Override
    public List<Action> getInventoryActions(GameData gameData, Actor forWhom) {
        return new ArrayList<>();
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "";
    }

    @Override
    public GameItem clone() {
        return new AILaw(number, text);
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean isRecyclable() {
        return false;
    }
}
