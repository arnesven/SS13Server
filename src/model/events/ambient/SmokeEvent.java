package model.events.ambient;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.characters.decorators.PoisonedDecorator;
import model.characters.decorators.SmokeDegradedVisionDecorator;
import model.characters.general.GameCharacter;
import model.events.Event;
import model.events.animation.AnimatedSprite;
import model.events.damage.SmokeInhalationDamage;
import model.map.rooms.Room;
import util.HTMLText;
import util.MyRandom;

public class SmokeEvent extends Event {

    private static final double SPREAD_CHANCE_PER_ROOM_AND_TURN = 0.2;
    private ElectricalFire sourceFire;
    private final Room position;
    private boolean isDone;
    private double SMOKE_POISON_CHANCE = 0.02; // 2% chance per turn to be poisoned by smoke

    public SmokeEvent(ElectricalFire ef, Room position) {
        this.sourceFire = ef;
        isDone = false;
        this.position = position;
    }

    public SmokeEvent(ElectricalFire ef) {
        this(ef, ef.getRoom());
    }


        @Override
    public void apply(GameData gameData) {
        if (sourceFire.getRoom() != position) {
            for (Event e : position.getEvents()) {
                if (e instanceof ElectricalFire) {
                    sourceFire = (ElectricalFire)e; // This smoke event is now connected to the fire in this room.
                }
            }
        }

        if (sourceFire.isFixed()) {
            if (MyRandom.nextDouble() < 0.5) {
                position.removeEvent(this);
                isDone = true;
            }
        } else {
            for (Room n : position.getNeighborList()) {
                if (!roomAlreadyHasSmoke(n) && MyRandom.nextDouble() < SPREAD_CHANCE_PER_ROOM_AND_TURN) {
                    SmokeEvent ev = new SmokeEvent(sourceFire, n);
                    gameData.addEvent(ev);
                    n.getEvents().add(ev);
                }
            }


            affectActorsInRoom(gameData);

        }
    }

    private void affectActorsInRoom(GameData gameData) {

        for (Actor a : position.getActors()) {
            if (!a.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof SmokeDegradedVisionDecorator)) {
                a.setCharacter(new SmokeDegradedVisionDecorator(a.getCharacter()));
            }

            double hpBefore = a.getCharacter().getHealth();
            a.getAsTarget().beExposedTo(null, new SmokeInhalationDamage(), gameData);
            if (MyRandom.nextDouble() < SMOKE_POISON_CHANCE && hpBefore > a.getCharacter().getHealth() &&
                    !a.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof PoisonedDecorator)) {
                a.addTolastTurnInfo(HTMLText.makeText("red", "You've been poisoned by noxious fumes!"));
                a.setCharacter(new PoisonedDecorator(a.getCharacter(), null));
            }

        }


    }

    public static boolean roomAlreadyHasSmoke(Room n) {
        for (Event e : n.getEvents()) {
            if (e instanceof SmokeEvent) {
                return true;
            }
        }
        return false;
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
    public boolean shouldBeRemoved(GameData gameData) {
        return isDone;
    }

    @Override
    public String howYouAppear(Actor performingClient) {
        return "Smoke";
    }

    @Override
    public SensoryLevel getSense() {
        return new SensoryLevel(SensoryLevel.VisualLevel.CLEARLY_VISIBLE, SensoryLevel.AudioLevel.INAUDIBLE, SensoryLevel.OlfactoryLevel.SHARP);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (!sourceFire.isRaging() && this.position == sourceFire.getRoom()) {
            return new AnimatedSprite("smokefillwholeroom", "effects.png", 8, 2, 32, 32, this, 16, true);
        }
        return new AnimatedSprite("smoke", "effects.png", 8, 2, 32, 32, this, 16, true);
    }

}
