package model.characters.decorators;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.items.laws.AILaw;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.behaviors.ActionBehavior;
import model.npcs.behaviors.MovementBehavior;
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

                    // TODO: some bug here... the switcheroo is not really working right...
                    try {
                        performingClient.getPosition().removeActor(performingClient);
                        oldPos.addActor(performingClient);
                    } catch (NoSuchThingException e) {
                        e.printStackTrace();
                    }

                    GameCharacter aiChar = oldNpc.getCharacter();
                    performingClient.removeInstance(((GameCharacter gc) -> gc == AITakenOverDecorator.this));

                    oldNpc.setActionBehavior(oldAct);
                    oldNpc.setMoveBehavior(oldMov);
                    oldNpc.setCharacter(performingClient.getCharacter());

                    performingClient.setCharacter(aiChar);
                    performingClient.addTolastTurnInfo("You uploaded into the mainframe.");

                }

                @Override
                public void setArguments(List<String> args, Actor performingClient) {

                }

                @Override
                public Sprite getAbilitySprite() {
                    return new Sprite("uploadability", "interface_robot.png", 2, 7, null);
                }
            });
        }
    }
}
