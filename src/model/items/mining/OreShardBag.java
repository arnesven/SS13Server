package model.items.mining;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 17/09/17.
 */
public class OreShardBag extends GameItem {

    private List<OreShard> shards = new ArrayList<>();


    public OreShardBag() {
        super("Ore Shard Bag", 0.1, false, 20);
    }

    @Override
    public String getFullName(Actor whosAsking) {
        return super.getFullName(whosAsking) + " (" + shards.size() + ")";
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("oreshardbag", "storage.png", 0, this);
    }

    @Override
    public GameItem clone() {
        return new OreShardBag();
    }

    public void addShard(OreShard os) {
        shards.add(os);
    }

    @Override
    public double getWeight() {
        return shards.size()*0.2 + 0.1;
    }

    @Override
    public String getExtraDescriptionStats(GameData gameData, Player performingClient) {
        return "<b>Shards: </b>" + shards.size();
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A bag for storing ore shards.";
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        Action a = new PutIntoBagAction();
        if (a.getOptions(gameData, cl).numberOfSuboptions() > 1) {
            at.add(a);
        }
    }

    public List<OreShard> getShards() {
        return shards;
    }

    private class PutIntoBagAction extends Action {
        private OreShard selected;
        private boolean all = false;

        public PutIntoBagAction() {
            super("Put Into Bag", SensoryLevel.PHYSICAL_ACTIVITY);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "put something into bag";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (all) {
                for (GameItem it : performingClient.getPosition().getItems()) {
                    if (it instanceof OreShard) {
                        OreShardBag.this.addShard((OreShard) it);
                    }
                }
                performingClient.getPosition().getItems().removeIf((GameItem it) -> it instanceof OreShard);
                performingClient.addTolastTurnInfo("You put ore shards into your bag.");
            } else if (selected != null) {
                if (performingClient.getPosition().getItems().contains(selected)) {
                    transferIntoBag(selected, performingClient);

                    performingClient.addTolastTurnInfo("You put the ore shard into your bag.");
                } else {
                    performingClient.addTolastTurnInfo("What, the ore shard is gone? " + Action.FAILED_STRING);
                }
            }
        }

        private void transferIntoBag(OreShard selected, Actor performingClient) {
            performingClient.getPosition().getItems().remove(selected);
            OreShardBag.this.addShard(selected);
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {
            if (args.get(0).contains("All")) {
                all = true;
                return;
            }
            for (GameItem it : performingClient.getPosition().getItems()) {
                if (args.get(0).equals(it.getPublicName(performingClient))) {
                    selected = (OreShard)it;
                }
            }
        }

        @Override
        public ActionOption getOptions(GameData gameData, Actor whosAsking) {
            ActionOption opt = super.getOptions(gameData, whosAsking);

            opt.addOption(new ActionOption("All Shards"));
            for (GameItem it : whosAsking.getPosition().getItems()) {
                if (it instanceof OreShard) {
                    opt.addOption(it.getPublicName(whosAsking));
                }
            }

            return opt;
        }
    }
}
