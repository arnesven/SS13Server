package model.fancyframe;

import model.Actor;
import model.Player;
import model.objects.general.VendingMachine;

public class SeedsAreUsFancyFrame extends VendingMachineFancyFrame {
    public SeedsAreUsFancyFrame(Actor performingClient, VendingMachine v) {
        super((Player)performingClient, v, "<b>Seed's R Us</b><br/><i>Growing Strong!</i>", "green");
    }
}
