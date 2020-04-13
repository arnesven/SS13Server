package model.objects.consoles;

import comm.chat.plebOS.PlebOSCommandHandler;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.LoginAction;
import model.map.rooms.Room;
import model.objects.general.ElectricalMachinery;
import model.objects.general.RemotelyOperateable;

import java.util.ArrayList;
import java.util.List;

public abstract class Console extends ElectricalMachinery implements RemotelyOperateable {

    private Actor loggedInAt;
    private GameData gameData = null;
    private boolean ffVacant = true;
    private List<String> plebosTexts;

    public Console(String name, Room r) {
		super(name, r);
		this.loggedInAt = null;
		plebosTexts = new ArrayList<>();
	}

	@Override
	public final Sprite getSprite(Player whosAsking) {
        if (isBroken()) {
            return new Sprite("consolebroken", "computer2.png", 12, this);
        }
        if (gameData != null) {
            if (!isPowered(gameData)) {
                return new Sprite("consolenopower", "computer2.png", 13, this);
            }
        }
		return getNormalSprite(whosAsking);
	}

    public Sprite getNormalSprite(Player whosAsking) {
        return new Sprite("console", "computer2.png", 10, this);
    }

    @Override
    protected final void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        this.gameData = gameData;
        if (cl.getCharacter().isCrew() && cl instanceof Player && !PlebOSCommandHandler.isLoggedIn((Player)cl)) {
            if (!isBroken() && isPowered(gameData) && loggedInAt==null) {
                //at.add(new LoginAction(this, cl, consoleFancyFrame));

            }
        }

        addConsoleActions(gameData, cl, at);
    }

    protected abstract void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at);

    public void setLoggedInAt(Player performingClient) {
        this.loggedInAt = performingClient;
    }

    public boolean isFancyFrameVacant() {
        return this.ffVacant;
    }

    public void setFancyFrameOccupied() {
        this.ffVacant = false;
    }


    public void setFancyFrameVacant() {
        this.ffVacant = true;
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
}
