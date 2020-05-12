package model.map.rooms;

import model.GameData;
import model.items.EmptyContainer;
import model.map.doors.Door;
import model.map.doors.UpgoingStairsDoor;
import model.npcs.robots.DockWorkerBot;
import model.objects.consoles.MarketConsole;
import model.objects.consoles.RequisitionsConsole;
import model.objects.mining.GeneralManufacturer;

public class CargoBayRoom extends TechRoom {
    public CargoBayRoom(int id, int x, int y, int w, int h, int[] neighbors, Door[] doors) {
        super(id, "Cargo Bay", "", x, y, w, h, neighbors, doors);
        setZ(-1);
        addObject(new UpgoingStairsDoor(this));
        addObject(new GeneralManufacturer(this));
        this.addObject(new MarketConsole(this));
        this.addItem(new EmptyContainer());
    }

    @Override
    public void doSetup(GameData gameData) {
        super.doSetup(gameData);
        gameData.addNPC(new DockWorkerBot(this));
        gameData.addNPC(new DockWorkerBot(this));
        this.addObject(new RequisitionsConsole(this, gameData));
    }
}
