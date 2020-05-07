package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.actions.general.PickUpAction;
import model.actions.objectactions.RecycleAction;
import model.items.general.GameItem;
import model.map.rooms.Room;
import model.objects.general.GameObject;
import model.objects.recycling.TrashBin;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

public class RecycleItemsOnFloorBehavior implements ActionBehavior {
    @Override
    public void act(Actor npc, GameData gameData) {
        if (!npc.getPosition().getItems().isEmpty()) {
            GameItem selected = MyRandom.sample(npc.getPosition().getItems());

            if (TrashBin.hasTrashCan(npc.getPosition())) {
                pickup(selected, npc, gameData);
                recycle(selected, npc, gameData);
                List<GameItem> itemsToRecycle = new ArrayList<>();
                itemsToRecycle.addAll(npc.getItems());
                for (GameItem it : itemsToRecycle) {
                    recycle(it, npc, gameData);
                }
            } else {
                pickup(selected, npc, gameData);

            }
        }
    }

    private void pickup(GameItem selected, Actor npc, GameData gameData) {
        List<String> args = new ArrayList<>();
        args.add(selected.getPublicName(npc));
        PickUpAction pua = new PickUpAction(npc);
        pua.setActionTreeArguments(args, npc);
        pua.doTheAction(gameData, npc);
    }

    private void recycle(GameItem selected, Actor npc, GameData gameData) {
        List<String> args = new ArrayList<>();
        args.add(selected.getFullName(npc));
        RecycleAction ra = new RecycleAction(TrashBin.getTrashBin(npc.getPosition()));
        ra.setActionTreeArguments(args, npc);
        ra.doTheAction(gameData, npc);
    }


}
