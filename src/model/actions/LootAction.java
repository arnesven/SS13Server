package model.actions;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

public class LootAction extends TargetingAction {
    private boolean lootall = false;

    public LootAction(Actor actor) {
        super("Loot", SensoryLevel.PHYSICAL_ACTIVITY, actor);
    }

    @Override
    protected void applyTargetingAction(GameData gameData, Actor performingClient, Target target, GameItem item) {
        performingClient.addTolastTurnInfo("You looted a " + item.getFullName(performingClient) + " from " + target.getName());
        performingClient.getCharacter().giveItem(item, target);
        target.getItems().remove(item);
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (!lootall) {
            performingClient.addTolastTurnInfo("You looted " + target.getName());
            super.execute(gameData, performingClient);
        } else {
            List<GameItem> itemsToRemove = new ArrayList<>();
            itemsToRemove.addAll(target.getItems());
            for (GameItem it : itemsToRemove) {
                applyTargetingAction(gameData, performingClient, target, it);
            }
        }
    }

    @Override
    public String getDescription(Actor whosAsking) {
        if (target == null) {
            return super.getDescription(whosAsking);
        }

        if (lootall) {
            return super.getDescription(whosAsking) + " " + target.getName();
        }

        return super.getDescription(whosAsking) + (item!=null?(" a " + item.getPublicName(whosAsking)):"") + " from " + target.getName();
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = new ActionOption(getName());
        for (Target t : getTargets()) {
           Actor a = (Actor)t;
           ActionOption opts2 = new ActionOption(a.getPublicName());
           for (GameItem it : a.getItems()) {
               opts2.addOption(it.getPublicName(whosAsking));
           }
           if (opts2.numberOfSuboptions() > 0) {
               opts2.addOption("All Items");
               opts.addOption(opts2);
           }
        }
        return opts;
    }

    @Override
    public boolean isViableForThisAction(Target target2) {
        if (!(target2 instanceof Actor)) {
            return false;
        }
        Actor actorTarget = (Actor)target2;
        return actorTarget != performer && (actorTarget.isDead() || !actorTarget.getsActions());
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "looted";
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        try {
            this.target = findTarget(args.get(0));
        } catch (NoSuchThingException e) {
            Logger.log(Logger.CRITICAL, "What, target wasn't there?");
            return;
        }

       if (args.get(1).equals("All Items")) {
            this.lootall = true;
       } else {

           if (args.size() > 1) {
               try {
                   this.item = findItem(args.get(1), (Actor) this.target);
               } catch (NoSuchThingException e) {
                   Logger.log(Logger.CRITICAL, "What, item wasn't there?");
                   return;
               }
           }
       }
        if (args.size() > 2) {
            otherArgs = args.get(2);
        }
    }

    protected GameItem findItem(String itemName, Actor victim) throws NoSuchThingException {
        for (GameItem it : victim.getItems()) {
            if (it.getPublicName(performer).equals(itemName)) {
                return it;
            }
        }
        throw new NoSuchThingException("Did not find item '" + itemName + "' in " + victim.getPublicName() + "'s inventory...");
    }

    protected boolean itemAvailable(Actor performingClient, GameItem it) {
        return target.getItems().contains(it);
    }


}
