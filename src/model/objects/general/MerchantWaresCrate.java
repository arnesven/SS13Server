package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.ShopFromMerchantAction;
import model.characters.general.GameCharacter;
import model.characters.visitors.MerchantCharacter;
import model.items.RandomItemManager;
import model.items.general.GameItem;
import model.map.rooms.Room;
import model.npcs.MerchantNPC;
import model.objects.general.ContainerObject;
import util.MyRandom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by erini02 on 15/11/16.
 */
public class MerchantWaresCrate extends ContainerObject {
    private final MerchantNPC merchant;

    public MerchantWaresCrate(Room shuttleGate, MerchantNPC merchant) {
        super("Merchant's Wares", shuttleGate);
        this.merchant = merchant;

        for (int i = 4 + MyRandom.nextInt(3); i > 0; --i) {
            GameItem it;
            do {
                it = RandomItemManager.getRandomMerchantWares();
            } while (getInventory().contains(it));
            getInventory().add(it);
        }
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("merchantwarescrates", "storage.png", 49, this);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (cl.getPosition().getActors().contains(merchant) && !merchant.isDead()) {
            if (getInventory().size() > 0) {
                at.add(new ShopFromMerchantAction(this, merchant));
            }
        }

    }

    @Override
    public boolean accessibleTo(Actor ap) {
        return ap.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof MerchantCharacter);
    }
}
