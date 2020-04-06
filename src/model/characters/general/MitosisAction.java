package model.characters.general;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.behaviors.ActionBehavior;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.behaviors.MovementBehavior;

import java.util.List;

/**
 * Created by erini02 on 20/10/16.
 */
public class MitosisAction extends Action {
    /**

     */
    public MitosisAction() {
        super("Mitosis", SensoryLevel.PHYSICAL_ACTIVITY);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "split into two!";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        ActionBehavior ab = new DoNothingBehavior();
        MovementBehavior mov = new MeanderingMovement(0.5);
        GameCharacter chara = performingClient.getCharacter().clone();
        if (performingClient instanceof NPC) {
            ab = ((NPC) performingClient).getActionBehavior();
            mov = ((NPC) performingClient).getMovementBehavior();
        }
        NPC npc = new MitosisNPC(chara, mov, ab, performingClient.getPosition());
        gameData.addNPC(npc);
        npc.getCharacter().getEquipment().removeEverything();
        npc.getCharacter().setEquipment(performingClient.getCharacter().getEquipment().copyAll(npc.getCharacter()));
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {

    }

    private class MitosisNPC extends NPC {
        public MitosisNPC(GameCharacter chara, MovementBehavior mov, ActionBehavior ab, Room position) {
            super(chara, mov, ab, position);
        }

        @Override
        public boolean hasInventory() {
            return true;
        }

        @Override
        public NPC clone() {
            return new MitosisNPC(getCharacter().clone(), getMovementBehavior(), getActionBehavior(), getPosition());
        }
    }
}
