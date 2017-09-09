package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.decorators.AlterMovement;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.NameAddDecorator;
import model.characters.decorators.NameChangeDecorator;
import model.characters.general.GameCharacter;

/**
 * Created by erini02 on 09/09/17.
 */
public class Rapido extends SuitItem {
    private CharacterDecorator cd;

    public Rapido() {
        super("Rapido", 65.0, 695);
    }

    @Override
    public SuitItem clone() {
        return new Rapido();
    }

    @Override
    public void beingPutOn(Actor actionPerformer) {
        actionPerformer.setCharacter(new AlterMovement(actionPerformer.getCharacter(),
				"Steak Diane", false, 3));
        cd = new NameChangeDecorator(actionPerformer.getCharacter(), actionPerformer.getPublicName() + " on Rapido");
        actionPerformer.setCharacter(cd);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("rapido", "objects2.png", 8, 10);
    }

    @Override
    protected Sprite getWornSprite(Actor whosAsking) {
        return new Sprite("rapido", "objects2.png", 8, 10);
    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {
        if (actionPerformer.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof AlterMovement)) {
            actionPerformer.removeInstance((GameCharacter gc) -> gc instanceof AlterMovement);
        }
        if (actionPerformer.getCharacter().checkInstance((GameCharacter gc) -> gc == cd)) {
            actionPerformer.removeInstance((GameCharacter gc) -> gc == cd);
        }
    }

    @Override
    public boolean permitsOver() {
        return false;
    }

    @Override
    public boolean canBePickedUp() {
        return false;
    }
}
