package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;

/**
 * Created by erini02 on 11/11/16.
 */
public class PirateNuclearDisc extends GameItem {
    public PirateNuclearDisc() {
        super("Pirates' Nuclear Disc", 0.1, false, 13);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("piratesnucleardisc", "items.png", 53, this);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A data disc. It seems strangely familiar.";
    }

    @Override
    public GameItem clone() {
        return new PirateNuclearDisc();
    }
}
