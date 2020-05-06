package model.objects.consoles;

import java.util.*;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtPowerConsoleAction;
import model.actions.general.Action;
import model.actions.objectactions.PowerConsoleAction;
import model.map.rooms.Room;
import model.objects.power.PositronGenerator;

public class GeneratorConsole extends Console {


    private PositronGenerator powerSource;

	public GeneratorConsole(Room r, GameData gameData) {
		super("Power Console", r);
        powerSource = new PositronGenerator(0.174, r, gameData);
        r.addObject(powerSource);
        setPowerPriority(3);
	}

    @Override
    public Sprite getNormalSprite(Player whosAsking) {
        return new Sprite("powerconsole", "computer2.png", 16, 10, this);
    }


	@Override
	protected void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
		at.add(new PowerConsoleAction(this));
		if (cl instanceof Player) {
            at.add(new SitDownAtPowerConsoleAction(gameData, this));
        }
	}


    public PositronGenerator getSource() {
        return powerSource;
    }
}
