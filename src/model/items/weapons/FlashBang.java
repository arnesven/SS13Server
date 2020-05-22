package model.items.weapons;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.events.FlashEvent;
import model.items.foods.ExplodingFood;
import model.items.general.ExplodableItem;
import model.items.general.GameItem;
import model.items.general.GrenadeItem;
import model.map.rooms.Room;

public class FlashBang extends GrenadeItem {
    public FlashBang() {
        super("Flash Bang", 0.25, true, 109);
    }

    @Override
    public void explode(GameData gameData, Room room, Actor maker) {
        FlashEvent flash = new FlashEvent(gameData, room);
        room.addEvent(flash);
        gameData.addEvent(flash);
        room.getItems().remove(this);
    }

    @Override
    public void setConceledWithin(ExplodingFood explodingFood) {

    }
    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("flashbangsp", "grenade.png", 3, this);
    }



    @Override
    public GameItem clone() {
        return new FlashBang();
    }

    @Override
    public String getText() {
        return "There was a bright flash!";
    }

    @Override
    public boolean isDamageSuccessful(boolean reduced) {
        return true;
    }

    @Override
    public String getName() {
        return "White Flash";
    }

    @Override
    public void doDamageOnMe(Target target) {

    }

    @Override
    public GameItem getOriginItem() {
        return this;
    }

    @Override
    public double getDamage() {
        return 0;
    }

    @Override
    public void doExplosionAction(Room targetRoom, GameData gameData, Actor performingClient) {
        this.explode(gameData, targetRoom, performingClient);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "An exploding canister, which upon detonation emits a powerful flash which blinds and disorientates any unprotected targets.";
    }
}
