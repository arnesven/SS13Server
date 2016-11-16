package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.itemactions.HealWithMedKitAction;
import model.items.general.GameItem;
import model.items.general.MedKit;
import model.items.general.Syringe;
import model.map.Room;

import java.util.ArrayList;
import java.util.List;

public class MedkitDispenser extends DispenserObject {

	public MedkitDispenser(int i, Room pos) {
		super("Medical Storage", pos);
		this.addItem(new MedKit());
		this.addItem(new MedKit());
		this.addItem(new MedKit());
		this.addItem(new Syringe());
	}

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("medkitdispenser", "closet.png", 30);
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        super.addSpecificActionsFor(gameData, cl, at);
        if (getAMedkit() != null && cl.getCharacter().getHealth() < cl.getMaxHealth()) {
            MedKit med = getAMedkit();
            HealWithMedKitAction healAct = new HealWithMedKitAction(cl, med);
            at.add(new Action("Retrieve and Heal", SensoryLevel.OPERATE_DEVICE) {
                @Override
                protected String getVerb(Actor whosAsking) {
                    return "healed";
                }

                @Override
                protected void execute(GameData gameData, Actor performingClient) {
                    performingClient.getCharacter().giveItem(med, null);
                    List<String> args = new ArrayList<String>();
                    args.add("Yourself");
                    healAct.setArguments(args, performingClient);
                    healAct.printAndExecute(gameData);
                }

                @Override
                public void setArguments(List<String> args, Actor performingClient) {

                }
            });
        }
    }

    private MedKit getAMedkit() {
        for (GameItem it : getInventory()) {
            if (it instanceof MedKit) {
                return (MedKit) it;
            }
        }
        return null;
    }
}
