package model.items.suits;

import graphics.sprites.RegularBlackShoesSprite;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.PlayerSettings;
import model.actions.general.Action;
import model.actions.general.DropAction;
import model.actions.general.PutOnAction;
import model.items.general.GameItem;
import util.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SuitItem extends GameItem implements Wearable {

	private SuitItem under = null;
	
	public SuitItem(String string, double weight, int cost) {
		super(string, weight, cost);
	}
	
	public SuitItem getUnder() {
		return under;
	}

    protected abstract int getEquipmentSlot();

    public abstract boolean blocksSlot(int targetSlot);

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("suititem", "uniforms.png", 0, this);
    }

    protected Sprite getWornSprite(Actor whosAsking) {
        List<Sprite> list = new ArrayList<>();
        list.add(new RegularBlackShoesSprite());
        return new Sprite("suititemworn", "uniform.png", 0, list, this);
    }

    public final Sprite getGetup(Actor whosWearing, Actor whosAsking) {
            List<Sprite> list = new ArrayList<>();
            StringBuffer buf = new StringBuffer();
            getupRecursive(list, buf, whosWearing, whosAsking);
            return new Sprite(buf.toString(), list);
    }

    private void getupRecursive(List<Sprite> list, StringBuffer buf, Actor whosWearing, Actor whosAsking) {
        list.add(0, getWornSprite(whosAsking));
        buf.append(this.getBaseName());
        if (under == null) {
            Sprite naked = whosWearing.getCharacter().getNakedSprite();
            list.add(0, naked);
            list.add(0, new Sprite("getupbase", "human.png", 0, whosWearing));
            buf.append(whosWearing.getBaseName());
            buf.append(naked.getName());
        } else {
            under.getupRecursive(list, buf, whosWearing, whosAsking);
        }
    }


    public void setUnder(SuitItem it) {
        if (it == this) {
            throw new IllegalStateException("Tried putting same suit ontop of itself");
        }
		under = it;
	}
	
	@Override
	public abstract SuitItem clone();
	
	@Override
	public String getFullName(Actor whosAsking) {
		if (under == null) {
			return super.getFullName(whosAsking);
		}
		return super.getFullName(whosAsking) + " (on " + under.getFullName(whosAsking) + ")";
	}
	
	public double getWeight() {
        try {
            //Logger.log(" In getWeight " + getBaseName());
            if (under == null) {
                return super.getWeight();
            }
            return super.getWeight() + under.getWeight();
        } catch (StackOverflowError soe) {
            System.out.println("In get weight for  " + getBaseName());
            throw soe;
        }
	}

    public static int countSuits(Actor belongsTo) {
	    return belongsTo.getCharacter().getEquipment().getSuitsAsList().size();
    }

    public List<Action> getInventoryActions(GameData gameData, Actor forWhom) {
        List<Action> acts = super.getInventoryActions(gameData, forWhom);
        PutOnAction po = new PutOnAction(forWhom);
        if (po.isAmongOptions(gameData, forWhom, this.getPublicName(forWhom))) {
            acts.add(po);
        }
        return acts;
    }


    public String getEquipmentActionsData(Player player, GameData gameData) {
        String res =  Action.makeActionListStringSpecOptions(gameData, getEquippedActions(gameData, player), player);
        return res;
    }

    private List<Action> getEquippedActions(GameData gameData, Player forWhom) {
        ArrayList<Action> acts = new ArrayList<>();
        if (forWhom.getsActions()) {
            DropAction drop = new DropAction(forWhom);
            if (drop.isAmongOptions(gameData, forWhom, this.getPublicName(forWhom)) ||
                    drop.isAmongOptions(gameData, forWhom, this.getFullName(forWhom))) {
                acts.add(drop);
            }
        }
        return acts; // TODO: at least add drop and unequip
    }

    @Override
    public void putYourselfOn(Equipment eq) {
	    eq.putOnEquipmentInSlot(this, getEquipmentSlot());
    }

    @Override
    public void removeYourself(Equipment eq) {
	    eq.removeEquipmentForSlot(getEquipmentSlot());
    }

    @Override
    public boolean canBeWornBy(Actor actor) {
	    Equipment eq = actor.getCharacter().getEquipment();
	    return eq.canWear(this);
    }


    public boolean hasAdditionalSprites() {
        return getAdditionalSprites().isEmpty();
    }

    public Map<Integer, Sprite> getAdditionalSprites() {
	    return new HashMap<>();
    }
}
