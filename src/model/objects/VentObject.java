package model.objects;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.MoveAction;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.decorators.SeeAirDuctsDecorator;
import model.characters.general.GameCharacter;
import model.characters.special.AlienCharacter;
import model.events.Event;
import model.items.general.GameItem;
import model.items.general.Tools;
import model.map.GameMap;
import model.map.rooms.AirDuctRoom;
import model.map.rooms.Room;
import model.objects.general.GameObject;
import sounds.Sound;

import java.util.ArrayList;
import java.util.List;

public class VentObject extends GameObject {
    private final Room toRoom;
    private VentObject buddy;
    private boolean isOpen;

    public VentObject(Room here, Room to, VentObject otherSide) {
        super("Air Vent", here);
        this.toRoom = to;
        this.isOpen = false;
        this.buddy = otherSide;
    }

    public Room getToRoom() {
        return toRoom;
    }

    private void setIsOpen(boolean b) {
        isOpen = b;
    }

    private VentObject getOtherSide() {
        return buddy;
    }


    public void setOtherSide(VentObject vent2) {
        this.buddy = vent2;
    }


    @Override
    public Sprite getSprite(Player whosAsking) {
        if (isOpen) {
            return new Sprite("airventopen", "power.png", 11, 12, this);
        }
        return new Sprite("airventshut", "power.png", 10, 12, this);
    }

    @Override
    public String getPublicName(Actor whosAsking) {
        if (whosAsking.getCharacter().getSize() == GameCharacter.SMALL_SIZE || isOpen) {
            return super.getPublicName(whosAsking) + " to " + toRoom.getName();
        }
        return super.getPublicName(whosAsking);
    }

    private boolean canOpenVent(Actor performingClient) {
        return GameItem.hasAnItemOfClass(performingClient, Tools.class) ||
                performingClient.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof AlienCharacter);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (cl.getCharacter().getSize() == GameCharacter.SMALL_SIZE || isOpen) {
            at.add(new VentMoveAction(gameData, cl));
        }
        if (canOpenVent(cl)) {
            if (!isOpen) {
                at.add(new OpenVentAction(this));
            } else {
                at.add(new CloseVentAction(this));
            }
        }
        if (!isOpen && cl.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof AlienCharacter)) {
            at.add(new OpenAndMoveThroughAction(this));
        }
    }

    public boolean isOpen() {
        return isOpen;
    }

    private class VentMoveAction extends MoveAction {

        boolean intoVent;

        public VentMoveAction(GameData gameData, Actor actor) {
            super(gameData, actor);
            String prep = " out to ";
            intoVent = false;
            if (toRoom instanceof AirDuctRoom) {
                prep = " into ";
                intoVent = true;
            }
            setName("Crawl" + prep + toRoom.getName());
            setDestination(toRoom);
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            super.execute(gameData, performingClient);
            if (intoVent) {
                performingClient.setCharacter(new SeeAirDuctsDecorator(VentObject.this, performingClient.getCharacter()));
            } else {
                gameData.addMovementEvent(new Event() {
                    @Override
                    public void apply(GameData gameData) {
                        if (!(performingClient.getPosition() instanceof AirDuctRoom)) {
                            performingClient.removeInstance((GameCharacter gc) -> gc instanceof SeeAirDuctsDecorator);
                        }
                    }

                    @Override
                    public String howYouAppear(Actor performingClient) {
                        return null;
                    }

                    @Override
                    public SensoryLevel getSense() {
                        return null;
                    }

                    @Override
                    public boolean shouldBeRemoved(GameData gameData) {
                        return true;
                    }
                });
            }
        }

        @Override
        public ActionOption getOptions(GameData gameData, Actor whosAsking) {
            ActionOption opts = new ActionOption(getName());
            return opts;
        }


    }

    private class OpenVentAction extends Action {

        private final VentObject vent;

        public OpenVentAction(VentObject ventObject) {
            super("Open Air Vent", SensoryLevel.PHYSICAL_ACTIVITY);
            this.vent = ventObject;
        }

        @Override
        public boolean hasRealSound() {
            return true;
        }

        @Override
        public Sound getRealSound() {
            return  new Sound("screwdriver");
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "open the air vent";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (canOpenVent(performingClient)) {
                performingClient.addTolastTurnInfo("You opened the air vent. Wow, it's filthy in there.");
                vent.setIsOpen(true);
                getOtherSide().setIsOpen(true);
                GameMap.joinRooms(getPosition(), getOtherSide().getPosition());
            } else {
                performingClient.addTolastTurnInfo("What no tools? " + failed(gameData, performingClient));
            }
        }


        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }

    private class CloseVentAction extends Action {
        private final VentObject vent;

        public CloseVentAction(VentObject ventObject) {
            super("Close Air Vent", SensoryLevel.PHYSICAL_ACTIVITY);
            this.vent = ventObject;
        }


        @Override
        public boolean hasRealSound() {
            return true;
        }

        @Override
        public Sound getRealSound() {
            return  new Sound("screwdriver");
        }


        @Override
        protected String getVerb(Actor whosAsking) {
            return "closed the air vent";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (canOpenVent(performingClient)) {
                performingClient.addTolastTurnInfo("You closed the air vent. We don't want anyone crawling in there.");
                vent.setIsOpen(false);
                getOtherSide().setIsOpen(false);
                GameMap.separateRooms(getPosition(), getOtherSide().getPosition());
            } else {
                performingClient.addTolastTurnInfo("What no tools? " + failed(gameData, performingClient));
            }
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }

    private class OpenAndMoveThroughAction extends OpenVentAction {
        public OpenAndMoveThroughAction(VentObject ventObject) {
            super(ventObject);
            setName(super.getName() + " and Move Through");
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            super.execute(gameData, performingClient);
            VentMoveAction vma = new VentMoveAction(gameData, performingClient);
            vma.doTheAction(gameData, performingClient);
        }
    }
}
