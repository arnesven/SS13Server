package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.map.rooms.Room;
import util.HTMLText;

public class PolaroidPhoto extends GameItem {
    private static int uid = 1;
    private final Sprite photoSprite;
    private final Player whoTookIt;
    private final Room whereItWasTaken;

    public PolaroidPhoto(Sprite combinedSprite, Player whoTookIt, Room whereItWasTaken) {
        super("Polaroid Photo #" + (uid++), 0.001, false, 0);
        this.photoSprite = combinedSprite;
        this.whoTookIt = whoTookIt;
        this.whereItWasTaken = whereItWasTaken;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("poloraoidphoto", "custom_items.png", 20, 6, this);
    }

    @Override
    public GameItem clone() {
        return new PolaroidPhoto(photoSprite, whoTookIt, whereItWasTaken);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A photo taken by " + whoTookIt.getBaseName() + " of the " + whereItWasTaken.getName() + ".<br/>" +
                "<table width=\"100%\" bgcolor=\"black\"><tr><td>" +
                HTMLText.makeCentered(HTMLText.makeImage(photoSprite)) + "</tr></td></table>";
    }
}
