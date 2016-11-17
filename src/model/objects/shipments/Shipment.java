package model.objects.shipments;

import java.util.HashSet;

import model.Actor;
import model.GameData;
import model.items.general.GameItem;
import model.map.Room;

public abstract class Shipment extends HashSet<GameItem> {
	
	private String name;
	private Actor orderedBy;
	private double rankNeeded;
	
	public Shipment(String name) {
		this(name, 0.0);
	}
	
	public Shipment(String name, double rankNeeded) {
		this.name = name;
		this.rankNeeded = rankNeeded;	
	}
	
	
	
	public abstract Shipment clone();



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getCost() {
        int cost = 0;
        for (GameItem gi : this) {
            cost += gi.getCost();
        }
		return cost*2;
	}



//	public void setCost(int cost) {
//		this.cost = cost;
//	}



	public Actor getOrderedBy() {
		return orderedBy;
	}



	public void setOrderedBy(Actor orderedBy) {
		this.orderedBy = orderedBy;
	}

	public double getRankNeeded() {
		return rankNeeded;
	}

    public void hasArrivedIn(Room position, GameData gameData) {

    }
}
