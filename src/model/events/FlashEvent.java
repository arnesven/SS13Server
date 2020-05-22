package model.events;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.characters.decorators.FlashedForOneRoundDecorator;
import model.items.suits.Equipment;
import model.items.suits.SecOffsHelmet;
import model.items.suits.SuitItem;
import model.items.suits.SunGlasses;
import model.map.rooms.Room;

public class FlashEvent extends Event {
    private final int roundSet;
    private final Room position;
    private Sprite flashSprite = new Sprite("flasheventfill", "whiteflash.png",
            2, 0, 32, 32, this);

    public FlashEvent(GameData gameData, Room room) {
        this.roundSet = gameData.getRound();
        this.position = room;
    }

    @Override
    public void apply(GameData gameData) {
        if (gameData.getRound() == roundSet) {
            position.addEffect(flashSprite);
            for (Actor a : position.getActors()) {
                SuitItem headGear = a.getCharacter().getEquipment().getEquipmentForSlot(Equipment.HEAD_SLOT);
                if (headGear instanceof SunGlasses || headGear instanceof SecOffsHelmet) {
                    a.addTolastTurnInfo("Your headgear protects your eyes from the bright flash.");
                } else {
                    a.addTolastTurnInfo("Aaah your eyes! They hurt from a brilliant flash.");
                    a.setCharacter(new FlashedForOneRoundDecorator(a.getCharacter(), gameData.getRound()));
                }
            }
        } else {
            position.getEffects().remove(flashSprite);
        }
    }

    @Override
    public boolean shouldBeRemoved(GameData gameData) {
        return gameData.getRound() > roundSet;
    }

    @Override
    public boolean showSpriteInRoom() {
        return true;
    }

    @Override
    public boolean showSpriteInTopPanel() {
        return false;
    }

    @Override
    public String howYouAppear(Actor performingClient) {
        return "Flash!";
    }

    @Override
    public SensoryLevel getSense() {
        return new SensoryLevel(SensoryLevel.VisualLevel.CLEARLY_VISIBLE,
                SensoryLevel.AudioLevel.INAUDIBLE, SensoryLevel.OlfactoryLevel.UNSMELLABLE);
    }
}
