package model.items.general;

import java.util.Set;

/**
 * Created by erini02 on 01/05/16.
 */
public class ItemStack extends GameItem {
    private int num;
    private GameItem innerItem;

    public ItemStack(int i, GameItem it) {
        super(i + "x" + it.getBaseName(), i * it.getWeight());
        this.num = i;
        this.innerItem = it;
    }

    @Override
    public GameItem clone() {
        return new ItemStack(getNum(), getInnerItem());
    }

    public int getNum() {
        return num;
    }

    public GameItem getInnerItem() {
        return innerItem;
    }
}
