package model.characters.decorators;

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
    public List<String> getOverlayStrings(Player player, GameData gameData) {
        List<String> list =  super.getOverlayStrings(player, gameData);
        list.addAll(OverlaySprites.seeActorsInAdjacentRooms(player, player.getPosition()));
        return list;
    }
}
