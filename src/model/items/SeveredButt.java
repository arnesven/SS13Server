package model.items;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;
import model.items.suits.Equipment;
import model.items.suits.Wearable;

import java.util.ArrayList;
import java.util.List;

public class SeveredButt extends BodyPart implements Wearable  {
    public SeveredButt(Actor belong) {
        super(belong.getPublicName() + "'s Butt", 3.0, belong);
    }

    @Override
    public GameItem clone() {
        return new SeveredButt(belongsTo);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("severedbutt", "body_parts.png", 4, 0, this);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A person's severed buttocks.";
    }

    @Override
    public void putYourselfOn(Equipment eq) {

    }

    @Override
    public void removeYourself(Equipment eq) {

    }

    @Override
    public boolean canBeWornBy(Actor actor) {
        return actor.getCharacter().getEquipment().getEquipmentForSlot(Equipment.HEAD_SLOT).permitsOver();
    }

    @Override
    public String getEatString() {
        return "calamari";
    }

    @Override
    public void beingPutOn(Actor actionPerformer) {
        actionPerformer.setCharacter(new WearingAButtDecorator(actionPerformer.getCharacter(), "Wearing A Butt"));
    }

    @Override
    public void beingTakenOff(Actor actionPerformer) {
        actionPerformer.removeInstance((GameCharacter cd) -> cd instanceof WearingAButtDecorator);
    }

    @Override
    public boolean permitsOver() {
        return false;
    }

    private class WearingAButtDecorator extends CharacterDecorator {

        public WearingAButtDecorator(GameCharacter chara, String name) {
            super(chara, name);
        }

        @Override
        public Sprite getSprite(Actor whosAsking) {
            Sprite sp = getInner().getSprite(whosAsking);
            List<Sprite> sprs = new ArrayList<>();
            sprs.add(sp);
            sprs.add(new Sprite(sp.getName() + "butt", "body_parts.png",  8, 1, getActor()));
            return new Sprite(sp.getName() + "withbuttonhead", "human.png", 0, sprs, getActor());
        }
    }
}
