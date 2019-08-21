package model.characters.decorators;

import graphics.OverlaySprite;
import graphics.sprites.Sprite;
import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;

import java.util.List;

/**
 * Created by erini02 on 17/11/16.
 */
public class ShowInMapDecorator extends CharacterDecorator {


    public ShowInMapDecorator(GameCharacter character, List<Sprite> result) {
        super(character, "Show stuff in map");
    }

    @Override
    public List<OverlaySprite> getOverlayStrings(Player player, GameData gameData) {
        return super.getOverlayStrings(player, gameData);
    }
}
