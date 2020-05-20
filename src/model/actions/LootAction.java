package model.actions;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.characters.decorators.DisablingDecorator;
import model.characters.decorators.HandCuffedDecorator;
import model.characters.decorators.PinnedDecorator;
import model.characters.decorators.StunnedDecorator;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.suits.SuitItem;
import model.objects.general.BreakableObject;
import org.junit.rules.DisableOnDebug;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

public class LootAction extends TargetingAction {
    private boolean lootall = false;

    public LootAction(Actor actor) {
        super("Loot", SensoryLevel.PHYSICAL_ACTIVITY, actor);
    }


    protected String getLootVerb() {
        return "looted";
    }

    @Override
    protected void applyTargetingAction(GameData gameData, Actor performingClient, Target target, GameItem item) {
        if (target.getItems().contains(item)) {
            performingClient.addTolastTurnInfo("You " + getLootVerb() + " a " + item.getFullName(performingClient) + " from " + target.getName());
            performingClient.getCharacter().giveItem(item, target);
            target.getItems().remove(item);
        } else if (item != null) {
            Actor targetAsActor = (Actor)target;
            SuitItem s = (SuitItem)item;
            s.removeYourself(targetAsActor.getCharacter().getEquipment());
            performingClient.getCharacter().giveItem(s, target);
            performingClient.addTolastTurnInfo("You " + getLootVerb() + " a " + item.getFullName(performingClient) + " from " + target.getName());
        }
    }


    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (!lootall) {
            super.execute(gameData, performingClient);
        } else {
            List<GameItem> itemsToRemove = new ArrayList<>();
            itemsToRemove.addAll(target.getItems());
            for (GameItem it : itemsToRemove) {
                applyTargetingAction(gameData, performingClient, target, it);
            }
            List<SuitItem> suitsToRemove = new ArrayList<>();
            suitsToRemove.addAll(((Actor)target).getCharacter().getEquipment().getTopEquipmentAsList());
            for (SuitItem it : suitsToRemove) {
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
            return getPerformer().getPublicName(whosAsking) + " " + getVerb(whosAsking) + " " + target.getName();
        }

        return getPerformer().getPublicName(whosAsking) + " " + getVerb(whosAsking) +
                (item!=null?(" a " + item.getPublicName(whosAsking)):"") + " from " + target.getName();
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = new ActionOption(getName());
        for (Target t : getTargets()) {
           ActionOption opts2 = new ActionOption(t.getName());
           for (GameItem it : t.getItems()) {
               opts2.addOption(it.getPublicName(whosAsking));
           }
           if (t instanceof Actor) {
               Actor a = (Actor) t;
               if (a.isDead() || !a.getsActions()) {
                   for (SuitItem s : a.getCharacter().getEquipment().getTopEquipmentAsList()) {
                       opts2.addOption(s.getPublicName(whosAsking));
                   }
               }
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
        if (target2 instanceof BreakableObject) {
            return ((BreakableObject)target2).isLootable();
        }
        if (!(target2 instanceof Actor)) {
            return false;
        }
        Actor actorTarget = (Actor)target2;
        if (actorTarget == performer) {
            return false;
        }
        return actorTarget.isDead() || !actorTarget.getsActions() ||
                actorTarget.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof DisablingDecorator);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return getLootVerb();
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
                   Logger.log(Logger.CRITICAL, "What, item wasn't there? " + e.getMessage());
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
        for (SuitItem it : victim.getCharacter().getEquipment().getTopEquipmentAsList()) {
            if (it.getPublicName(performer).equals(itemName)) {
                return it;
            }
        }
        throw new NoSuchThingException("Did not find item '" + itemName + "' in " + victim.getPublicName() + "'s inventory...");
    }

    protected boolean itemAvailable(Actor performingClient, GameItem it) {
        return target.getItems().contains(it) || ((Actor)target).getCharacter().getEquipment().getTopEquipmentAsList().contains(it);
    }



}
