package model.characters.decorators;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.characters.general.GameCharacter;
import model.events.damage.AsphyxiationDamage;
import model.events.damage.Damager;
import model.events.damage.FireDamage;
import model.events.damage.NoPressureDamage;
import model.items.general.GameItem;
import model.items.suits.Equipment;
import model.items.suits.SuitItem;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 10/09/17.
 */
public class OnFireCharacterDecorator extends CharacterDecorator {


    private SuitItem currentSuit;
    private double END_CHANCE = 0.25;

    public OnFireCharacterDecorator(GameCharacter character) {
        super(character, "On Fire");
        this.currentSuit = character.getEquipment().getEquipmentForSlot(Equipment.TORSO_SLOT);
    }

    @Override
    public String getPublicName() {
        return "Burning " + super.getPublicName();
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (getActor().isDead()) {
            return super.getSprite(whosAsking);
        }

        Sprite s = super.getSprite(whosAsking);

        List<Sprite> list = new ArrayList<>();
        list.add(0, new Sprite("burner", "fire.png", 4, 9, getActor()));
        list.add(0, s);
        list.add(0, new Sprite("getupbase", "human.png", 0, getActor()));
        Sprite res = new Sprite("burning" + s.getName(), list);
        return res;
    }

    @Override
    public void doAtEndOfTurn(GameData gameData) {
        getActor().getCharacter().beExposedTo(null, new FireDamage());

        if (currentSuit != getActor().getCharacter().getEquipment().getEquipmentForSlot(Equipment.TORSO_SLOT)) {
            END_CHANCE *= 2;
            currentSuit = getActor().getCharacter().getEquipment().getEquipmentForSlot(Equipment.TORSO_SLOT);
        }

        if (MyRandom.nextDouble() < END_CHANCE) {
            if (getActor().getCharacter().checkInstance((GameCharacter gc) -> gc == this)) {
                getActor().removeInstance((GameCharacter gc) -> gc == this);
            }
        } else {
            getActor().addTolastTurnInfo("You're on fire!");
        }
    }

    @Override
    public void beExposedTo(Actor something, Damager damager) {
        super.beExposedTo(something, damager);

        if (damager instanceof NoPressureDamage || damager instanceof AsphyxiationDamage) {
            getActor().removeInstance((GameCharacter gc) -> gc == this);
        }
    }
}
