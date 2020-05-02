package graphics.sprites;

import graphics.ClientInfo;
import graphics.OverlaySprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.map.SpacePosition;
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
    private static final double maxDZ = 1;
    private static final double minDZ = -1;
    private static final double RANGE_CUBED = 50.0;

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
        for (double dz = minDZ; dz <= maxDZ; dz += 1) {
            for (double dy = minDY; dy <= maxDY; dy += 0.5) {
                for (double dx = minDX; dx <= maxDX; dx += 0.5) {
                    double x = player.getCharacter().getSpacePosition().getX() + dx;
                    double y = player.getCharacter().getSpacePosition().getY() + dy;
                    double z = player.getCharacter().getSpacePosition().getZ() + dz;
                    if (!(dx == 0.0 && dy == 0.0)) {
                        Room r = null;
                        try {
                            r = gameData.getMap().getRoomForCoordinates(x, y, z,
                                    gameData.getMap().getLevelForRoom(player.getPosition()).getName());
                            if (player.findMoveToAblePositions(gameData).contains(r) && isWithinRange(dx, dy, dz)) {
                                result.add(new MoveTargetObject(x, y, z, player.getPosition(), player.getCharacter().getSpacePosition()));
                            }
                        } catch (NoSuchThingException e) {
                            if (isWithinRange(dx, dy, dz)) {
                                result.add(new MoveTargetObject(x, y, z, player.getPosition(), player.getCharacter().getSpacePosition()));
                            }
                        }
                    }

                }
            }
        }
        return result;
    }

    private boolean isWithinRange(double x, double y, double z) {
        return Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2) < RANGE_CUBED;
    }

    private static class MoveTargetObject extends GameObject {
        private final double x;
        private final double y;
        private final double z;
        private final Sprite sprite;

        public MoveTargetObject(double x, double y, double z, Room r, SpacePosition current) {
            super(String.format("Position (%1.1f, %1.1f)", x, y), r);
            this.x = x;
            this.y = y;
            this.z = z;
            if (z == current.getZ()) {
                sprite = new Sprite("movetargetobject", "alert.png", 0, 6, this);
            } else if (z > r.getZ()) {
                sprite = new Sprite("movetargetobjectup", "alert.png", 1, 6, this);
            } else {
                sprite = new Sprite("movetargetobjectdown", "alert.png", 2, 6, this);

            }
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
        public double getAbsoluteZ() {
            return this.z;
        }

        @Override
        public Sprite getSprite(Player whosAsking) {
            return sprite;
        }

        @Override
        public List<Action> getOverlaySpriteActionList(GameData gameData, Room r, Player forWhom) {
            List<Action> list = new ArrayList<>();
            if (!forWhom.isDead()) {
                list.add(new MoveToSpaceTargetAction(x, y, z));
            }
            return list;
        }
    }

    private static class MoveToSpaceTargetAction extends Action {


        private final double x;
        private final double y;
        private final double z;

        public MoveToSpaceTargetAction(double x, double y, double z) {
            super("Move to (x=" + x + " y=" + y + " z=" + z + ")", SensoryLevel.NO_SENSE);
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "moved";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            performingClient.getCharacter().getSpacePosition().setX(x);
            performingClient.getCharacter().getSpacePosition().setY(y);
            performingClient.getCharacter().getSpacePosition().setZ(z);
            performingClient.addTolastTurnInfo("You moved through space. (to " + x + " " + y + " " + z + ")");
        }

        @Override
        protected void setArguments(List<String> args, Actor performingClient) {

        }
    }
}
