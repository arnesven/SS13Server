package model.items.suits;

public class TanRolledDownCoverall extends RolledDownCoverall {
    public TanRolledDownCoverall() {
        super("Tan");
    }

    @Override
    protected int getSpriteCol() {
        return 0;
    }

    @Override
    protected int getSpriteRow() {
        return 4;
    }

    @Override
    public SuitItem clone() {
        return new TanRolledDownCoverall();
    }
}
