package model.items.suits;

public class GreenRolledDownCoverall extends RolledDownCoverall {


    public GreenRolledDownCoverall() {
        super("Green");
    }

    @Override
    protected int getSpriteCol() {
        return 9;
    }

    @Override
    protected int getSpriteRow() {
        return 3;
    }


    @Override
    public SuitItem clone() {
        return new GreenRolledDownCoverall();
    }
}
