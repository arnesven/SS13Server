package model.characters.decorators;

import graphics.sprites.Sprite;
import model.Actor;
import model.characters.general.GameCharacter;

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
        sps.add(sp);
        Sprite tele = new Sprite("teleporting" + sp.getName(), "effects.png", 4, 15, getActor());
        sps.add(tele);
        Sprite res = new Sprite("tele" + tele.getName(), "blank.png", 0, sps, getActor());
        return res;
    }
}
