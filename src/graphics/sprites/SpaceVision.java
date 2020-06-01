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
import model.misc.EVAStrategy;
import model.objects.general.GameObject;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

public class SpaceVision extends NormalVision {


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
        EVAStrategy evaStrat = player.getCharacter().getDefaultEVAStrategy();
        for (double[] pos : evaStrat.getMoveTargetPositions(gameData, player)) {
            result.add(new MoveTargetObject(pos[0], pos[1], pos[2], player.getPosition(), player.getCharacter().getSpacePosition()));
        }
        return result;
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
                sprite = new Sprite("movetargetobject", "alert.png", 3, 6, this);
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
