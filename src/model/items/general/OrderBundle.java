package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.items.TraitorItem;

/**
 * Created by erini02 on 01/05/16.
 */
public class OrderBundle extends GameItem implements TraitorItem {
    private int num;
    private GameItem innerItem;

    public OrderBundle(int i, GameItem it) {
        super(i + "x" + it.getBaseName(), i * it.getWeight(), it.getCost() * i);
        this.num = i;
        this.innerItem = it;
    }

    @Override
    public GameItem clone() {
        return new OrderBundle(getNum(), getInnerItem());
    }

    public int getNum() {
        return num;
    }

    public GameItem getInnerItem() {
        return innerItem;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return innerItem.getSprite(whosAsking);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "This bundle contains " + num + " " + innerItem.getBaseName();
    }

    @Override
    public int getTelecrystalCost() {
        return 3;
    }
}
