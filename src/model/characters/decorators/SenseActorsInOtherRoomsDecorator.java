package model.characters.decorators;

import graphics.OverlaySprite;
import graphics.sprites.AlsoSeeActorsInAdjacentRooms;
import graphics.sprites.OverlaySprites;
import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;

import java.util.List;

/**
 * Created by erini02 on 15/10/16.
 */
public class SenseActorsInOtherRoomsDecorator extends CharacterDecorator {

    public SenseActorsInOtherRoomsDecorator(GameCharacter chara, GameData gameData) {
        super(chara, "Sense Actors");
    }

    @Override
    public List<OverlaySprite> getOverlayStrings(Player player, GameData gameData) {
        return new AlsoSeeActorsInAdjacentRooms().getOverlaySprites(player, gameData);
    }
}
