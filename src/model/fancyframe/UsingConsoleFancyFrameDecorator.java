package model.fancyframe;

import model.GameData;
import model.Player;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.map.rooms.Room;

public class UsingConsoleFancyFrameDecorator extends CharacterDecorator {
    private final Room originalPos;
    private final ConsoleFancyFrame fancyFrame;

    public UsingConsoleFancyFrameDecorator(GameCharacter character, ConsoleFancyFrame ff) {
        super(character, "Using Fancy Frame");
        originalPos = character.getPosition();
        this.fancyFrame = ff;
    }

    @Override
    public void doAfterActions(GameData gameData) {
        super.doAfterActions(gameData);
        if (getActor() instanceof Player) {
            fancyFrame.rebuildInterface(gameData, (Player) getActor());
            fancyFrame.doAtEndOfTurn(gameData, (Player)getActor());
        }
    }

    @Override
    public void doAfterMovement(GameData gameData) {
        super.doAfterActions(gameData);
        System.out.println("Checking position for player using fancy frame");
        if (getPosition() != originalPos && getActor() instanceof Player) {
            System.out.println("Position has changed, making console vacant");
            fancyFrame.leaveFancyFrame(gameData, (Player)getActor());
            getActor().removeInstance((GameCharacter gc) -> gc == this);
        }
    }
}
