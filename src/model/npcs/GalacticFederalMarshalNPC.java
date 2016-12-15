package model.npcs;

import model.Player;
import model.characters.general.GalacticFederalMarshalCharacter;
import model.items.suits.MarshalOutfit;
import model.items.suits.UncoolSunglasses;
import model.map.rooms.Room;
import model.npcs.behaviors.ArrestLoadOrCounterAttack;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.behaviors.MoveTowardsMostWantedIfClose;

/**
 * Created by erini02 on 17/11/16.
 */
public class GalacticFederalMarshalNPC extends NPC {
    private Player mostWanted;

    public GalacticFederalMarshalNPC(int i, Player mostWantedCriminal, Room startPos) {
        super(new GalacticFederalMarshalCharacter(i, startPos.getID()),
                new MeanderingMovement(0.0),
                new DoNothingBehavior(), startPos);
        mostWanted = mostWantedCriminal;
        putOnSuit(new MarshalOutfit());
        putOnSuit(new UncoolSunglasses());
        setMaxHealth(2.0);
        setHealth(2.0);
        giveStartingItemsToSelf();
        this.setMoveBehavior(new MoveTowardsMostWantedIfClose(this));
        this.setActionBehavior(new ArrestLoadOrCounterAttack(this));

    }


    @Override
    public boolean hasInventory() {
        return true;
    }

    public void setMostWanted(Player mostWanted) {
        this.mostWanted = mostWanted;
    }

    public Player getMostWanted() {
        return mostWanted;
    }
}
