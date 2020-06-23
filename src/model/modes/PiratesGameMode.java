package model.modes;


import model.GameData;
import model.Player;
import model.characters.decorators.NPCCommanderDecorator;
import model.characters.decorators.PirateCommanderDecorator;
import model.characters.general.GameCharacter;
import model.characters.general.PirateCaptainCharacter;
import model.characters.general.PirateCharacter;
import model.characters.general.WizardCharacter;
import model.fancyframe.SinglePageFancyFrame;
import model.items.MissionDetails;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.PDA;
import model.items.suits.PirateCaptainOutfit;
import model.items.suits.WizardsHat;
import model.items.suits.WizardsRobes;
import model.map.DockingPoint;
import model.map.GameMap;
import model.map.rooms.*;
import model.modes.objectives.PirateCaptainTraitorObjective;
import model.npcs.NPC;
import util.HTMLText;
import util.MyRandom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PiratesGameMode extends TraitorGameMode {

    private static final String PIRATE_CAPTAIN_START_STRING = "You are a " + HTMLText.makeWikiLink("modes/pirates", "Pirate Captain") + "!";;
    private Player pirateCaptain;
    private NPC decoy;

    @Override
    public String getName() {
        return "Space Pirates";
    }


    @Override
    protected int getNoOfTraitors(GameData gameData) {
        return 1;
    } // TODO: change to 2

    @Override
    protected void assignOtherRoles(ArrayList<GameCharacter> listOfCharacters, GameData gameData) {
        super.assignOtherRoles(listOfCharacters, gameData);

        // TODO: Make New Algiers Station
        //ShuttleRoom wizarddinghy = new WizardDinghyRoom(gameData);
        //Integer[] randLevel = MyRandom.sample(gameData.getMap().getJumpableToLevels());
        //String wizLevel = gameData.getMap().getLevelForCoordinates(randLevel, gameData);
        //gameData.getMap().addRoom(wizarddinghy, wizLevel, "space");

        Room throneRoom = null;
        try {
            throneRoom = gameData.getRoom("Throne Room");
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

        assignTraitors(gameData);
        pirateCaptain = getTraitors().get(0);
        decoy = makeDecoy(pirateCaptain, gameData);

        GameCharacter pirateCapChar = new PirateCaptainCharacter(throneRoom.getID());
        pirateCaptain.setCharacter(pirateCapChar);
        pirateCaptain.putOnSuit(new PirateCaptainOutfit());
  }

    @Override
    protected void setUpOtherStuff(GameData gameData) {
        //wizard.getCharacter().giveItem(new MissionDetails(this), null);
        //assignTraitorObjectivesAndGivePDAs(gameData); // TODO, modify this so specific traitor objectives are given
        getObjectives().put(pirateCaptain, new PirateCaptainTraitorObjective());
        //wizard.getItems().removeIf((GameItem it) -> it instanceof PDA);
        pirateCaptain.setCharacter(new PirateCommanderDecorator(pirateCaptain.getCharacter()));
        getEvents().get("pirate attack").setProbability(0.0);
        dockPirateShip(gameData);
    }


    @Override
    public String getSummary(GameData gameData) {
        return (new PiratesModeStats(gameData, this)).toString();
    }

    public void setAntagonistFancyFrame(Player c) {
        c.setFancyFrame(new SinglePageFancyFrame(c.getFancyFrame(), "Secret Role!",
                HTMLText.makeColoredBackground("orange", HTMLText.makeCentered("<br/><br/><b>" +
                        HTMLText.makeText("black", PIRATE_CAPTAIN_START_STRING) + "</b><br/>" + //
                        HTMLText.makeText("black", "Your decoy is: " + decoy.getPublicName(c) + "<br/>") +
                        getObjectiveText(c) + "<br/>" + "<i>You can see your objective again by using your Mission Details.</i>"))));
    }

    @Override
    protected void addProtagonistStartingMessage(Player c) {
        c.addTolastTurnInfo("A notorious pirate lord is coming to the station to make a deal with a traitor. All hands on deck!");
    }

    @Override
    public String getModeDescription() {
        return "A notorious pirate lord has made a deal with a traitor on SS13 and is coming to make sure the deal" +
                "goes down solid. The crew must battle these rowdy boozers before they turn the station to scrap. ";
    }


    @Override
    public String getAntagonistName(Player p) {
        return "Pirate";
    }

    @Override
    public String getImageURL() {
        return "https://www.ida.liu.se/~erini02/ss13/pirates.jpg";
    }

    public Player getPirateCaptain() {
        return pirateCaptain;
    }

    @Override
    public Map<Player, NPC> getDecoys() {
        Map<Player, NPC> map = new HashMap<>();
        map.put(pirateCaptain, decoy);
        return map;
    }


    private void dockPirateShip(GameData gameData) {
        PirateShipRoom pirateShip = new PirateShipRoom(gameData);
        List<DockingPoint> dockingPoints = new ArrayList<>();
        for (DockingPoint dp : gameData.getMap().getLevel("new algiers").getDockingPoints()) {
            if (pirateShip.canDockAt(gameData, dp)) {
                dockingPoints.add(dp);
            }
        }
        gameData.getMap().addRoom(pirateShip, "new algiers", "space");
        pirateShip.dockYourself(gameData, MyRandom.sample(dockingPoints));
    }

}
