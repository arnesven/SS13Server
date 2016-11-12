package model.characters.decorators;

import graphics.sprites.OverlaySprites;
import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;

import java.util.List;

/**
 * Created by erini02 on 20/10/16.
 */
public class AnimalOverlayDecorator extends CharacterDecorator {
    public AnimalOverlayDecorator(GameCharacter character) {
        super(character, "Animal Overlay");
    }

    @Override
    public List<String> getOverlayStrings(Player player, GameData gameData) {
        List<String> strs = super.getOverlayStrings(player, gameData);
        strs.addAll(OverlaySprites.seeAnimalsInAdjacentRooms(player, gameData));
        return strs;
    }
}