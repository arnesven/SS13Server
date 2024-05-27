package model.items.weapons;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.general.GameItem;
import model.map.rooms.Room;
import sounds.Sound;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 17/12/16.
 */
public class ImpalerWeapon extends SlashingWeapon {
    public ImpalerWeapon() {
        super("Impaler", 0.95, 1.0, false, 0.0, true, 0);
    }

    @Override
    protected void usedOnBy(Target target, Actor performingClient, GameData gameData) {
        super.usedOnBy(target, performingClient, gameData);
        if (target instanceof Actor) {
            target.getPosition().addToActionsHappened(new ScreamAction());
        }
    }

    @Override
    public GameItem clone() {
        return new ImpalerWeapon();
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "";
    }

    private class ScreamAction extends Action {
        public ScreamAction() {
            super("Scream", new SensoryLevel(SensoryLevel.VisualLevel.INVISIBLE,
                    SensoryLevel.AudioLevel.VERY_LOUD,
                    SensoryLevel.OlfactoryLevel.UNSMELLABLE));
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "screamed terrifyingly";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {

        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }

        @Override
        public String getDescription(Actor whosAsking) {
            return "You hear a terrifying scream!";
        }

        @Override
        public boolean hasRealSound() {
            return true;
        }

        @Override
        public Sound getRealSound() {
            return new Sound("scary-scream");
        }
    }
}
