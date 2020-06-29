package model.npcs.behaviors;

import model.Actor;
import model.characters.general.AnimalCharacter;
import model.characters.general.ChangelingCharacter;
import model.characters.general.GameCharacter;
import model.characters.general.ParasiteCharacter;
import model.characters.special.AlienCharacter;

import java.util.List;

public class ParasiteAttackBehavior extends AttackAllActorsButNotTheseClasses {
    public ParasiteAttackBehavior() {
        super(List.of(ParasiteCharacter.class, ChangelingCharacter.class,
                AlienCharacter.class, AnimalCharacter.class));
    }

    @Override
    protected boolean shouldAvoidTarget(Actor targetAsActor) {
        return super.shouldAvoidTarget(targetAsActor) || targetAsActor.isInfected();
    }
}
