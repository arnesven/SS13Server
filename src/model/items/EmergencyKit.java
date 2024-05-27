package model.items;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.itemactions.UnpackItemAction;
import model.items.chemicals.IodinePill;
import model.items.general.GameItem;
import model.items.general.UnpackableItem;
import model.items.suits.CheapOxygenMask;
import model.items.tools.Wrench;

import java.util.ArrayList;

public class EmergencyKit extends UnpackableItem {
    public EmergencyKit() {
        super("Emergency Kit", 1.0, true, 50);
        addInnerItem(new Wrench());
        addInnerItem(new MedPatch());
        addInnerItem(new CheapOxygenMask());
        addInnerItem(new IodinePill());
    }

    @Override
    public GameItem clone() {
        return new EmergencyKit();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("emergencykit", "storage.png", 83, this);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "Open in case of emergency! Contents: Wrench, Med-Patch, Oxygen Mask and Iodine Pill.";
    }

    @Override
    protected UnpackItemAction getUnpackAction(GameData gameData, ArrayList<Action> at, Actor cl) {
        return new UnpackEmergencyKitAction(this);
    }

    private class UnpackEmergencyKitAction extends UnpackItemAction {
        public UnpackEmergencyKitAction(EmergencyKit emergencyKit) {
            super("Unpack", emergencyKit);
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            StringBuilder content = new StringBuilder();
            for (GameItem gi : getInners()) {
                content.append(", " + gi.getBaseName());
            }
            performingClient.addTolastTurnInfo("You unpacked the kit. It contained " +
                    content.toString().replaceFirst(", ", ""));
            super.execute(gameData, performingClient);
        }
    }
}
