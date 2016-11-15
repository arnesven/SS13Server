package model.items.suits;

import graphics.sprites.RegularBlackShoesSprite;
import graphics.sprites.Sprite;
import model.Actor;
import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;

public abstract class SuitItem extends GameItem {

	private SuitItem under = null;
	
	public SuitItem(String string, double weight, int cost) {
		super(string, weight, cost);
	}
	
	public SuitItem getUnder() {
		return under;
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("suititem", "uniforms.png", 0);
    }

    protected Sprite getWornSprite(Actor whosAsking) {
        List<Sprite> list = new ArrayList<>();
        list.add(new RegularBlackShoesSprite());
        return new Sprite("suititemworn", "uniform.png", 0, list);
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
            list.add(0, new Sprite("getupbase", "human.png", 0));
            buf.append(whosWearing.getBaseName());
            buf.append(naked.getName());
        } else {
            under.getupRecursive(list, buf, whosWearing, whosAsking);
        }
    }


    public void setUnder(SuitItem it) {
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
		if (under == null) {
			return super.getWeight();
		}
		return super.getWeight() + under.getWeight();
	}
	
	public abstract void beingPutOn(Actor actionPerformer);
	public abstract void beingTakenOff(Actor actionPerformer);
	public abstract boolean permitsOver();


}
