package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.items.general.BombItem;
import model.items.general.GameItem;
import model.items.general.Tools;
import model.items.tools.CraftingTools;
import model.items.tools.RepairTools;
import util.MyRandom;

import java.util.List;

/**
 * Created by erini02 on 02/05/16.
 */
public class DefuseBombAction extends Action {
    private static final double DETONATION_CHANCE = 0.1;
    private final BombItem bomb;


    public DefuseBombAction(BombItem bomb) {
        super("Defuse Bomb", SensoryLevel.OPERATE_DEVICE);
        this.bomb = bomb;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Tinkered with bomb";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (!GameItem.hasAnItemOfClass(performingClient, Tools.class)) {
            performingClient.addTolastTurnInfo("What? The Tools are missing!" + failed(gameData, performingClient));
            return;
        }

        if (bomb.isExploded()) {
            performingClient.addTolastTurnInfo("The bomb already exploded! " + failed(gameData, performingClient));
        } else if (bomb.isDefused()) {
            performingClient.addTolastTurnInfo("The bomb has already been defused! " + failed(gameData, performingClient));
        } else {
            if (!bomb.getDefusal().isDefused()) {
                performingClient.addTolastTurnInfo("You failed to defuse the bomb!");
                bomb.explode(gameData, performingClient);
            } else {
                performingClient.addTolastTurnInfo("You successfully defused the bomb.");
                bomb.defuse(gameData);
                performingClient.getPosition().getItems().remove(bomb);
            }
            try {
                RepairTools rt = GameItem.getItemFromActor(performingClient, new RepairTools());
                rt.makeHoldInHand(performingClient);
            } catch (NoSuchThingException e) {
                CraftingTools ct = null;
                ct.makeHoldInHand(performingClient);
                try {
                    ct = GameItem.getItemFromActor(performingClient, new CraftingTools());
                } catch (NoSuchThingException e1) {

                }
            }
            return;
        }

    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }

    @Override
    public boolean doesCommitThePlayer() {
        return true;
    }
}
