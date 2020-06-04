package model.actions.itemactions;

import model.GameData;
import model.Player;
import model.actions.FreeAction;
import model.fancyframe.AdvancedBuildingFancyFrame;
import model.items.general.RoomPartsStack;

import java.util.List;

public class ShowBuildNewRoomFancyFrame extends FreeAction {
    private final RoomPartsStack stack;

    public ShowBuildNewRoomFancyFrame(GameData gameData, Player cl, RoomPartsStack roomPartsStack) {
        super("Open Advanced Building", gameData, cl);
        this.stack = roomPartsStack;
    }

    @Override
    protected void doTheFreeAction(List<String> args, Player p, GameData gameData) {
        p.setFancyFrame(new AdvancedBuildingFancyFrame(p, gameData, stack));
    }
}
