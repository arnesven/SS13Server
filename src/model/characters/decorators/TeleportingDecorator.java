package model.characters.decorators;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.GameCharacter;
import model.events.animation.AnimatedSprite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 15/09/17.
 */
public class TeleportingDecorator extends AlterMovement {
    public TeleportingDecorator(GameCharacter character) {
        super(character, "teleport", true, 0);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        Sprite sp = super.getSprite(whosAsking);
        List<Sprite> sps = new ArrayList<>();
        sps.add(new Sprite("teleblank", "blank.png", 0, null));
        sps.add(sp);
        Sprite tele = new AnimatedSprite("teleporting" + sp.getName(), "effects.png", 4, 15, 32, 32, getActor(), 10, true);
        sps.add(tele);
        Sprite res = new AnimatedSprite("tele" + tele.getName(), sps, 10, true);
        return res;
    }

    @Override
    public boolean getsActions() {
        return false;
    }
}
