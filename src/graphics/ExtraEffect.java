package graphics;

import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.Actor;
import model.GameData;
import model.Player;
import model.map.doors.DoorMechanism;

import java.io.Serializable;

public class ExtraEffect implements Serializable {
    private final SpriteObject from;
    private final SpriteObject to;
    private final Sprite spriteToUse;
    private final int totalFrames;
    private final boolean looping;

    public ExtraEffect(SpriteObject from, SpriteObject to, Sprite spriteToUse, int totalFrames, boolean looping) {
        this.from = from;
        this.to = to;
        this.spriteToUse = spriteToUse;
        for (int i = 0; i < totalFrames; ++i) {
            Sprite sp = new Sprite(spriteToUse.getName() + "frame"+i, spriteToUse.getMap(),
                    spriteToUse.getColumn()+i, spriteToUse.getRow(), null);
            if (spriteToUse.getColor() != null) {
                sp.setColor(spriteToUse.getColor());
            }
        }
        this.totalFrames = totalFrames;
        this.looping = looping;

    }


    public String getStringRepresentation(Actor forWhom) {
        String delim = "<eepart>";
        return spriteToUse.getName() + delim +
                from.getEffectIdentifier(forWhom) + delim +
                to.getEffectIdentifier(forWhom) + delim +
                spriteToUse.getWidth() + delim +
                spriteToUse.getHeight() + delim +
                totalFrames + delim +
                looping;
    }

    private static void makeExtraEffect(Player seenBy, Actor performingClient, SpriteObject target, Sprite beamSprite, int i, boolean b) {
        if (target instanceof DoorMechanism) {
            DoorMechanism mech = (DoorMechanism)target;
            target = mech.getDoor();

        }

        seenBy.addExtraEffect(new ExtraEffect(performingClient,
                    target, beamSprite, 8, false));

    }


    public static void makeExtraEffectForAllInRoom(Actor performingClient, SpriteObject target, Sprite beamSprite, int i, boolean b) {
        for (Player p : performingClient.getPosition().getClients()) {
            makeExtraEffect(p, performingClient, target, beamSprite, i, b);
        }
    }


}
