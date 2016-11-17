package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.characteractions.NuclearExplosiveDamage;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.general.GameItem;
import model.items.general.NuclearDisc;
import model.map.Room;

import java.util.ArrayList;
import java.util.List;

public class NuclearBomb extends GameObject {

	public NuclearBomb(Room position) {
		super("Nuclear Bomb", position);
	}

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("nuclearbomb", "stationobjs.png", 98);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (GameItem.hasAnItem(cl, new NuclearDisc())) {
            at.add(new SetOffNukeAction(this));
        }
    }

    public void detonate(GameData gameData) {
        for (Actor a : gameData.getActors()) {
            if (gameData.getRooms().contains(a.getPosition())) {
                a.getCharacter().beExposedTo(null, new NuclearExplosiveDamage());
            }
        }
    }

    private class SetOffNukeAction extends Action {
        private final NuclearBomb nuke;

        public SetOffNukeAction(NuclearBomb nuclearBomb) {
            super("Activate Nuke", SensoryLevel.OPERATE_DEVICE);
            this.nuke = nuclearBomb;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "Activated a nuke";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (GameItem.hasAnItem(performingClient, new NuclearDisc())) {
                nuke.detonate(gameData);
            }
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }
}
