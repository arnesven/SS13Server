package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ElectrifyObjectAction;
import model.actions.general.SensoryLevel;
import model.characters.general.GameCharacter;
import model.characters.general.RobotCharacter;
import model.items.NoSuchThingException;
import model.npcs.robots.AnimatedSlotMachineNPC;
import model.npcs.robots.RobotNPC;
import model.objects.general.EmaggedStasisPod;
import model.objects.general.EmaggedVendingMachine;
import model.objects.general.StasisPod;
import model.objects.consoles.AIConsole;
import model.objects.consoles.Console;
import model.objects.general.ElectricalMachinery;
import model.objects.general.GameObject;
import model.objects.general.SlotMachine;
import model.objects.general.VendingMachine;
import model.programs.BotProgram;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 14/12/16.
 */
public class EMAG extends UniversalKeyCard {

    public EMAG() {
        setName("EMAG");
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("emag", "card.png", 3, this);
    }

    	@Override
	public EMAG clone() {
		return new EMAG();
	}

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        try {
            AIConsole cons = gameData.findObjectOfType(AIConsole.class);
            if (cons.getPosition().equals(cl.getPosition()) && cons.AIIsPlayer()) {
                at.add(new EmagAIAction(cons));
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        for (Actor a : cl.getPosition().getActors()) {
            if (a.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof RobotCharacter)) {
                at.add(new EmagRobotAction(a));
            }
        }

        for (GameObject ob : cl.getPosition().getObjects()) {
            if (ob instanceof StasisPod && ((StasisPod)ob).isVacant() && !(ob instanceof EmaggedStasisPod)) {
                at.add(new EmagStasisPodAction((StasisPod)ob));
            }
            if (ob instanceof SlotMachine) {
                at.add(new EmagSlotMachineAction((SlotMachine)ob));
            }
            if (ob instanceof VendingMachine) {
                at.add(new EmagVendingMachineAction((VendingMachine)ob, cl));
            }
            if (ob instanceof Console) {
                at.add(new ElectrifyObjectAction((ElectricalMachinery)ob));
            }
        }

    }

    private class EmagAIAction extends Action {
        private final AIConsole cons;

        public EmagAIAction(AIConsole cons) {
            super("Emag AI", SensoryLevel.OPERATE_DEVICE);
            this.cons = cons;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "Emagged the AI Console";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (cons.AIIsPlayer()) {
                cons.getLaws().clear();
                performingClient.addTolastTurnInfo("You cleared the AI of laws!");
            }
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }

    private class EmagRobotAction extends Action {
        private final Actor target;

        public EmagRobotAction(Actor a) {
            super("Emag " + a.getPublicName(), SensoryLevel.OPERATE_DEVICE);
            this.target = a;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "Emagged " + target.getPublicName();
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (target instanceof RobotNPC) {
                BotProgram bp = BotProgram.createHostileProgram(gameData);
                bp.loadInto((RobotNPC)target, performingClient);
                performingClient.addTolastTurnInfo("The bot is now hostile.");
            }
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }

    private class EmagStasisPodAction extends Action {
        private final StasisPod pod;

        public EmagStasisPodAction(StasisPod pod) {
            super("Emag Stasis Pod", SensoryLevel.OPERATE_DEVICE);
            this.pod = pod;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "Emagged the Stasis Pod";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            pod.getPosition().removeObject(pod);
            pod.getPosition().addObject(new EmaggedStasisPod(pod.getPosition(), performingClient));
            performingClient.addTolastTurnInfo("The stasis pod is now a trap.");
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }

    private class EmagSlotMachineAction extends Action {
        private final SlotMachine slots;

        public EmagSlotMachineAction(SlotMachine ob) {
            super("Emag Slot Machine", SensoryLevel.OPERATE_DEVICE);
            this.slots = ob;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "Emagged the Slot Machine";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            slots.getPosition().removeObject(slots);
            performingClient.addTolastTurnInfo("The slot machine came to life!");
            gameData.addNPC(new AnimatedSlotMachineNPC(slots.getPosition()));
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }

    private class EmagVendingMachineAction extends Action {
        private final VendingMachine vending;

        public EmagVendingMachineAction(VendingMachine ob, Actor performer) {
            super("Emag " + ob.getPublicName(performer), SensoryLevel.OPERATE_DEVICE);
            this.vending = ob;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "Emagged the " + vending.getPublicName(whosAsking);
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            vending.getPosition().removeObject(vending);
            vending.getPosition().addObject(new EmaggedVendingMachine(vending));
            performingClient.addTolastTurnInfo("The vending machine has a new selection.");
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }
}
