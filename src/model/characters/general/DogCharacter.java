package model.characters.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.characteractions.BarkingAction;
import model.actions.general.Action;
import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 03/09/16.
 */
public class DogCharacter extends GameCharacter {
    public DogCharacter() {
        super("German Shepherd", 0, 17.5);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("dog", "animal.png", 15, 22, 32, 32);
    }

    @Override
    public List<GameItem> getStartingItems() {
        return new ArrayList<>();
    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        at.add(new BarkingAction());
    }

    @Override
    public GameCharacter clone() {
        return new DogCharacter();
    }
}
