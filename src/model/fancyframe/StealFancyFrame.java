package model.fancyframe;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.LootAction;
import model.actions.characteractions.StealAction;
import model.actions.general.MultiAction;
import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StealFancyFrame extends LootingFancyFrame {
    private final Actor victim;

    public StealFancyFrame(Player performingClient, GameData gameData, Actor victim) {
        super(performingClient, gameData, victim, "Stealing", "Planting");
        this.victim = victim;
    }

    @Override
    protected MultiAction getFinalAction(GameData gameData, Player player, Set<GameItem> puttings, Set<GameItem> gettings) {
        MultiAction multiAction = new MultiAction("Steal/Plant Items");
        for (GameItem gi : puttings) {
            List<String> args = new ArrayList<>();
            args.add(victim.getPublicName(player));
            args.add(gi.getPublicName(player));
            StealAction la = new StealAction(player);
            la.setActionTreeArguments(args, player);
            multiAction.addAction(la);
        }

        for (GameItem gi : gettings) {
            multiAction.addAction(super.makeStoreAction(gi, gameData, player, victim));
        }


        return multiAction;
    }
}
