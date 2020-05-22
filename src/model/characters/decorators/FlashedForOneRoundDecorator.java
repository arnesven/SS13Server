package model.characters.decorators;

import graphics.OverlaySprite;
import graphics.sprites.FlashedVision;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.characters.general.GameCharacter;

import java.util.List;

public class FlashedForOneRoundDecorator extends StunnedDecorator {
    private final int roundSet;

    public FlashedForOneRoundDecorator(GameCharacter gc, int round) {
        super(gc);
        this.roundSet = round;
    }

    @Override
    public boolean doesPerceive(Action a) {
        return false;
    }

    @Override
    public List<OverlaySprite> getOverlayStrings(Player player, GameData gameData) {
        return new FlashedVision().getOverlaySprites((Player)getActor(), gameData);
    }

    @Override
    public void doAfterActions(GameData gameData) {
        super.doAfterActions(gameData);
        if (gameData.getRound() > roundSet) {
            getActor().removeInstance((GameCharacter gc) -> gc == this);
        }
    }

}
