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

public abstract class Console extends ElectricalMachinery implements RemotelyOperateable {

    private Actor loggedInAt;

    public Console(String name, Room r) {
		super(name, r);
		this.loggedInAt = null;
	}

	@Override
	public Sprite getSprite(Player whosAsking) {
		return new Sprite("console", "computer2.png", 10, this);
	}

    @Override
    protected final void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (cl.getCharacter().isCrew() && cl instanceof Player && !PlebOSCommandHandler.isLoggedIn((Player)cl)) {
            if (!isBroken() && isPowered(gameData) && loggedInAt==null) {
                at.add(new LoginAction(this, cl));

            }
        }

        addConsoleActions(gameData, cl, at);
    }

    protected abstract void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at);

    public void setLoggedInAt(Player performingClient) {
        this.loggedInAt = performingClient;
    }
}
