package model.items.suits;

public class BlueRolledDownCoverall extends RolledDownCoverall {
    public BlueRolledDownCoverall() {
        super("Blue");
    }

    @Override
    protected int getSpriteCol() {
        return 4;
    }

    @Override
    protected int getSpriteRow() {
        return 0;
    }

    @Override
    public SuitItem clone() {
        return new BlueRolledDownCoverall();
    }
}
