package clientlogic;

import clientview.OverlaySprite;
import clientview.components.MapPanel;
import util.MyRandom;
import util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpriteSlotTable {
    private final Room room;
    private final double oneHalf;
    private List<Pair<Double, Double>> table;
    private Map<Integer, Boolean> available;
    private List<Integer> extended;
    private int size;
    private OverlaySprite fillerSprite;

    public SpriteSlotTable(Room room) {
        this.room = room;
        int width = (room.getScaledWidthPX() / MapPanel.getZoom());
        int height = (room.getScaledHeightPX() / MapPanel.getZoom());
        table = new ArrayList<>();
        available = new HashMap<>();
        extended = new ArrayList<>();
        size = 0;
        oneHalf = 0.5 * (double)room.getWidth() / (double)width;
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


    public Pair<Double, Double> getSlot(OverlaySprite sp) {
        if (sp.getSprite().contains("fillwholeroom")) {
            this.fillerSprite = sp;
        }
        if (table.size() == 0) {
            return new Pair<>(0.0, 0.0);
        }
        int index = sp.getHash() % table.size();
        if (isFull()) {
            return getExtendedSlots(index);
        }
        while (!available.get(index)) {
            index++;
            if (index == table.size()) {
                index = 0;
            }
        }
        size++;
        available.put(index, false);
        return table.get(index);
    }

    private boolean isFull() {
        return size == table.size();
    }

    private Pair<Double, Double> getExtendedSlots(int index) {
        extended.set(index, extended.get(index)+1);
        Pair<Double, Double> slot = table.get(index);
        slot.first += Math.pow(oneHalf, extended.get(index));
        return slot;
    }

    public void fillTheRest(Graphics g, Room r, int xOffset, int yOffset, int xoffPX, int yoffPX, int currZ) {
        if (fillerSprite != null) {
            for (Integer i : available.keySet()) {
                if (available.get(i)) {
                    Pair<Double, Double> pos = table.get(i);
                    OverlaySprite copy = fillerSprite.copyYourself();
                    copy.setFrameShift(MyRandom.nextInt(fillerSprite.getFrames()));
                    copy.drawYourselfInRoom(g, r, pos, xOffset, yOffset, xoffPX, yoffPX, currZ);
                    fillerSprite.addAdditionalHitbox(copy.getHitBox());
                }
            }
        }
    }
}
