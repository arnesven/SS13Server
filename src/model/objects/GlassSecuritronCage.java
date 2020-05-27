package model.objects;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.general.SecuritronCharacter;
import model.items.NoSuchThingException;
import model.map.rooms.RoboticsRoom;
import model.npcs.robots.SecuritronNPC;
import model.objects.consoles.CrimeRecordsConsole;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.List;

public class GlassSecuritronCage extends GameObject {
    private boolean isClosed;
    private final SecuritronCharacter securitronChar;

    public GlassSecuritronCage(RoboticsRoom roboticsRoom) {
        super("Glass Cage", roboticsRoom);
        this.isClosed = true;
        this.securitronChar = new SecuritronCharacter(roboticsRoom.getID());
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (isClosed) {
            List<Sprite> sprs = new ArrayList<>();
            sprs.add(securitronChar.getSprite(whosAsking));
            sprs.get(0).shiftUpPx(6);
            sprs.add(new Sprite("cage", "stationobjs.png", 283, this));
            return new Sprite("securitroncageclosed", "human.png", 0, sprs, this);
        }
        return new Sprite("securitroncageopen", "stationobjs.png", 284, this);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (isClosed) {
            at.add(new ActivateSecuritronAction());
        }
    }

    private class ActivateSecuritronAction extends Action {

        public ActivateSecuritronAction() {
            super("Activate Securitron", SensoryLevel.OPERATE_DEVICE);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "fiddled with " + GlassSecuritronCage.this.getPublicName(whosAsking);
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            try {
                gameData.addNPC(new SecuritronNPC(GlassSecuritronCage.this.getPosition(), gameData,
                        gameData.findObjectOfType(CrimeRecordsConsole.class), securitronChar));
                isClosed = false;
            } catch (NoSuchThingException e) {
                performingClient.addTolastTurnInfo("What, SecuriTRON won't come on? " + failed(gameData, performingClient));
            }
        }

        @Override
        protected void setArguments(List<String> args, Actor performingClient) {

        }
    }
}
