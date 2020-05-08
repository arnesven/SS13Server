package model.actions.fancyframeactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.LootAction;
import model.actions.general.ActionOption;
import model.actions.general.DoNothingAction;
import model.actions.general.TargetingAction;
import model.fancyframe.FancyFrame;
import model.fancyframe.LootingFancyFrame;

import java.util.List;

public class ShowLootingFancyFrame extends LootAction {
    private final GameData gameData;
    private final Actor victim;

    public ShowLootingFancyFrame(Player forWhom, GameData gameData, Actor victim) {
        super(forWhom);
        setName("Loot/Store " + victim.getPublicName(forWhom));
        this.gameData = gameData;
        this.victim = victim;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        // Should not happen
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opt = new ActionOption(getName());
        for (ActionOption superopt : super.getOptions(gameData, whosAsking).getSuboptions()) {
            opt.addOption(superopt.getName());
        }
        return opt;
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        if (performingClient instanceof Player) {
            FancyFrame ff = new LootingFancyFrame((Player)performingClient, gameData, victim, "Looting", "Store");
            ((Player) performingClient).setFancyFrame(ff);
            ((Player) performingClient).setNextAction(new DoNothingAction());
            ((Player) performingClient).refreshClientData();
        }
    }

    @Override
    public boolean doesSetPlayerReady() {
        return false;
    }
}
