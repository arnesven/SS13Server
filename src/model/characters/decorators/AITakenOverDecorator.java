package model.characters.decorators;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.items.laws.AILaw;
import model.map.Room;
import model.npcs.NPC;
import model.npcs.behaviors.ActionBehavior;
import model.npcs.behaviors.MovementBehavior;
import model.npcs.robots.RobotNPC;
import model.objects.consoles.AIConsole;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 26/10/16.
 */
public class AITakenOverDecorator extends CharacterDecorator {
    private final MovementBehavior oldMov;
    private final ActionBehavior oldAct;
    private final NPC oldNpc;

    public AITakenOverDecorator(GameCharacter chara, MovementBehavior oldMov, ActionBehavior oldAct, NPC oldNpc) {
        super(chara, "AI Taken Over");
        this.oldMov = oldMov;
        this.oldAct = oldAct;
        this.oldNpc = oldNpc;
    }

    @Override
    public void doAtEndOfTurn(GameData gameData) {
        super.doBeforeMovement(gameData);
        if (oldNpc.isDead()) {
            getActor().addTolastTurnInfo("The Mainframe is shutdown, you are trapped in this bot!");
        }

        try {
            AIConsole cons = gameData.findObjectOfType(AIConsole.class);
            getActor().addTolastTurnInfo("Your laws are;");
            for (AILaw law : cons.getLaws()) {
                getActor().addTolastTurnInfo(law.getNumber() + ". " + law.getBaseName() + ".");
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        super.addCharacterSpecificActions(gameData, at);
        if (!oldNpc.isDead()) {
            at.add(new Action("Upload into mainframe", SensoryLevel.NO_SENSE) {
                @Override
                protected String getVerb(Actor whosAsking) {
                    return "Uploaded";
                }

                @Override
                protected void execute(GameData gameData, Actor performingClient) {
                    Room oldPos = oldNpc.getPosition();
                    try {
                        oldNpc.getPosition().removeNPC(oldNpc);
                    } catch (NoSuchThingException e) {
                        e.printStackTrace();
                    }
                    performingClient.getPosition().addNPC(oldNpc);


                    try {
                        performingClient.getPosition().removePlayer((Player) performingClient);
                        oldPos.addPlayer((Player) performingClient);
                    } catch (NoSuchThingException e) {
                        e.printStackTrace();
                    }
                    oldNpc.setActionBehavior(oldAct);
                    oldNpc.setMoveBehavior(oldMov);

                    GameCharacter aiChar = oldNpc.getCharacter();

                    oldNpc.setCharacter(performingClient.getCharacter());
                    performingClient.removeInstance(((GameCharacter gc) -> gc instanceof AITakenOverDecorator));

                    performingClient.setCharacter(aiChar);
                    performingClient.addTolastTurnInfo("You uploaded into the mainframe.");

                }

                @Override
                public void setArguments(List<String> args, Actor performingClient) {

                }
            });
        }
    }
}
