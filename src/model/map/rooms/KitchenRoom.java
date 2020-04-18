package model.map.rooms;

import model.GameData;
import model.items.SeveredButt;
import model.items.foods.RawFoodContainer;
import model.items.general.FireExtinguisher;
import model.map.doors.Door;
import model.npcs.animals.ChimpNPC;
import model.objects.general.CrateObject;
import model.objects.general.Dumbwaiter;
import model.objects.general.CookOMatic;
import model.objects.shipments.FoodShipment;
import model.objects.shipments.HalfFullFoodShipment;

public class KitchenRoom extends SupportRoom {

	public KitchenRoom(GameData gameData, int id, int x, int y, int width, int height, int[] ns, Door[] ds) {
		super(id, "Kitchen", "Kitch", x, y, width, height, ns, ds);
		
		this.addObject(new CookOMatic(this));
        this.addObject(new Dumbwaiter(this));
        this.addItem(new FireExtinguisher());
        this.addObject(new CrateObject(this, new HalfFullFoodShipment(), gameData));

	}

}
