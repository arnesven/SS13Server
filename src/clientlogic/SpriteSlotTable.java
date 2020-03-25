package clientlogic;

import clientview.MapPanel;
import util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpriteSlotTable {
    private final Room room;
    private List<Pair<Double, Double>> table;
    private Map<Integer, Boolean> available;
    private List<Integer> extended;

    public SpriteSlotTable(Room room) {
        this.room = room;
        int width = (room.getScaledWidthPX() / MapPanel.getZoom());
        int height = (room.getScaledHeightPX() / MapPanel.getZoom());
        table = new ArrayList<>();
        available = new HashMap<>();
        extended = new ArrayList<>();
        for (int y = 1; y < height; ++y) {
            for (int x = 1; x < width; ++x) {
                available.put(table.size(), true);
                double finx = x / (double)width * (double)room.getWidth();
                double finy = y / (double)height * (double)room.getHeight();
                table.add(new Pair<>(finx, finy));
                extended.add(0);
            }
        }
    }

    public Pair<Double, Double> getSlot(int hash) {
        if (table.size() == 0) {
            return new Pair<>(0.0, 0.0);
        }
        if (table.size() == 1 || !available.get(0)) {
            return getExtendedSlots(0);
        }
        int index = hash % table.size();
        int start = index;
        while (!available.get(index)) {
            index++;
            if (index == start) {
                return getExtendedSlots(index);
            } else if (index == table.size()) {
                index = 0;
            }
        }
        available.put(index, false);
        return table.get(index);
    }

    private Pair<Double, Double> getExtendedSlots(int index) {
        extended.set(index, extended.get(index)+1);
        Pair<Double, Double> slot = table.get(index);
        slot.first += extended.get(index)*0.25;
        return slot;
    }

}
