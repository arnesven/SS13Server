package model.items;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.events.Event;
import model.items.general.BombItem;
import util.MyRandom;

/**
 * Created by erini02 on 02/05/16.
 */
public class CryoBomb extends BombItem {
    private static final double DETONATION_BY_FIRE_CHANCE = 0.33;
    private Actor maker;

    public CryoBomb(GameData gameData, Actor maker) {
        super("Cryo Bomb", 50);
        this.maker = maker;

        gameData.addEvent(new Event() {

            private CryoBomb bomb = CryoBomb.this;

            @Override
            public void apply(GameData gameData) {
                if (bomb.getPosition() != null && bomb.getPosition().hasFire()) {
                    if (MyRandom.nextDouble() < DETONATION_BY_FIRE_CHANCE) {
                        bomb.explode(gameData, bomb.getMaker());
                    }
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
                return bomb.isExploded();
            }
        });
    }

    public Actor getMaker() {
        return maker;
    }
}
