package model;

import model.characters.decorators.InstanceChecker;
import model.characters.decorators.TalkingDecorator;
import model.characters.general.GameCharacter;
import model.events.RemoveInstanceLaterEvent;
import util.Logger;

public class Talking {
    public static void decorateWithTalk(GameData gameData, Player player, String whatWasSaid) {
        Logger.log("Added talking for " + player.getCharacter().getBaseName());
        if (player.getCharacter() != null) {
            player.setCharacter(new TalkingDecorator(player.getCharacter(), whatWasSaid.endsWith("?"), whatWasSaid.endsWith("!")));
            player.refreshClientData();
        }
        gameData.addEvent(new RemoveInstanceLaterEvent(player, gameData.getRound(), 0, new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof TalkingDecorator;
            }
        }));
    }

}
