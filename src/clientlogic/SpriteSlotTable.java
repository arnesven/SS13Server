package clientlogic;

import clientview.OverlaySprite;
import clientview.components.MapPanel;
import util.MyRandom;
import util.Pair;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

public class SpriteSlotTable {
    private final Room room;
    private final double oneHalf;
    private List<Pair<Double, Double>> table;
    private Map<Integer, OverlaySprite> available;
    private List<List<OverlaySprite>> extended;
    private int size;
    private OverlaySprite fillerSprite;

    public SpriteSlotTable(Room room, ArrayList<OverlaySprite> overlaySprites) {
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
                available.put(table.size(), null);
                double finx = x / (double)width * (double)room.getWidth();
                double finy = y / (double)height * (double)room.getHeight();
                table.add(new Pair<>(finx, finy));
                extended.add(new ArrayList<>());
            }
        }


        ArrayList<OverlaySprite> sprs = new ArrayList<>();
        sprs.addAll(overlaySprites);
        Collections.sort(sprs, new Comparator<OverlaySprite>() {
            @Override
            public int compare(OverlaySprite a, OverlaySprite b) {
                return a.getSlotRelPosValue() - b.getSlotRelPosValue();
            }
        });

        System.out.println("Assigning slots");
        for (OverlaySprite sp : sprs) {
            if (sp.getRelPos().equals("ANY")) {
                getHashedSlot(sp);
            } else {
                System.out.println("Found an overlay sprite with preferred placement " + sp.getName());
                String[] parts = sp.getRelPos().split(",");
                Point2D point = new Point2D.Double(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
                getSlotClosestTo(sp, point);
            }
        }
        System.out.println("Slot assignment done!");
    }

    private Pair<Double, Double> getSlotClosestTo(OverlaySprite sp, Point2D point) {
        int index = 0;
        double closest = Double.MAX_VALUE;
        Integer best = -1;
        for (Pair<Double, Double> pos : table) {
            if (available.get(index) == null) {
                double distSq = point.distanceSq(pos.first, pos.second);
                if (distSq < closest) {
                    closest = distSq;
                    best = index;
                }
            }
            index++;
        }

        if (best != -1) {
            available.put(best, sp);
            return table.get(best);
        }
        return getHashedSlot(sp);
    }


    private Pair<Double, Double> getHashedSlot(OverlaySprite sp) {
        if (sp.getSprite().contains("fillwholeroom")) {
            this.fillerSprite = sp;
        }
        if (table.size() == 0) {
            return new Pair<>(0.0, 0.0);
        }
        int index = sp.getHash() % table.size();
        if (isFull()) {
            return getExtendedSlots(index, sp);
        }
        while (available.get(index) != null) {
            index++;
            if (index == table.size()) {
                index = 0;
            }
        }
        size++;
        available.put(index, sp);
        return table.get(index);
    }

    private boolean isFull() {
        return size == table.size();
    }

    private Pair<Double, Double> getExtendedSlots(int index, OverlaySprite sp) {
        extended.get(index).add(sp);
        //extended.set(index, extended.get(index)+1);
        Pair<Double, Double> slot = table.get(index);
        //slot.first += 0.1 * oneHalf * extended.get(index).size();
        return slot;
    }

    private void fillTheRest(Graphics g, Room r, int xOffset, int yOffset, int xoffPX, int yoffPX, int currZ) {
        if (fillerSprite != null) {
            for (Integer i : available.keySet()) {
                if (available.get(i) == null) {
                    Pair<Double, Double> pos = table.get(i);
                    OverlaySprite copy = fillerSprite.copyYourself();
                    copy.setFrameShift(MyRandom.nextInt(fillerSprite.getFrames()));
                    copy.drawYourselfInRoom(g, r, pos, xOffset, yOffset, xoffPX, yoffPX, currZ);
                    fillerSprite.addAdditionalHitbox(copy.getHitBox());
                }
            }
        }
    }

    public void drawAll(Graphics g, Room r, int xOffset, int yOffset, int xoffPX, int yoffPX, boolean shadow, int currZ) {
        for (Integer i : available.keySet()) {
            if (available.get(i) != null) {
                OverlaySprite sp = available.get(i);
                Pair<Double, Double> pos = table.get(i);
                sp.drawYourselfInRoom(g, r, pos, xOffset, yOffset, xoffPX, yoffPX, currZ);
            }
        }

        int index = 0;
        for (List<OverlaySprite> list : extended) {
            for (OverlaySprite os : list) {
                Pair<Double, Double> slot = table.get(index);
                os.drawYourselfInRoom(g, r, slot, xOffset, yOffset, xoffPX, yoffPX, currZ);
            }
            index++;
        }

        fillTheRest(g, r, xOffset, yOffset, xoffPX, yoffPX, currZ);
    }
}
