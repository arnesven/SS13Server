package model.actions.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.events.damage.ElectricalDamage;
import model.objects.general.ElectricalMachinery;
import model.objects.general.GameObject;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 17/12/16.
 */
public class ElectrifyObjectAction extends Action {
    private final ElectricalMachinery ob;
    private Actor overCharger = null;

    public ElectrifyObjectAction(ElectricalMachinery ob) {
        super("Overcharge " + ob.getBaseName(), SensoryLevel.OPERATE_DEVICE);
        this.ob = ob;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Overcharged the " + ob.getPublicName(whosAsking);
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        ob.getPosition().removeObject(ob);
        ob.getPosition().addObject(new OverchargedElectricalMachinery(ob));
        performingClient.addTolastTurnInfo("You overcharged the " + ob.getPublicName(performingClient) + ".");
        this.overCharger = performingClient;
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }

    private class OverchargedElectricalMachinery extends GameObject {
        private final ElectricalMachinery innerObject;

        public OverchargedElectricalMachinery(ElectricalMachinery ob) {
            super(ob.getBaseName(), ob.getPosition());
            this.innerObject = ob;
        }

        @Override
        public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
            ArrayList<Action> acts = new ArrayList<>();
            innerObject.addSpecificActionsFor(gameData, cl, acts);
            for (Action a : acts) {
                at.add(new OverchargeWrapper(a, this));
            }
        }

        @Override
        public Sprite getSprite(Player whosAsking) {
            return innerObject.getSprite(whosAsking);
        }

        public void revert() {
            innerObject.getPosition().removeObject(this);
            innerObject.getPosition().addObject(innerObject);
        }
    }

    private class OverchargeWrapper extends Action {
        private final Action innerAction;
        private final OverchargedElectricalMachinery overcharge;
        private boolean chargeLeft;

        public OverchargeWrapper(Action a, OverchargedElectricalMachinery overchargedElectricalMachinery) {
            super(a.getName(), a.getSense());
            innerAction = a;
            this.chargeLeft = true;
            this.overcharge = overchargedElectricalMachinery;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return innerAction.getVerb(whosAsking);
        }

        @Override
        public ActionOption getOptions(GameData gameData, Actor whosAsking) {
            return innerAction.getOptions(gameData, whosAsking);
        }

        @Override
        public void doTheAction(GameData gameData, Actor performingClient) {
            innerAction.doTheAction(gameData, performingClient);
            if (chargeLeft) {
                performingClient.getAsTarget().beExposedTo(overCharger, new ElectricalDamage(1.0));
                chargeLeft = false;
                overcharge.revert();
            }
        }

        @Override
        public String getDescription(Actor whosAsking) {
            return innerAction.getDescription(whosAsking);
        }

        @Override
        public String getDistantDescription(Actor whosAsking) {
            return innerAction.getDescription(whosAsking);
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            Logger.log(Logger.CRITICAL, "Should not have been called!");
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {
            innerAction.setArguments(args, performingClient);
        }
    }
}
