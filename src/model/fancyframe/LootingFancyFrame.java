package model.fancyframe;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.LootAction;
import model.actions.general.MultiAction;
import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LootingFancyFrame extends ManageItemsFancyFrame {
    private final Actor victim;

    public LootingFancyFrame(Player performingClient, GameData gameData, Actor victim) {
        super(performingClient, gameData,
                victim.getPublicName(performingClient) + "'s Items - Looting",
                victim,
                ((GameItem gi, Player pl) -> gi.getPublicName(pl)),
                Integer.MAX_VALUE,
                "Inventory - Storing (max 1)",
                performingClient,
                ((GameItem gi, Player pl) -> gi.getFullName(pl)),
                1, null);
        this.victim = victim;
    }

    @Override
    protected MultiAction getFinalAction(GameData gameData, Player player, Set<GameItem> puttings, Set<GameItem> gettings) {
        MultiAction multiAction = new MultiAction("Loot Items");
        for (GameItem gi : puttings) {
            List<String> args = new ArrayList<>();
            args.add(victim.getPublicName(player));
            args.add(gi.getPublicName(player));
            LootAction la = new LootAction(player);
            la.setActionTreeArguments(args, player);
            multiAction.addAction(la);
        }


        return multiAction;
    }
}
