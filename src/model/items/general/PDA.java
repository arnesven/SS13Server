package model.items.general;

import java.util.ArrayList;
import java.util.List;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.FreeAction;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.itemactions.UsePDAAction;
import model.fancyframe.PDAOrderFancyFrame;
import model.items.TraitorItem;
import model.items.suits.SuperSuit;
import model.modes.TraitorGameMode;
import model.items.weapons.*;

public class PDA extends UplinkItem {

	private TraitorGameMode traitorMode;
	private int usesLeft = 10;

	public PDA(TraitorGameMode traitorMode) {
		super("PDA", 0.5, 1500);
		this.traitorMode = traitorMode;
	}
	
	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at,
                               Actor cl) {
		//at.add(new UsePDAAction(traitorMode, this, gameData));
		if (cl instanceof Player) {
			at.add(new FreeAction("Use PDA Interface", gameData, (Player)cl) {
				@Override
				protected void doTheFreeAction(List<String> args, Player p, GameData gameData) {
					if (p instanceof Player) {
						((Player) p).setFancyFrame(new PDAOrderFancyFrame((Player) p, PDA.this, gameData));
						((Player) p).setNextAction(new DoNothingAction());
					}
				}
			});
		}
	}

	public int getTelecrystalsLeft() {
		return usesLeft;
	}

	public void decrementUses(int number) {
		usesLeft = Math.max(usesLeft-number, 0);
	}

	public static List<TraitorItem> getOrderableItems() {
		List<TraitorItem> items = new ArrayList<>();
		items.add(new LaserPistol());
		items.add(new OrderBundle(3, new FragGrenade()));
		items.add(new DummyHivePlacer());
		items.add(new TimeBomb());
		items.add(new RemoteBomb());
		items.add(new BoobyTrapBomb());
		items.add(new Locator());
		items.add(new LarcenyGloves());
        items.add(new SuperSuit());
        items.add(new EMAG());
        items.add(new PoisonSyringe());
		return items;
	}

	@Override
	public PDA clone() {
		return new PDA(this.traitorMode);
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("PDA", "pda.png", 24, this);
    }

	public TraitorGameMode getTraitorMode() {
		return traitorMode;
	}
}
