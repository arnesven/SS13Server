package model.npcs;

import model.Actor;
import model.GameData;
import model.actions.characteractions.HatchAction;
import model.characters.general.ParasiteCharacter;
import model.characters.special.AlienCharacter;
import model.map.rooms.Room;
import model.npcs.behaviors.ActionBehavior;
import model.npcs.behaviors.AttackAllActorsButNotTheseClasses;
import model.npcs.behaviors.MeanderingMovement;

import java.util.List;

public class AlienNPC extends NPC {
    public AlienNPC(Room position) {
        super(new AlienCharacter(position), new MeanderingMovement(0.0),
                new AlienBehavior(),
                position);
    }

    @Override
    public NPC clone() {
        return new AlienNPC(getPosition());
    }

    private static class AlienBehavior implements ActionBehavior {

        @Override
        public void act(Actor npc, GameData gameData) {
            HatchAction ha = new HatchAction();
            ha.doTheAction(gameData, npc);
            ((NPC)npc).setActionBehavior(new AttackAllActorsButNotTheseClasses(List.of(AlienCharacter.class, ParasiteCharacter.class)));
            ((NPC)npc).setMoveBehavior(new MeanderingMovement(0.75));
        }
    }
}
