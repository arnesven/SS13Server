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
import model.plebOS.ComputerSystemLogin;

import java.util.ArrayList;

public abstract class Console extends ElectricalMachinery implements RemotelyOperateable {
	
	public Console(String name, Room r) {
		super(name, r);
	}

	@Override
	public Sprite getSprite(Player whosAsking) {
		return new Sprite("console", "computer2.png", 10);
	}

    @Override
    protected final void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (cl.getCharacter().isCrew() && cl instanceof Player && !PlebOSCommandHandler.isLoggedIn((Player)cl)) {
            if (!isBroken() && isPowered(gameData)) {
                at.add(new LoginAction(this, cl));

            }
        }

        addConsoleActions(gameData, cl, at);
    }

    protected abstract void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at);
}
