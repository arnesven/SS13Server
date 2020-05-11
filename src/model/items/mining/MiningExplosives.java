package model.items.mining;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.decorators.ExplosiveProtection;
import model.characters.general.GameCharacter;
import model.events.Event;
import model.items.CryoBomb;
import model.items.general.BombItem;
import model.items.general.GameItem;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 17/09/17.
 */
public class MiningExplosives extends BombItem {
    private Room position;

    public MiningExplosives() {
        super("Mining Explosives", 2000);
    }

     @Override
	public String getPublicName(Actor whosAsking) {
		return "Mining Explosives";
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        List<Sprite> list = new ArrayList<>();
        list.add(new Sprite("mineingexp1", "equipment.png", 4, 2, this));
        list.add(new Sprite("mineingexp2", "equipment.png", 6, 3, this));

        Sprite total = new Sprite("miningexptotal", "equipment.png", 3, 4, 32, 32, list, this);

        return total;
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        at.add(new SetMiningExplosives());

    }

    private class SetMiningExplosives extends Action {

        public SetMiningExplosives() {
            super("Set Mining Explosives", SensoryLevel.OPERATE_DEVICE);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "set mining explosives";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (GameItem.hasAnItemOfClass(performingClient, MiningExplosives.class)) {
                performingClient.getCharacter().getItems().remove(MiningExplosives.this);
                performingClient.getPosition().addItem(MiningExplosives.this);
                position = performingClient.getPosition();
                final List<Actor> protectedPeople = temporarilyProtectNeighbors(position);
                gameData.addEvent(new Event() {

                    private final int roundSet = gameData.getRound();

                    @Override
                    public void apply(GameData gameData) {
                        if (gameData.getRound() == roundSet + 2) {
                            position.addItem(new CryoBomb(gameData, performingClient));
                            position.addItem(new CryoBomb(gameData, performingClient));
                            MiningExplosives.this.explode(gameData, performingClient);
                            removeTemporaryProtection(protectedPeople);
                        }
                    }

                    @Override
                    public String howYouAppear(Actor performingClient) {
                        return "";
                    }

                    @Override
                    public SensoryLevel getSense() {
                        return SensoryLevel.NO_SENSE;
                    }

                    @Override
                    public boolean shouldBeRemoved(GameData gameData) {
                        return gameData.getRound() > roundSet + 1;
                    }
                });
                performingClient.addTolastTurnInfo("You set the mining explosives. Now get the hell out of here!");
            } else {
                performingClient.addTolastTurnInfo("What, the mining explosives were gone? " + failed(gameData, performingClient));
            }
        }

        private void removeTemporaryProtection(List<Actor> protectedPeople) {
            for (Actor a : protectedPeople) {
                a.removeInstance((GameCharacter gc) -> gc instanceof ExplosiveProtection);
            }
        }

        private List<Actor> temporarilyProtectNeighbors(Room position) {
            List<Actor> lst = new ArrayList<>();
            for (Room r : position.getNeighborList()) {
                for (Actor a : r.getActors()) {
                    lst.add(a);
                    a.setCharacter(new ExplosiveProtection(a.getCharacter()));
                }
            }
            return lst;
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }
}
