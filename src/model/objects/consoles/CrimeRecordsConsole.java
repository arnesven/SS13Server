package model.objects.consoles;

import java.util.*;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.CrimeRecordsAction;
import model.actions.objectactions.GeneralSitDownAtConsoleAction;
import model.actions.objectactions.SitDownAtConsoleAtCrimeConsoleAction;
import model.characters.general.AICharacter;
import model.characters.general.GameCharacter;
import model.events.SentenceCountdownEvent;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.suits.Equipment;
import model.items.suits.PrisonerSuit;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.robots.SecuritronNPC;
import model.objects.ai.SecurityCamera;
import model.objects.general.EvidenceBox;
import model.objects.general.GameObject;
import util.Logger;
import util.Pair;

import javax.naming.directory.Attribute;

public class CrimeRecordsConsole extends Console {
	private int[] sentenceLengths = new int[]{2, 3, 5, 7};
	private String[] crimes = new String[]{"Rowdiness", "Theft",
												  "Assault", "Murder"};
	private Map<Actor, Integer> sentences = new HashMap<>();
	private Map<Actor, List<Pair<String, Actor>>> reportMap = new HashMap<>();
    private Map<Actor, List<Pair<String, Actor>>> reportsHistory = new HashMap<>();
	private int noOfSentenced = 1;
    private Room releaseIntoRoom;
	private Set<Actor> extraBaddies;

	public CrimeRecordsConsole(Room r, GameData gameData, Room release) {
		super("Crime Records", r);
		extraBaddies = new HashSet<>();
        NPC securitron = null;
        try {
            securitron = new SecuritronNPC(r, gameData, this);
            gameData.addNPC(securitron);
        } catch (NoSuchThingException e) {
            Logger.log("No crime console found, not adding securitron to station");
        }
        this.releaseIntoRoom = release;

	}

    @Override
    public Sprite getNormalSprite(Player whosAsking) {
        return new Sprite("crimerecordsconsole", "computer2.png", 12, 12, this);
    }

	@Override
	protected void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
		if (cl.getCharacter().isCrew() || cl.getCharacter().checkInstance(((GameCharacter ch) -> ch instanceof AICharacter))) {
			at.add(new CrimeRecordsAction(this));
			if (cl instanceof Player) {
				at.add(new SitDownAtConsoleAtCrimeConsoleAction(gameData, this, (Player)cl));
			}
		}
	}

	public void addReport(Actor criminal, String selectedCrime, Actor reporter) {
		if (reportMap.get(criminal) == null) {
			reportMap.put(criminal, new ArrayList<Pair<String, Actor>>());
		    reportsHistory.put(criminal, new ArrayList<Pair<String, Actor>>());
        }
		
		reportMap.get(criminal).add(new Pair<String, Actor>(selectedCrime, reporter));
        reportsHistory.get(criminal).add(new Pair<String, Actor>(selectedCrime, reporter));

    }


	public void addReport(Actor guy, String selectedCrime, Actor performingClient, int selectedDuration) {
    	if (!crimeAlreadyExists(selectedCrime)) {
    		Logger.log("Adding a new crime: \"" + selectedCrime + "\" with duration " + selectedDuration + " turns");
			addNewCrime(selectedCrime, selectedDuration);
		}
		addReport(guy, selectedCrime, performingClient);


	}

	private void addNewCrime(String selectedCrime, int selectedDuration) {
		String[] newCrimes = new String[crimes.length+1];
		int[] newLength = new int[sentenceLengths.length+1];
		int i = 0;
		for (; i < crimes.length; ++i) {
			newCrimes[i] = crimes[i];
			newLength[i] = sentenceLengths[i];
		}
		newCrimes[i] = selectedCrime;
		newLength[i] = Math.min(selectedDuration, 10);
		crimes = newCrimes;
		sentenceLengths = newLength;
	}

	private boolean crimeAlreadyExists(String selectedCrime) {
    	for (String s : crimes) {
    		if (s.equals(selectedCrime)) {
    			return true;
			}
		}

		return false;
	}

	public Map<Actor, List<Pair<String, Actor>>> getReportedActors() {
		return reportMap;
	}

    public Map<Actor, List<Pair<String, Actor>>> getReportsHistory() {
        return reportsHistory;
    }


	public Actor getMostWanted(boolean withLOS) {
		List<Actor> criminals = new ArrayList<>();
		criminals.addAll(reportMap.keySet());
		Collections.sort(criminals, new Comparator<Actor>() {
			@Override
			public int compare(Actor actor, Actor t1) {
				return  sumCrimesFor(t1) - sumCrimesFor(actor);
			}
		});
		if (withLOS) {
			for (Actor a : criminals) {
				if (a.getPosition().hasWorkingSecurityCamera()) {
					return a;
				}
			}
			return null;
		}

		if (criminals.size() > 0) {
			return criminals.get(0);
		}
		return null;
	}

    public int getTimeForCrime(String crime) throws NoSuchThingException {
        for (int i = 0; i < sentenceLengths.length; ++i) {
            if (crime.equals(crimes[i])) {
                return sentenceLengths[i];
            }
        }
        throw new NoSuchThingException("Could not find time for crime " + crime);
    }

	public int sumCrimesFor(Actor a) {
		int sumSent = 0;
        if (reportMap.get(a) != null) {
            for (Pair<String, Actor> p : reportMap.get(a)) {
                try {
                    sumSent += getTimeForCrime(p.first);
                } catch (NoSuchThingException e) {
                    Logger.log(Logger.CRITICAL, e.getMessage());
                }
            }
        }
		return sumSent;
	}

	public String getCrimeStringFor(Actor a) throws NoSuchThingException {
		if (getReportedActors().get(a) == null) {
			throw new NoSuchThingException("No crimes for actor " + a.getBaseName());
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
        Room brig = null;
        try {
            brig = gameData.getRoom("Brig");

        worst.moveIntoRoom(brig);
		try {
			EvidenceBox ev = gameData.findObjectOfType(EvidenceBox.class);
			transferItems(worst, ev);
			Logger.log("Prisoners affects were stored in the evidence box");
			PrisonerSuit prisonSuit = new PrisonerSuit(noOfSentenced++);
			if (worst.getCharacter().getEquipment().getEquipmentForSlot(Equipment.TORSO_SLOT) != null &&
					!worst.getCharacter().getEquipment().canWear(prisonSuit)) {
				ev.addAffect(worst, worst.getCharacter().getEquipment().getEquipmentForSlot(Equipment.TORSO_SLOT));
				worst.getCharacter().getEquipment().removeEquipmentForSlot(Equipment.TORSO_SLOT);
				Logger.log("Suit was removed from prisoner so prison clothes could be put over");
			}
			worst.putOnSuit(prisonSuit);
		} catch (NoSuchThingException nse) {
			Logger.log(Logger.CRITICAL, "No evidence box found on station. Prisoner keeps items.");
		}
		
		int duration = sumCrimesFor(worst);
		worst.addTolastTurnInfo("You were auto-sentenced by JudgeBot to " + duration + " rounds in the brig. Your personal affects will be returned to you upon release.");
		getReportedActors().remove(worst);
		sentences.put(worst, duration);
		gameData.addEvent(new SentenceCountdownEvent(gameData, worst, this));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
	}

    private void transferItems(Actor worst, EvidenceBox ev) {
        ev.addAffects(worst, worst.getItems());
        for (GameItem it : worst.getItems()) {
            it.setHolder(null);
            it.setPosition(ev.getPosition());
        }
        worst.getItems().removeAll(worst.getItems());

    }

    public Map<Actor, Integer> getSentenceMap() {
		return sentences;
	}

	public void release(GameData gameData, Actor inmate) {
		getSentenceMap().remove(inmate);
        inmate.moveIntoRoom(releaseIntoRoom);

        try {
			EvidenceBox ev = gameData.findObjectOfType(EvidenceBox.class);
			for (GameItem it : ev.removeAffects(inmate)) {
				inmate.addItem(it, null);
			}
			if (inmate.getCharacter().getEquipment().getEquipmentForSlot(Equipment.TORSO_SLOT) instanceof PrisonerSuit) {
				inmate.getCharacter().getEquipment().removeEquipmentForSlot(Equipment.TORSO_SLOT);
			}
		} catch (NoSuchThingException nse) {
			Logger.log(Logger.CRITICAL, "No evindencebox found on station, nothing to retrieve.");
		}
		
		inmate.addTolastTurnInfo("You were released from the brig.");
		
	}


    public Room getReleaseIntoRoom() {
        return releaseIntoRoom;
    }

	public String[] getAllCrimes() {
    	return crimes;
	}

	public int[] getSentenceLengths() {
		return sentenceLengths;
	}

	public Set<Actor> getExtraBaddies() {
    	return extraBaddies;
	}

	public boolean addExtraBaddieByName(String target, GameData gameData) {
		for (Actor a : gameData.getActors()) {
			if (!extraBaddies.contains(a)) {
				if (a.getBaseName().contains(target) || target.contains(a.getBaseName())) {
					if (hasLOSToBaddie(a)) {
						extraBaddies.add(a);
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean hasLOSToBaddie(Actor a) {
		return a.getPosition().hasWorkingSecurityCamera();
	}
}
