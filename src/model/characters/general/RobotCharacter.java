package model.characters.general;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.itemactions.*;
import model.events.damage.AsphyxiationDamage;
import model.events.damage.ColdDamage;
import model.events.damage.Damager;
import model.events.damage.RadiationDamage;
import model.items.general.FireExtinguisher;
import model.items.general.GameItem;
import model.items.general.RobotParts;
import model.items.general.Tools;
import model.items.weapons.BluntWeapon;
import model.items.weapons.Weapon;

public class RobotCharacter extends GameCharacter {

    public RobotCharacter(String string, int i, double d) {
        super(string, i, d);
        this.setMaxHealth(4.0);
        this.setHealth(4.0);
    }

    @Override
    public String getPublicName() {
        return super.getPublicName();
    }

    @Override
    public boolean wasCriticalHit(Weapon weapon) {
        return false;
    }

    @Override
    public final Sprite getSprite(Actor whosAsking) {
        if (isDead()) {
            return getBrokenSprite(whosAsking);
        }

        return getNormalSprite(whosAsking);
    }

    protected Sprite getBrokenSprite(Actor whosAsking) {
        Sprite normal = getNormalSprite(whosAsking);
        Sprite sp = new Sprite("robotbroken", normal.getMap(),  normal.getColumn(), getActor());
        sp.setRotation(90);
        return sp;
    }

    protected Sprite getNormalSprite(Actor whosAsking) {
        return new Sprite("robot", "robots.png", 51, getActor());
    }

    @Override
    public GameCharacter clone() {
        return new RobotCharacter(getPublicName(), getStartingRoom(), getSpeed());
    }

    @Override
    public List<GameItem> getStartingItems() {
        return new ArrayList<GameItem>();
    }

    @Override
    public void beExposedTo(Actor something, Damager damager) {
        if (damager instanceof ColdDamage || damager instanceof AsphyxiationDamage) {
            return;
        }
        super.beExposedTo(something, damager);
    }

    @Override
    public boolean isHealable() {
        return false;
    }

    public boolean isEncumbered() {
        return getTotalWeight() >= 50.0;
    }

    @Override
    public boolean isCrew() {
        return false;
    }

    @Override
    public Weapon getDefaultWeapon() {
        return Weapon.STEEL_PROD;
    }

    @Override
    public boolean hasInventory() {
        return true;
    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        super.addCharacterSpecificActions(gameData, at);

        if (getActor() instanceof Player) {
            FireExtinguisher fext = new FireExtinguisher();
            fext.addYourActions(gameData, at, (Player) getActor());

            Tools tool = new Tools();
            tool.addYourActions(gameData, at, (Player) getActor());
        }

    }

    @Override
    public boolean isVisibileFromAdjacentRoom() {
        return true;
    }
}
