package model.characters.decorators;

import graphics.OverlaySprite;
import graphics.sprites.AlsoSeeSpecificRoomVision;
import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;
import model.map.rooms.Room;

import java.util.List;

/**
 * Created by erini02 on 13/10/16.
 */
public class SeeRoomOverlayDecorator extends CharacterDecorator {

    private final int round;
    private final Room room;

    public SeeRoomOverlayDecorator(GameCharacter chara, GameData gameData, Room room) {
        super(chara, "See into room");
        round = gameData.getRound();
        this.room = room;
    }

    @Override
    public List<OverlaySprite> getOverlayStrings(Player player, GameData gameData) {
        if (round == gameData.getRound() - 1) {
            return new AlsoSeeSpecificRoomVision(room).getOverlaySprites(player, gameData);
        }
        player.removeInstance(new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof SeeRoomOverlayDecorator;
            }
        });
        return super.getOverlayStrings(player, gameData);
    }
}
