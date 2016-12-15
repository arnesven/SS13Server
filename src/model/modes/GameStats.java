package model.modes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import graphics.sprites.SpriteManager;
import model.Actor;
import model.GameData;
import model.Player;
import model.events.ambient.SpontaneousCrazyness;
import model.events.ambient.OngoingEvent;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.MoneyStack;
import model.items.laws.AILaw;
import model.map.rooms.OtherDimension;
import model.modes.goals.PersonalGoal;
import model.npcs.AlienNPC;
import model.npcs.animals.CatNPC;
import model.npcs.HumanNPC;
import model.npcs.NPC;
import model.npcs.PirateNPC;
import model.objects.consoles.AIConsole;
import model.objects.consoles.PowerSource;
import util.HTMLText;
import util.Logger;

public abstract class GameStats {
	
	protected GameData gameData;
	private GameMode mode;


    public GameStats(GameData gameData, GameMode mode) {
		this.gameData = gameData;
		this.mode = mode;

	}

	/**
	 * This method is called during player-table generation
	 * for all players who are not dead and will show its result
	 * in the status column.
	 * @param value
	 * @return
	 */
	protected abstract String getModeSpecificStatus(Actor value);
	
	/**
	 * This method is called during player-table generation
	 * for all players who are dead. The result will show up 
	 * after the "Dead" string in the status column
	 * @param value
	 * @return
	 */
	protected abstract String getExtraDeadInfo(Actor value);
	
	/**
	 * This method is called during player-table generation
	 * for all players. The result will show up in the column 
	 * to the right of status, before the "killed by"-info.
	 * @param value
	 * @return
	 */
	protected abstract String modeSpecificExtraInfo(Actor value);

	public abstract String getMode();
	public abstract String getOutcome();
	public abstract String getEnding();
	
	@Override
	public String toString() {
		return "<h3>Game is over!</h3>" + getTopContent() +
				"<table>" +
				"<tr><td> Mode: </td><td>" + HTMLText.makeWikiLink("modes/" + getMode().toLowerCase(), getMode()) + "</td></tr>" +
				"<tr><td> Players: </td><td>" + gameData.getPlayersAsList().size() + "</td></tr>" + 
				"<tr><td> Rounds: </td><td>" + gameData.getRound() + "</td></tr>" +
				"<tr><td> Outcome: </td><td>" + getOutcome() + "</td></tr>" +
				"<tr><td> Ending: </td><td>" + getEnding() + "</td></tr>" +
				"</table> <br/>" + 
				generatePlayersTable() + "<br/>" + 
				getContent() + "<br/>" + 
				getMiscStats() + "<br/>" +
                getHallOfFame();
	}

	protected String getTopContent() {
		return "<p><i>Hopefully it didn't suck too badly!</i></p>";
	}

	private String generatePlayersTable() {
		StringBuffer buf = new StringBuffer("<table>");
		buf.append("<tr><td></td><td><b>Crew      </b></td>");
		buf.append(    "<td><b>HP         </b></td>");
		buf.append(    "<td><b>Status     </b></td>");
		buf.append(    "<td><b> </b></td></tr>");
		for (Map.Entry<String, Player> entry : gameData.getPlayersAsEntrySet()) {
			buf.append("<tr><td>");
            String img = SpriteManager.encode64(entry.getValue().getCharacter().getSprite(entry.getValue()));
            buf.append("<img src=\"data:image/png;base64," + img +"\"></img>");
            buf.append("</td><td>");
			buf.append(entry.getValue().getBaseName() + " (" + entry.getKey() + ")");
			buf.append("</td><td>");
			buf.append(entry.getValue().getHealth() + "");
			buf.append("</td><td>");
			buf.append(getStatusStringForPlayer(entry.getValue()));
			buf.append("</td><td>");
			buf.append(getExtraInfoAndKilledBy(entry.getValue()));
			buf.append("</td><td>");
		}
		for (NPC npc : gameData.getNPCs()) {
			if (npc instanceof HumanNPC) {
                appendForNPC(buf, npc);
			}
		}
//        for (NPC npc : gameData.getNPCs()) {
//            if (!(npc instanceof HumanNPC)) {
//                appendForNPC(buf, npc);
//            }
//        }

		
		buf.append("</table>");
		
		return buf.toString();
	}

    private void appendForNPC(StringBuffer buf, NPC npc) {
        buf.append("<tr><td>");
        String img = SpriteManager.encode64(npc.getCharacter().getSprite(npc));
        buf.append("<img src=\"data:image/png;base64," + img +"\"></img>");
        buf.append("</td><td>");
        buf.append(npc.getBaseName());
        buf.append("</td><td>");
        buf.append(npc.getHealth() + "");
        buf.append("</td><td>");
        buf.append(getStatusStringForPlayer(npc));
        buf.append("</td><td>");
        buf.append(getExtraInfoAndKilledBy(npc));
        buf.append("</td><td>");
    }


    private String getExtraInfoAndKilledBy(Actor value) {
		String result = "";
		
		result += modeSpecificExtraInfo(value);
        result += protagonistTaskInfo(value);

		if (value.isDead()) {
			if (!result.equals("")) {
				result += ", ";
			}

            Logger.log("Value is " + value);
            Logger.log("Value killer string is " + value.getCharacter().getKillerString());
            if (value.getCharacter().getKillerString() != null) {
                if (value.getCharacter().getKillerString().equals(value.getBaseName())) {
                    result += "<i>Committed suicide!</i>";
                } else {
                    result += "<i>Killed by ";
                    result += value.getCharacter().getKillerString() + "</i>";
                }
            } else {
                result += "<i>Killed by unknown";
            }
		}
		
		return result;
	}

    private String protagonistTaskInfo(Actor value) {
        PersonalGoal pt = gameData.getGameMode().getTasks().getGoalsForActors().get(value);
        if (pt == null) {
            return "";
        }
        String result = " (Personal Goal: " + pt.getText() + " - ";
        if (pt.isCompleted(gameData)) {
            result += HTMLText.makeText("green", "*Success*") + ")";
        } else {
            result += HTMLText.makeText("red", "*Failed*") + ")";
        }
        return result;
    }


    private String getStatusStringForPlayer(Actor value) {
		if (value.isDead()) {

			return "<span style='background-color: #AAAAAA'>Dead" + getExtraDeadInfo(value) + "</span>";
		}
		
		String s = getModeSpecificStatus(value);
		if (!s.equals("")) {
			return s;
		}
		
		return "Alive";
	}


    final private String getMiscStats() {
        String res = "<br/> <table>" +
                "<tr><td><b>Miscellaneous Stats</b></td><td></td></tr>" +
                "<tr><td> Fires put out: </td><td>" + getFireString(gameData) + "</td></tr>" +
                "<tr><td> Hull breaches fixed: </td><td>" + getHullString(gameData) + "</td></tr>";
        try {
            res +=
                    "<tr><td> Station power output: </td><td>" + String.format("%.1f", gameData.findObjectOfType(PowerSource.class).getPowerOutput() * 100.0) +
                            "%  <a target='_blank' href='https://www.wolframalpha.com/input/?i=plot" + powerHistoryString() + "'>graph</a></td></tr>";
        } catch (NoSuchThingException e) {
            Logger.log(Logger.CRITICAL, "What? no generator on station?");
        }
        res += "<tr><td> Cat survived: </td><td>" + isCatDead(gameData) + "</td></tr>" +
                "<tr><td> Greatest bomb chain: </td><td>" + mode.getMaxChain() + "</td></tr>" +
                "<tr><td> Bombs defused: </td><td>" + mode.getBombsDefused() + "</td></tr>" +
                "<tr><td> Parasites spawned: </td><td>" + mode.getAllParasites().size() + "</td></tr>" +
                "<tr><td> Parasites killed: </td><td>" + countDead(mode.getAllParasites()) + "</td></tr>" +
                "<tr><td> Parasite vanquisher: </td><td>" + findVanquisher(mode.getAllParasites()) + "</td></tr>";
        String pirateKiller = findPirateKiller(gameData);
        if (pirateKiller != null) {
            res += "<tr><td> Pirate Killer: </td><td>" + pirateKiller + "</td></tr>";
        }
        String crazyPeople = crazyPeopleString();
        if (!crazyPeople.equals("")) {
            res += "<tr><td colspan=\"2\"> " + crazyPeopleString() + " went crazy. </td>";
        }
        for (String str : mode.getMiscHappenings()) {
            res += "<tr><td colspan=\"2\"> " + str + "</td>";
        }
        for (Actor a : gameData.getActors()) {
            if (a.getPosition() instanceof OtherDimension && !(a instanceof AlienNPC)) {
                res += "<tr><td colspan=\"2\"> " + a.getBaseName() + " got trapped in another dimension! </td>";
            }
        }
        Actor winner = whoGotMostMoney();
        if (winner != null) {
            try {
                res += "<tr><td colspan=\"2\"> " + winner.getBaseName() + " accrued the most money: " +
                        ((MoneyStack) GameItem.getItemFromActor(winner, new MoneyStack(1))).getAmount() + ".";
            } catch (NoSuchThingException e) {

            }
        }

        if (gameData.getGameMode().aiIsPlayer()) {
            try {
                AIConsole console = gameData.findObjectOfType(AIConsole.class);
                res += "<tr><td><b>AI Laws at end of game</b></td></tr>";
                for (AILaw law : console.getLaws()) {
                    res += "<tr><td>" + law.getNumber() + ". " +
                            law.getBaseName() + "</td></tr>";
                }
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
        }

        res += "</table>";

        return res;
    }

    private Actor whoGotMostMoney() {
        int max = 0;
        Actor winner = null;
        for (Actor a : gameData.getActors()) {
            try {
                int hisAmount = ((MoneyStack) GameItem.getItemFromActor(a, new MoneyStack(1))).getAmount();
                if (hisAmount > max) {
                    winner = a;
                    max = hisAmount;
                }
            } catch (NoSuchThingException e) {
                // had no money;
            }

        }

        return winner;
    }

    private String findPirateKiller(GameData gameData) {
        List<Actor> pirates = new ArrayList<>();
        Map<String, Integer> killMap = new HashMap<>();
        for (Actor a : gameData.getActors()) {
            if (a instanceof PirateNPC) {
                if (a.isDead()) {
                    if (a.getCharacter().getKillerString() != null) {
                        String key = a.getCharacter().getKillerString();
                        if (killMap.containsKey(key)) {
                            killMap.put(key, killMap.get(key) + 1);
                        } else {
                            killMap.put(a.getCharacter().getKillerString(), 1);
                        }
                    }
                }
            }
        }
        if (killMap.size() == 0) {
            return null;
        }

        int maxkill = 0;
        String maxkillName = null;
        for (Entry<String, Integer> entry : killMap.entrySet()) {
            if (entry != null) {
                if (entry.getValue() > maxkill) {
                    maxkill = entry.getValue();
                    maxkillName = entry.getKey();
                }
            }
        }

        return maxkillName;

    }

    private String powerHistoryString() throws NoSuchThingException {
        String res = "";
        for (Double d : gameData.findObjectOfType(PowerSource.class).getHistory()) {
            res += String.format("+%.1f", d.doubleValue());
        }
        System.out.println("Power history is: " + res);
        return res;
    }


    private String crazyPeopleString() {
		String res = "";
		for (NPC npc : ((SpontaneousCrazyness)mode.getEvents().get("crazyness")).getCrazyPeople()) {
			if (!res.equals("")) {
				res += ", ";
			}
			res += npc.getBaseName();
		}
		return res;
	}

	public abstract String getContent();

	private String isCatDead(GameData gameData2) {
		for (NPC npc : gameData2.getNPCs()) {
			if (npc instanceof CatNPC) {
				if (npc.isDead()) {
					return "No";
				} else {
					return "Yes";
				}
			}
		}
		return "No";
	}

	private String getFireString(GameData gameData) {
		OngoingEvent fire = (OngoingEvent) mode.getEvents().get("fires");
		return fire.noOfFixed() + "/" + fire.noOfOngoing();
	}
	
	private String getHullString(GameData gameData2) {
		OngoingEvent hull = (OngoingEvent) mode.getEvents().get("hull breaches");
		return hull.noOfFixed() + "/" + hull.noOfOngoing();
	}
	
	private int countDead(List<NPC> allParasites) {
		int sum = 0;
		for (NPC para : allParasites) {
			if (para.isDead()) {
				sum++;
			}
		}
		return sum;
	}

	
	private String findVanquisher(List<NPC> allParasites) {
		HashMap<String, Integer> map = new HashMap<>();
		for (NPC n : allParasites) {
			if (n.isDead()) {
				String key = n.getCharacter().getKillerString();
				if (map.containsKey(n.getCharacter().getKillerString())) {
					map.put(key, map.get(key) + 1);
				} else {
					map.put(key, 1);
				}
			}
		}
		
		int max = 0;
		String maxName = "Nobody";
		for (Entry<String, Integer> ent : map.entrySet()) {
			if (ent.getValue() > max) {
				maxName = ent.getKey();
			}
		}
		
		return maxName;
	}


    public String getHallOfFame() {
        return (new HallOfFame(gameData)).getHTMLTable();
    }
}
