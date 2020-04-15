package model.objects.general;

import model.items.foods.Banana;
import model.items.foods.Doughnut;
import model.items.foods.NukaCola;
import model.items.foods.SpaceCheetos;
import model.items.general.PackOfSmokes;
import model.items.general.ZippoLighter;
import model.map.rooms.Room;

/**
 * Created by erini02 on 28/11/16.
 */
public class JunkVendingMachine extends VendingMachine {
    public JunkVendingMachine(Room r) {
        super("Vending Machine", r);
        addSelection(new Doughnut(null));
        addSelection(new Banana(null));
        addSelection(new PackOfSmokes());
        addSelection(new ZippoLighter());
        addSelection(new NukaCola(null));
        addSelection(new SpaceCheetos(null));
    }




}
