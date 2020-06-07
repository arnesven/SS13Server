package model.objects.general;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.VendingMachineAction;
import model.actions.objectactions.WalkUpToVendingMachine;
import model.fancyframe.FancyFrame;
import model.fancyframe.VendingMachineFancyFrame;
import model.items.general.*;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 04/05/16.
 */
public abstract class VendingMachine extends ElectricalMachinery {

    private ArrayList<GameItem> selection;
    private GameData gameData = null;

    public VendingMachine(String name, Room r) {
        super(name,  r);
        selection = new ArrayList<>();
    }

    public void addSelection(GameItem it) {
        selection.add(it);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (isBroken()) {
            return new Sprite("vendingmachinebroken", "vending.png", 4, this);
        }
        if (gameData != null) {
            if (!isPowered()) {
                return new Sprite("vendingmachinenopower", "vending.png", 5, this);
            }
        }
        return new Sprite("vendingmachine", "vending.png", 3, this);
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        this.gameData = gameData;
        //at.add(new VendingMachineAction(this));
        if (cl instanceof Player) {
            at.add(new WalkUpToVendingMachine(gameData, (Player)cl, this));
        }
    }

    @Override
    public List<GameItem> getItems() {
        return selection;
    }

    public FancyFrame getFancyFrame(Actor performingClient) {
        return new VendingMachineFancyFrame((Player)performingClient, this);
    }

    @Override
    public boolean isLootable() {
        return isBroken();
    }
}
