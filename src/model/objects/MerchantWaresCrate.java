package model.objects;

import graphics.sprites.Sprite;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.ShopFromMerchantAction;
import model.items.general.GameItem;
import model.map.Room;
import model.npcs.MerchantNPC;
import model.npcs.NPC;
import model.objects.general.ContainerObject;
import model.objects.general.GameObject;
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

        List<GameItem> randomItems = MyRandom.getItemsWhichAppearRandomly();
        Collections.shuffle(randomItems);
        for (int i = 3 + MyRandom.nextInt(4); i > 0; --i) {
            getInventory().add(randomItems.remove(0));
        }
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("merchantwarescrates", "storage.png", 49);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Player cl, ArrayList<Action> at) {
        if (cl.getPosition().getActors().contains(merchant)) {
            if (getInventory().size() > 0) {
                at.add(new ShopFromMerchantAction(this, merchant));
            }
        }

    }
}
