package model.objects.consoles;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtConsoleAction;
import model.actions.general.Action;
import model.actions.objectactions.SetSolarPanelRotationAction;
import model.fancyframe.ConsoleFancyFrame;
import model.fancyframe.SolarArrayControlFancyFrame;
import model.items.NoSuchThingException;
import model.map.rooms.Room;
import model.objects.decorations.SolarPanel;
import model.objects.general.GameObject;
import model.objects.power.PowerSupply;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

public class SolarArrayControl extends Console implements PowerSupply {
    private final int solarPanelRoomID;
    private final GameData gameData;
    private List<SolarPanel> solarPanels;

    public SolarArrayControl(Room r, int solarPanelRoomID, GameData gameData) {
        super("Solar Array Control #" + solarPanelRoomID, r);
        this.solarPanelRoomID = solarPanelRoomID;
        this.gameData = gameData;
    }

    @Override
    public boolean isPowered() {
        return true;
    }

    public List<SolarPanel> findSolarPanels() {
        List<SolarPanel> result = new ArrayList<>();
        try {
            for (GameObject obj : gameData.getRoomForId(solarPanelRoomID).getObjects()) {
                if (obj instanceof SolarPanel) {
                    result.add((SolarPanel)obj);
                }
            }
        } catch (NoSuchThingException e) {
            Logger.log(Logger.CRITICAL, "Could not find solar panel array.");
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public String getName() {
        return "Solar Array #" + solarPanelRoomID;
    }

    @Override
    public double getPower() {
        this.solarPanels = findSolarPanels();
        double sum = 0;
        for (SolarPanel sp : solarPanels) {
            sum += sp.getPower(gameData);
        }

        return sum;
    }

    @Override
    public double getEnergy() {
        return Double.MAX_VALUE;
    }

    @Override
    public void drainEnergy(double d) {

    }

    @Override
    public void updateYourself(GameData gameData) {

    }

    @Override
    public double getPowerConsumption() {
        return 0.0;
    }

    @Override
    protected void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        at.add(new SetSolarPanelRotationAction(this));
        if (cl instanceof Player) {
            at.add(new SitDownAtConsoleAction(gameData, this) {
                @Override
                protected ConsoleFancyFrame getNewFancyFrame(Console console, GameData gameData, Player performingClient) {
                    return new SolarArrayControlFancyFrame(performingClient, SolarArrayControl.this, gameData);
                }
            });
        }
    }

    public void setAllConnectedSolarPanelRotation(int selectedAngle) {
        for (SolarPanel sp : solarPanels) {
            sp.setRotation(selectedAngle);
        }
    }

    @Override
    public Sprite getNormalSprite(Player whosAsking) {
        return new Sprite("powerconsole", "computer2.png", 16, 10, this);
    }
}
