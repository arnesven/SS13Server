package graphics.sprites;

import graphics.ClientInfo;
import graphics.OverlaySprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.map.rooms.Room;
import model.objects.general.GameObject;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

public class SpaceVision extends NormalVision {

    private static final double minDY = -10;
    private static final double minDX = -10;
    private static final double maxDY = 10;
    private static final double maxDX = 10;
    private static final double RANGE_SQUARED = 50.0;

    @Override
    protected void addExtraSensoryPerception(Player player, GameData gameData, ArrayList<OverlaySprite> strs) {
        if (!player.isDead()) {
            List<Sprite> spriteList = new ArrayList<>();
            for (GameObject obj : getMovableToSpacePositions(gameData, player)) {
                spriteList.add(obj.getSprite(player));
            }

            strs.addAll(getOverlaysForSpritesInRoom(gameData, spriteList, player.getPosition(), player));
        }
    }

    private List<GameObject> getMovableToSpacePositions(GameData gameData, Player player) {
        List<GameObject> result = new ArrayList<>();
        for (double dy = minDY; dy <= maxDY; dy += 0.5) {
            for (double dx = minDX; dx <= maxDX; dx += 0.5) {
                double x = player.getCharacter().getSpacePosition().getX() + dx;
                double y = player.getCharacter().getSpacePosition().getY() + dy;
                if (!(dx == 0.0 && dy == 0.0)) {
                    Room r = null;
                    try {
                        r = gameData.getMap().getRoomForCoordinates(x, y, player.getCharacter().getSpacePosition().getZ(),
                                gameData.getMap().getLevelForRoom(player.getPosition()).getName());
                        if (player.findMoveToAblePositions(gameData).contains(r) && isWithinRange(dx, dy)) {
                            result.add(new MoveTargetObject(x, y, player.getPosition()));
                        }
                    } catch (NoSuchThingException e) {
                        if (isWithinRange(dx, dy)) {
                            result.add(new MoveTargetObject(x, y, player.getPosition()));
                        }
                    }
                }

            }
        }
        return result;
    }

    private boolean isWithinRange(double x, double y) {
        return Math.pow(x, 2) + Math.pow(y, 2) < RANGE_SQUARED;
    }

    private static class MoveTargetObject extends GameObject {
        private final double x;
        private final double y;

        public MoveTargetObject(double x, double y, Room r) {
            super(String.format("Position (%1.1f, %1.1f)", x, y), r);
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean hasAbsolutePosition() {
            return true;
        }

        @Override
        public double getAbsoluteY() {
            return this.y;
        }

        @Override
        public double getAbsoluteX() {
            return this.x;
        }

        @Override
        public Sprite getSprite(Player whosAsking) {
            return new Sprite("movetargetobject", "alert.png", 4, 3, this);
        }

        @Override
        public List<Action> getOverlaySpriteActionList(GameData gameData, Room r, Player forWhom) {
            List<Action> list = new ArrayList<>();
            if (!forWhom.isDead()) {
                list.add(new MoveToSpaceTargetAction(x, y));
            }
            return list;
        }
    }

    private static class MoveToSpaceTargetAction extends Action {


        private final double x;
        private final double y;

        public MoveToSpaceTargetAction(double x, double y) {
            super("Move to (x=" + x + " y=" + y + ")", SensoryLevel.NO_SENSE);
            this.x = x;
            this.y = y;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "moved";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            performingClient.getCharacter().getSpacePosition().setX(x);
            performingClient.getCharacter().getSpacePosition().setY(y);
            performingClient.addTolastTurnInfo("You moved through space. (to " + x + " " + y + ")");
        }

        @Override
        protected void setArguments(List<String> args, Actor performingClient) {

        }
    }
}
