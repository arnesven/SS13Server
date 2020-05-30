package model.objects.consoles;

import comm.chat.plebOS.PlebOSCommandHandler;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtConsoleAction;
import model.actions.general.Action;
import model.actions.objectactions.GeneralSitDownAtConsoleAction;
import model.map.rooms.Room;
import model.objects.SinglePersonUseMachine;
import model.objects.general.ElectricalMachinery;
import model.objects.general.RemotelyOperateable;

import java.util.ArrayList;
import java.util.List;

public abstract class Console extends SinglePersonUseMachine implements RemotelyOperateable {

    private Actor loggedInAt;
    private GameData gameData = null;
    private List<String> plebosTexts;

    public Console(String name, Room r) {
		super(name, r);
		this.loggedInAt = null;
		plebosTexts = new ArrayList<>();
	}

	@Override
	public final Sprite getSprite(Player whosAsking) {
        if (isBroken()) {
            return getBrokenSprite(whosAsking);
        }
        if (gameData != null) {
            if (!isPowered()) {
                return getUnpoweredSprite(whosAsking);
            }
        }
		return getNormalSprite(whosAsking);
	}

    protected Sprite getUnpoweredSprite(Player whosAsking) {
        return new Sprite("consolenopower", "computer2.png", 13, this);
    }

    protected Sprite getBrokenSprite(Player whosAsking) {
        return new Sprite("consolebroken", "computer2.png", 12, this);
    }

    @Override
    public double getPowerConsumption() {
        return 0.000250; // 250 W
    }

    public Sprite getNormalSprite(Player whosAsking) {
        return new Sprite("console", "computer2.png", 10, this);
    }

    @Override
    protected final void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        this.gameData = gameData;
        if (cl.getCharacter().isCrew() && cl instanceof Player && !PlebOSCommandHandler.isLoggedIn((Player)cl)) {
            if (!isBroken() && isPowered() && loggedInAt==null) {
                //at.add(new LoginAction(this, cl, consoleFancyFrame));
            }
        }

        ArrayList<Action> consoleActions = new ArrayList<>();
        addConsoleActions(gameData, cl, consoleActions);
        if (!hasSitDownAction(consoleActions)) {
            consoleActions.add(new GeneralSitDownAtConsoleAction(gameData, this));
            at.addAll(consoleActions);
        } else {
            if (cl.isAI()) {
                consoleActions.removeIf((Action a) -> a instanceof SitDownAtConsoleAction);
                at.addAll(consoleActions);
            } else if (cl.isHuman() || cl.isRobot()) {
                consoleActions.removeIf((Action a) -> !(a instanceof SitDownAtConsoleAction));
                at.addAll(consoleActions);
            }
        }
    }

    protected abstract void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at);

    public void setLoggedInAt(Player performingClient) {
        this.loggedInAt = performingClient;
    }


    public Actor getLoggedInActor() {
        return loggedInAt;
    }

    public void plebOSSay(String s, Player sender) {
        this.plebosTexts.add(s);
    }

    public List<String> getPlebosTexts() {
        return this.plebosTexts;
    }

    public void clearPlebosTexts() {
        this.plebosTexts.clear();
    }

    private boolean hasSitDownAction(ArrayList<Action> at) {
        for (Action a : at) {
            if (a instanceof SitDownAtConsoleAction) {
                return true;
            }
        }
        return false;
    }

}
