package model.fancyframe;

import model.Actor;
import model.GameData;
import model.ItemHolder;
import model.Player;
import model.actions.LootAction;
import model.actions.general.MultiAction;
import model.items.general.GameItem;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LootingFancyFrame extends ManageItemsFancyFrame {
    private final Actor victim;

    public LootingFancyFrame(Player performingClient, GameData gameData, Actor victim, String lootVerb, String storeVerb) {
        super(performingClient, gameData,
                victim.getPublicName(performingClient) + "'s Items - " + lootVerb,
                new LootItems(victim),
                ((GameItem gi, Player pl) -> gi.getPublicName(pl)),
                Integer.MAX_VALUE,
                "Inventory - " + storeVerb + " (max 1)",
                performingClient,
                ((GameItem gi, Player pl) -> gi.getFullName(pl)),
                1, null);
        this.victim = victim;
    }

    @Override
    protected MultiAction getFinalAction(GameData gameData, Player player, Set<GameItem> puttings, Set<GameItem> gettings) {
        MultiAction multiAction = new MultiAction("Loot/Plant Items");
        for (GameItem gi : puttings) {
            List<String> args = new ArrayList<>();
            args.add(victim.getPublicName(player));
            args.add(gi.getPublicName(player));
            LootAction la = new LootAction(player);
            la.setActionTreeArguments(args, player);
            multiAction.addAction(la);
        }

        for (GameItem gi : gettings) {
            multiAction.addAction(super.makeStoreAction(gi, gameData, player, victim));
        }


        return multiAction;
    }

    private static class LootItems implements ItemHolder {
        private final Actor victim;

        public LootItems(Actor victim) {
            this.victim = victim;
        }

        @Override
        public List<GameItem> getItems() {
            List<GameItem> newArr = new ArrayList<>();
            newArr.addAll(victim.getItems());
            if (victim.isDead() || !victim.getsActions()) {
                newArr.addAll(victim.getCharacter().getEquipment().getTopEquipmentAsList());
            }
            return newArr;
        }

        @Override
        public String getPublicName(Actor forWhom) {
            return victim.getPublicName(forWhom);
        }

        @Override
        public Room getPosition() {
            return victim.getPosition();
        }
    }
}
