package model;

import graphics.sprites.Sprite;
import model.characters.decorators.CharacterDecorator;
import model.characters.decorators.InstanceChecker;
import model.characters.general.GameCharacter;
import model.events.RemoveInstanceLaterEvent;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

public class Talking {
    public static void decorateWithTalk(GameData gameData, Player player, String whatWasSaid) {
        Logger.log("Added talking for " + player.getCharacter().getBaseName());
        if (player.getCharacter() != null) {
            player.setCharacter(new TalkingDecorator(player.getCharacter(), whatWasSaid.endsWith("?"), whatWasSaid.endsWith("!")));
        }
        gameData.addEvent(new RemoveInstanceLaterEvent(player, gameData.getRound(), 1, new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof TalkingDecorator;
            }
        }));
    }

    private static class TalkingDecorator extends CharacterDecorator {
        private final boolean wasQuestion;
        private final boolean wasScreaming;

        public TalkingDecorator(GameCharacter character, boolean wasQuestion, boolean wasScreaming) {
            super( character, "Talking");
            this.wasQuestion = wasQuestion;
            this.wasScreaming = wasScreaming;
        }

        @Override
        public Sprite getSprite(Actor whosAsking) {
                List<Sprite> look = new ArrayList<>();
                look.add(super.getSprite(whosAsking));
                String suffix = "";
                if (wasQuestion) {
                    look.add(new Sprite("talkingdecoratorquestion", "talk.png", 0, 1, getActor()));
                    suffix = "q";
                } else if (wasScreaming) {
                    look.add(new Sprite("talkingdecoratorscreaming", "talk.png", 1, 1, getActor()));
                    suffix = "s";
                } else {
                    look.add(new Sprite("talkingdecorator", "talk.png", 0, 0, getActor()));
                }
                return new Sprite(super.getSprite(whosAsking).getName() + "talking" + suffix,
                        "human.png", 0, look, whosAsking);
           
        }

    }
}
