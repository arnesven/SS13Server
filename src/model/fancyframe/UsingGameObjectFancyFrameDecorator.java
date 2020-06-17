package model.fancyframe;

import graphics.sprites.SpriteObject;
import model.GameData;
import model.Player;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.map.doors.Door;
import model.map.doors.ElectricalDoor;
import model.map.rooms.RelativePositions;
import model.map.rooms.Room;
import model.objects.general.GameObject;

import java.awt.geom.Point2D;

public class UsingGameObjectFancyFrameDecorator extends CharacterDecorator {
    private final Room originalPos;
    private final FancyFrame fancyFrame;
    private final SpriteObject usingObject;

    public UsingGameObjectFancyFrameDecorator(GameCharacter character, FancyFrame ff, SpriteObject whatObj) {
        super(character, "Using Fancy Frame");
        originalPos = character.getPosition();
        this.fancyFrame = ff;
        this.usingObject = whatObj;
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
            System.out.println("Position has changed, making object vacant");
            fancyFrame.leaveFancyFrame(gameData, (Player)getActor());
            getActor().removeInstance((GameCharacter gc) -> gc == this);
        }
    }

    @Override
    public RelativePositions getPreferredRelativePosition() {
        if (usingObject instanceof Door) {
            Door d = (Door)usingObject;
            return new RelativePositions.CloseToDoor(d);
        }
        return new RelativePositions.InProximityOf(usingObject);
    }
}
