package model;

import model.items.general.BombItem;
import model.items.general.BombPowerCord;
import model.map.doors.PowerCord;
import util.MyRandom;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BombDefusing implements Serializable {
    private List<PowerCord> cords;
    private String name;
    private final String defuseButton;
    private boolean correctButtonPressed;
    private boolean beingDefused;
    private String tip;

    public BombDefusing(BombItem bombItem) {
        setupCords();
        String[] buttonNames = new String[]{"RED", "GREEN"};
        this.defuseButton = buttonNames[MyRandom.nextInt(2)];
        correctButtonPressed = false;
        beingDefused = false;
        this.name = "Simon";
        if (MyRandom.nextDouble() < 0.17) {
            name = MyRandom.getRandomName();
        }
        tip = makeDefuseTip();
    }

    private void setupCords() {
        List<Color> colors = new ArrayList<>();
        for (int i = 3; i > 0; --i) {
            Color col;
            do {
                col = PowerCord.randomPowerCordColor();
            } while (colors.contains(col));
            colors.add(col);
        }

        cords = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            cords.add(new BombPowerCord(colors.get(i), MyRandom.nextInt(2)));
        }
    }


    private String makeDefuseTip() {
        StringBuilder tip = new StringBuilder("\"" + name + "\":<i> To defuse, cut ");
        if (cords.get(0).getState() == 1 && cords.get(1).getState() == 1 && cords.get(2).getState() == 1) {
            if (name.equals("Simon")) {
                tip.append("all wires, ");
            } else {
                tip.append("no wires, ");
            }
        } else if (cords.get(0).getState() == 0 && cords.get(1).getState() == 0 && cords.get(2).getState() == 0) {
            if (name.equals("Simon")) {
                tip.append("no wires, ");
            } else {
                tip.append("all wires, ");
            }
        } else {
            int i = 1;
            for (PowerCord cord : cords) {
                if (name.equals("Simon")) {
                    if (cord.getState() == 1) {
                        tip.append(" the " + PowerCord.getNameForPowerCordColor(cord.getColor()).toLowerCase() + " wire, ");
                    }
                } else {
                    if (cord.getState() == 0) {
                        tip.append(" the " + PowerCord.getNameForPowerCordColor(cord.getColor()).toLowerCase() + " wire, ");
                    }
                }
            }
        }

        String button = defuseButton;
        if (!name.equals("Simon")) {
            if (button.equals("GREEN")) {
                button = "RED";
            } else {
                button = "GREEN";
            }
        }

        tip.append("then push the " + button.toLowerCase() + " button.</i>");
        return tip.toString();
    }


    public String getTip() {
        return tip;
    }

    public List<PowerCord> getCords() {
        return cords;
    }

    public void pressButton(String button) {
        if (button.equals(defuseButton)) {
            correctButtonPressed = true;
        }
    }

    public boolean isDefused() {
        for (PowerCord pc : cords) {
            if (pc.getState() == 1) {
                return false;
            }
        }
        return correctButtonPressed;
    }

    public void setBeingDefused(boolean b) {
        beingDefused = b;
    }

    public boolean isBeingDefused() {
        return beingDefused;
    }
}
