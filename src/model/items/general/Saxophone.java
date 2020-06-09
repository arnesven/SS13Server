package model.items.general;

import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.items.weapons.BluntWeapon;
import model.actions.general.SensoryLevel;
import sounds.Sound;
import util.MyRandom;

public class Saxophone extends BluntWeapon {

	public Saxophone() {
		super("Saxophone", 1.5, 100, 0.65);
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("saxophone", "saxophone.png", 0, this);
    }

    @Override
	public void addYourActions(GameData gameData, ArrayList<Action> at,
                               Actor cl) {
		super.addYourActions(gameData, at, cl);
		at.add(new PlaySaxophoneAction());
	}

    @Override
    public Saxophone clone() {
        return new Saxophone();
    }

    public static class PlaySaxophoneAction extends Action {

        private final int soundInt;

        public PlaySaxophoneAction() {
            super("Play Saxophone", SensoryLevel.PHYSICAL_ACTIVITY);
            soundInt = MyRandom.nextInt(5) + 1;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "Played a cool jazz tune";
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) { }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (GameItem.hasAnItem(performingClient, new Saxophone())) {
                performingClient.addTolastTurnInfo("You play a cool jazz tune.");
            } else {
                performingClient.addTolastTurnInfo("What? The saxophone is gone! Your action failed.");
            }
        }

        @Override
        public boolean hasRealSound() {
            return true;
        }

        @Override
        public Sound getRealSound() {
            return new Sound("sax" + soundInt);
        }
    }
}
