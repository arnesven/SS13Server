package model.characters.visitors;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.decorators.TraitorCharacter;
import model.characters.general.ChangelingCharacter;
import model.characters.general.GameCharacter;
import model.characters.general.HumanCharacter;
import model.characters.general.OperativeCharacter;
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

    @Override
    public void setKiller(Actor a) {
        super.setKiller(a);
    }

    private boolean isOutlaw(Actor a) {
        return a.getCharacter().checkInstance((GameCharacter ch) -> ch instanceof OperativeCharacter) ||
                a.getCharacter().checkInstance((GameCharacter ch) -> ch instanceof TraitorCharacter) ||
                a.isInfected() ||
                a.getCharacter().checkInstance((GameCharacter ch) -> ch instanceof ChangelingCharacter);
    }
}
