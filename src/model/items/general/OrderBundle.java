package model.items.general;

/**
 * Created by erini02 on 01/05/16.
 */
public class OrderBundle extends GameItem {
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
}
