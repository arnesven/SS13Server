package model.objects.consoles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.objectactions.CrimeRecordsAction;
import model.events.SentenceCountdownEvent;
import model.map.Room;
import model.npcs.NPC;
import model.objects.GameObject;
import util.Pair;

public class CrimeRecordsConsole extends Console {
	public static int[] sentenceLengths = new int[]{1, 2, 3, 5};
	public static String[] crimes = new String[]{"Rowdiness", "Theft", 
												  "Assault", "Murder"};
	
	
	private Map<Actor, Integer> sentences = new HashMap<>();
	private Map<Actor, List<Pair<String, Actor>>> reportMap = new HashMap<>();
	
	public CrimeRecordsConsole(Room r) {
		super("Crime Records", r);
	}

	@Override
	protected void addActions(GameData gameData, Player cl, ArrayList<Action> at) {
		if (cl.getCharacter().isCrew()) {
			at.add(new CrimeRecordsAction(this));
		}
	}

	public void addReport(Actor guy, String selectedCrime, Actor reporter) {
		if (reportMap.get(guy) == null) {
			reportMap.put(guy, new ArrayList<Pair<String, Actor>>());
		}
		
		reportMap.get(guy).add(new Pair<String, Actor>(selectedCrime, reporter));	
	}

	public Map<Actor, List<Pair<String, Actor>>> getReportedActors() {
		return reportMap;
	}

	public static CrimeRecordsConsole find(GameData gameData) {
		for (Room r : gameData.getRooms()) {
			for (GameObject o : r.getObjects()) {
				if (o instanceof CrimeRecordsConsole) {
					return (CrimeRecordsConsole)o;
				}
			}
		}
		
		throw new NoSuchElementException("Could not find CrimeRecordsConsole!");
	}

	public Actor getMostWanted() {
		int maxSent = 0;
		Actor worst = null;
		for (Actor a : reportMap.keySet()) {
			int sumSent = sumCrimesFor(a);	
			
			if (sumSent > maxSent) {
				maxSent = sumSent;
				worst = a;
			}
		}
		return worst;
	}

	public int sumCrimesFor(Actor a) {
		int sumSent = 0;	
		for (Pair<String, Actor> p : reportMap.get(a)) {
			for (int i = 0; i < sentenceLengths.length; ++i) {
				if (p.first.equals(crimes[i])) {
					sumSent += sentenceLengths[i];
					break;
				}
			}	
		}
		return sumSent;
	}

	public String getCrimeStringFor(Actor a) {
		if (getReportedActors().get(a) == null) {
			throw new NoSuchElementException("No crimes for actor " + a.getBaseName());
		}
		String entry = "";
		boolean commasNeeded = false;
		for (Pair<String, Actor> crime : getReportedActors().get(a)) {
			if (commasNeeded) {
				entry += ", ";
			}
			entry += crime.first;				
			commasNeeded = true;
		}
		return entry;
	}

	public void teleBrig(Actor worst, GameData gameData) {
		Room brig = gameData.getRoom("Brig");
		worst.moveIntoRoom(brig);
		
		int duration = sumCrimesFor(worst);
		worst.addTolastTurnInfo("You were auto-sentenced by JudgeBot to " + duration + " rounds in the brig.");
		getReportedActors().remove(worst);
		sentences.put(worst, duration);
		gameData.addEvent(new SentenceCountdownEvent(gameData, worst, this));
	}

	public Map<Actor, Integer> getSentenceMap() {
		return sentences;
	}

}
