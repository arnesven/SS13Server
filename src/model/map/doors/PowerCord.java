package model.map.doors;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.events.damage.ElectricalDamage;
import model.items.general.GameItem;
import util.HTMLText;
import util.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class PowerCord extends GameItem {
    private final Color color;
    private final DoorMechanism doorMechanism;
    private boolean isCut;
    private int state; // -1 = undefined

    public PowerCord(Color color, int initialState, DoorMechanism dm) {
        super("Power Cord", 0.0, false, 0);
        this.color = color;
        isCut = false;
        this.state = initialState;
        this.doorMechanism = dm;
    }

    public Color getColor() {
        return color;
    }


    public boolean isCut() {
        return isCut;
    }

    public int getState() {
        if (isCut) {
            return -1;
        }
        return state;
    }

    public void setCut(boolean b) {
        isCut = b;
    }

    public void setState(int i) {
        this.state = i;
    }

    public Action cut(Player player, GameData gameData) {
        if (getState() == 1) {
            player.beExposedTo(null, new ElectricalDamage(0.5), gameData);
            gameData.getChat().serverInSay(HTMLText.makeText("red", "You got a shock from a loose wire!"), player);
        }
        isCut = true;
        return specificCutAction(player, gameData);
    }



    protected abstract Action specificCutAction(Player player, GameData gameData);

    public Action mend(Player player, GameData gameData) {
        this.repair(doorMechanism);
        if (getState() == 1) {
            Logger.log("After mending power cable, state was 1, ITS ELECTRIC!");
            player.beExposedTo(null, new ElectricalDamage(0.5), gameData);
            gameData.getChat().serverInSay(HTMLText.makeText("red", "You got a shock from a loose wire!"), player);
        }
        return specificMendAction(player, gameData);
    }

    protected abstract Action specificMendAction(Player player, GameData gameData);

    public Action pulse(Player player, GameData gameData) {
        if (getState() != 1) {
            return specificPulseAction(player, gameData);
        }
        return null;
    }

    protected abstract Action specificPulseAction(Player player, GameData gameData);


    public boolean isOK() {
        return !isCut && state != -1;
    }

    public void repair(DoorMechanism dm) {
        setCut(false);
        setState(0);
    }



    @Override
    public Sprite getSprite(Actor whosAsking) {
        Sprite sp = new Sprite("straightpowercord" + getColor().getRed() + "-" + getColor().getGreen() + "-" + getColor().getBlue(),
                "power_cords.png", 0, 1, this);
        sp.setColor(getColor());
        Sprite sp2 = new Sprite("straightpoweroverlay" + getColor().getRed() + "-" + getColor().getGreen() + "-" + getColor().getBlue(),
                "power_cords.png", 2, 1, this);
        List<Sprite> sprs = new ArrayList<>();
        sprs.add(sp);
        sprs.add(sp2);
        return new Sprite("final" + sp.getName(), "human.png", 0, sprs, null);
    }


    public Sprite cutSprite(Actor whosAsking) {
        Sprite sp = new Sprite("cutpowercord" + getColor().getRed() + "-" + getColor().getGreen() + "-" + getColor().getBlue(),
                "power_cords.png", 1, 1, this);
        sp.setColor(getColor());
        Sprite sp2 = new Sprite("cutpoweroverlay" + getColor().getRed() + "-" + getColor().getGreen() + "-" + getColor().getBlue(),
                "power_cords.png", 3, 1, this);
        List<Sprite> sprs = new ArrayList<>();
        sprs.add(sp);
        sprs.add(sp2);
        return new Sprite("final" + sp.getName(), "human.png", 0, sprs, null);
    }


    public String drawYourselfInHTML(Player player) {
        StringBuilder result = new StringBuilder();
        for (int i = 3; i > 0; --i) {
            if (i == 2 && isCut()) {
                result.append(HTMLText.makeImage(cutSprite(player)));
            } else {
                result.append(HTMLText.makeImage(getSprite(player)));
            }
        }
        return result.toString();
    }


    @Override
    public GameItem clone() {
        return null;
    }


}