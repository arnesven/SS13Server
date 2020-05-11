package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.mining.OreShard;
import model.items.mining.OreShardBag;
import model.items.mining.MiningDrill;
import model.objects.mining.RockObject;
import util.MyRandom;

import java.util.List;

/**
 * Created by erini02 on 17/09/17.
 */
public class DrillIntoRockAction extends Action {
    private final RockObject target;

    public DrillIntoRockAction(RockObject rockObject) {
        super("Drill into " + rockObject.getBaseName(), SensoryLevel.PHYSICAL_ACTIVITY);
        this.target = rockObject;

    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "drilled into the rock";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (GameItem.hasAnItemOfClass(performingClient, MiningDrill.class)) {
            if (MyRandom.nextDouble() < 0.4) {
                List<OreShard> list = target.shatter();
                performingClient.addTolastTurnInfo("You drilled into the rock - it shattered!");
                for (OreShard s : list) {
                    performingClient.getPosition().addItem(s);
                }
            } else {
                OreShard os = target.splitOff();
                try {
                    OreShardBag osb = GameItem.getItemFromActor(performingClient, new OreShardBag());
                    osb.addShard(os);
                    performingClient.addTolastTurnInfo("An ore shard split off from the rock, you put it in your bag.");
                } catch (NoSuchThingException e) {
                    target.getPosition().addItem(os);
                    performingClient.addTolastTurnInfo("An ore shard split off from the rock.");
                }

            }
        } else {
            performingClient.addTolastTurnInfo("What, the mining drill is gone? " + failed(gameData, performingClient));
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }
}
