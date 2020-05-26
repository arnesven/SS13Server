package model.items.suits;

public abstract class FaceCoveringSuitItem extends SuitItem {
    public FaceCoveringSuitItem(String string, double weight, int cost) {
        super(string, weight, cost);
        setCoversFacial(true);
        setCoversHair(true);
    }
}
