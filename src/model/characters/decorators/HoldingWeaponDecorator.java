package model.characters.decorators;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.weapons.LaserSword;
import model.items.weapons.Weapon;

import java.util.ArrayList;
import java.util.List;

public class HoldingWeaponDecorator extends CharacterDecorator {
    private final Weapon weapon;

    public HoldingWeaponDecorator(GameCharacter gc, Weapon w) {
              super(gc, "Laser sword holder");
              this.weapon = w;
        }

        @Override
        public Sprite getSprite(Actor whosAsking) {
            if (whosAsking.getItems().contains(weapon)) {
                List<Sprite> look = new ArrayList<>();
                look.add(super.getSprite(whosAsking));
                look.add(weapon.getHandHeldSprite());
                return new Sprite(super.getSprite(whosAsking).getName() + "holding" + weapon.getBaseName().toLowerCase(),
                        "human.png", 0, look, whosAsking);
            }

            return super.getSprite(whosAsking);
        }


    @Override
    public Sprite getMugshotSprite(GameData gameData, Actor player) {
        return getSprite(player);
    }
}
