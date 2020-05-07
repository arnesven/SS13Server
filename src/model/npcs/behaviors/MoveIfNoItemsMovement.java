package model.npcs.behaviors;

import model.npcs.NPC;

public class MoveIfNoItemsMovement extends MeanderingMovement {

    public MoveIfNoItemsMovement() {
        super(0.9);
    }

    @Override
    public void move(NPC npc) {
        if (npc.getPosition().getItems().isEmpty()) {
            super.move(npc);
        }
    }
}
