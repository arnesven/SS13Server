package model.characters.decorators;

import model.Actor;
import model.GameData;
import model.characters.general.GameCharacter;
import model.items.weapons.Weapon;
import model.map.rooms.Room;

public class PinnedDecorator extends CharacterDecorator implements DisablingDecorator {
    private final Actor attacker;
    private final Room meleeRoom;
    private final Weapon weapon;

    public PinnedDecorator(GameCharacter character, Actor performingClient, Weapon w) {
        super(character, "Pinned");
        attacker = performingClient;
        meleeRoom = performingClient.getPosition();
        this.weapon = w;
    }

    @Override
    public String getPublicName(Actor whosAsking) {
        return super.getPublicName(whosAsking) + " (Pinned)";
    }

    @Override
    public void doAfterMovement(GameData gameData) {
        super.doAfterMovement(gameData);
        Actor defender = getActor();
        if (this.meleeRoom == attacker.getPosition() &&
                this.meleeRoom != defender.getPosition() &&
                !defender.isDead() && !attacker.isDead()) {
            weapon.doAttack(attacker, defender.getAsTarget(), gameData);
            if (defender.isDead()) {
                defender.moveIntoRoom(this.meleeRoom);
            }
        }
        if (defender.getCharacter().checkInstance((GameCharacter gc) -> gc == this)) {
            defender.removeInstance((GameCharacter gc) -> gc == this);
        }
    }
}
