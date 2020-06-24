package model.map.rooms;

import model.GameData;
import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.map.floors.HallwayFloorSet;
import model.objects.ai.SecurityCamera;
import model.objects.general.GameObject;
import sounds.Sound;

public class NewAlgiersRoom extends StationRoom implements JukeBoxRoom {

    private String tune = "nothing";

    public NewAlgiersRoom(int i, String name, int x, int y, int width, int height, int[] neighs, Door[] doors) {
        super(i, name, x, y, width, height, neighs, doors);
    }

    @Override
    public FloorSet getFloorSet() {
        return new HallwayFloorSet();
    }

    @Override
    protected boolean getsTrashBin() {
        return false;
    }

    @Override
    public void doSetup(GameData gameData) {
        super.doSetup(gameData);
        this.getObjects().removeIf((GameObject obj) -> obj instanceof SecurityCamera);
    }

    @Override
    public Sound getSpecificAmbientSound() {
        if (!tune.equals("nothing")) {
            return new Sound(tune);
        }
        return new Sound("piratesong_short");
    }

    public void setAmbientSound(String selectedTune) {
        tune = selectedTune;
    }

}
