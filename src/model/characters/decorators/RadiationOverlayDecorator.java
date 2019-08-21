package model.characters.decorators;

import graphics.OverlaySprite;
import graphics.sprites.OverlaySprites;
import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;

import java.util.List;

/**
 * Created by erini02 on 19/10/16.
 */
public class RadiationOverlayDecorator extends CharacterDecorator {

    public RadiationOverlayDecorator(GameCharacter chara) {
        super(chara, "Radiation Overlay");
    }

    @Override
    public List<OverlaySprite> getOverlayStrings(Player player, GameData gameData) {
        List<OverlaySprite> strs = super.getOverlayStrings(player, gameData);
        strs.addAll(OverlaySprites.seeRadiationAndPortalsInRoomAndAdjacent(gameData, player));
        return strs;
    }
}
