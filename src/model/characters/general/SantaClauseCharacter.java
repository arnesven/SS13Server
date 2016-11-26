package model.characters.general;

import model.Actor;
import model.items.general.ChristmasGift;
import model.items.general.GameItem;
import util.MyRandom;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by erini02 on 22/11/16.
 */
public class SantaClauseCharacter extends GameCharacter {

    private Set<Actor> receivedGiftSet = new HashSet<>();

    public SantaClauseCharacter(int startRoom) {
        super("Father Christmas", startRoom, 0.5);
    }

    @Override
    public List<GameItem> getStartingItems() {
        List<GameItem> lsit = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            lsit.add(new ChristmasGift(MyRandom.sample(MyRandom.allRandomItems())));
        }

        return lsit;
    }

    @Override
    public GameCharacter clone() {
        return new SantaClauseCharacter(getStartingRoom());
    }

    public boolean alreadyReceivedGift(Actor a) {
        return receivedGiftSet.contains(a);
    }

    public void gotAGift(Actor a) {
        receivedGiftSet.add(a);
    }

    @Override
    public boolean isCrew() {
        return false;
    }
}
