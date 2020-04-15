package model.fancyframe;

import graphics.sprites.Sprite;
import model.Player;
import model.objects.general.EmaggedVendingMachine;
import model.objects.general.VendingMachine;
import util.Logger;

public class EmaggedVendingMachineFancyFrame extends VendingMachineFancyFrame {
    private final EmaggedVendingMachine emagged;

    public EmaggedVendingMachineFancyFrame(Player performingClient, EmaggedVendingMachine emaggedVendingMachine) {
        super(performingClient, emaggedVendingMachine);
        this.emagged = emaggedVendingMachine;

        buildContent(performingClient, emaggedVendingMachine);
    }

    @Override
    protected Sprite getLookForItem(int col, Player p) {
        if (emagged == null) {
            return super.getLookForItem(col, p);
        }
        Sprite sp = this.emagged.getInner().getItems().get(col).getSprite(p);
        return sp;
    }
}
