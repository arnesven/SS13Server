package model.characters.decorators;

import graphics.OverlaySprite;
import graphics.sprites.OverlaySprites;
import model.GameData;
import model.Player;
import model.characters.general.GameCharacter;

import java.util.List;

/**
 * Created by erini02 on 13/10/16.
 */
//public class AlarmOverlayDecorator extends CharacterDecorator {
//
//    private final int round;
//
//    public AlarmOverlayDecorator(GameCharacter chara, GameData gameData) {
//        super(chara, "Alarm Overlay");
//        round = gameData.getRound();
//    }
//
//    @Override
//    public List<OverlaySprite> getOverlayStrings(Player player, GameData gameData) {
//        if (round == gameData.getRound()-1) {
//            return OverlaySprites.seeAlarms(player, gameData);
//        }
//        player.removeInstance(new InstanceChecker() {
//            @Override
//            public boolean checkInstanceOf(GameCharacter ch) {
//                return ch instanceof AlarmOverlayDecorator;
//            }
//        });
//        return super.getOverlayStrings(player, gameData);
//    }
//}
