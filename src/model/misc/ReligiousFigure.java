package model.misc;

import model.Actor;
import model.GameData;
import model.map.floors.ImprovedChapelFloorSet;
import model.objects.Altar;

import java.io.Serializable;

public class ReligiousFigure implements Serializable {

    private final String name;
    private final int floorsetCol;
    private final String practitioner;

    public ReligiousFigure(String name, int floorsetCol, String practicioner) {
        this.name = name;
        this.floorsetCol = floorsetCol;
        this.practitioner = practicioner;
    }

    public void doWhenPrayedTo(GameData gameData, Actor prayer, Altar altar) {
        altar.getPosition().setFloorSet(new ImprovedChapelFloorSet("chapelfloor"+name, floorsetCol));
        altar.addToPoints(1);
        altar.setChristian(false);
    }

    public String getName() {
        return name;
    }

    public String getPracticionerName() {
        return practitioner;
    }

}
