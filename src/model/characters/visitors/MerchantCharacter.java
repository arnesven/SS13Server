package model.characters.visitors;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.GameCharacter;
import model.characters.general.HumanCharacter;
import model.items.general.GameItem;
import model.items.general.MoneyStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 15/11/16.
 */
public class MerchantCharacter extends HumanCharacter {
    private final int startRoom;

    public MerchantCharacter(int startRoom) {
        super("Merchant", startRoom, -3.26);
        this.startRoom = startRoom;
    }

    @Override
    public List<GameItem> getStartingItems() {
        List<GameItem> things = new ArrayList<>();
        things.add(new MoneyStack(50));
        return things;
    }

    @Override
    public GameCharacter clone() {
        return new MerchantCharacter(startRoom);
    }


}
