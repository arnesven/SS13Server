package model.objects.shipments;

import java.util.HashSet;

import model.Actor;
import model.items.GameItem;

public abstract class Shipment extends HashSet<GameItem> {
	
	private String name;
	private int cost;
	private Actor orderedBy;
	private double rankNeeded;
	
	public Shipment(String name, int cost) {
		this(name, cost, 0.0);
	}
	
	public Shipment(String name, int cost, double rankNeeded) {
		this.name = name;
		this.cost = cost;
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
		return cost;
	}



	public void setCost(int cost) {
		this.cost = cost;
	}



	public Actor getOrderedBy() {
		return orderedBy;
	}



	public void setOrderedBy(Actor orderedBy) {
		this.orderedBy = orderedBy;
	}

	public double getRankNeeded() {
		return rankNeeded;
	}

}
