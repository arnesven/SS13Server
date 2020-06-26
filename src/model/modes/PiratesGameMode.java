package model.modes;


import model.GameData;
import model.Player;
import model.characters.PleasureBoyCharacter;
import model.characters.PleasureGirlCharacter;
import model.characters.decorators.OnTopOfObjectDecorator;
import model.characters.decorators.PirateCommanderDecorator;
import model.characters.general.GameCharacter;
import model.characters.general.PirateCaptainCharacter;
import model.fancyframe.SinglePageFancyFrame;
import model.items.MedPatch;
import model.items.NoSuchThingException;
import model.items.general.*;
import model.items.suits.*;
import model.items.tools.Blowtorch;
import model.items.tools.RepairTools;
import model.items.weapons.Crowbar;
import model.map.DockingPoint;
import model.map.rooms.*;
import model.modes.objectives.PirateCaptainTraitorObjective;
import model.npcs.*;
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

        GameCharacter pirateCapChar = new PirateCaptainCharacter(throneRoom.getID(), false);
        pirateCaptain.setCharacter(pirateCapChar);
        pirateCapChar.giveItem(new PirateCaptainOutfit(), null);
        pirateCaptain.putOnSuit(new BlueRolledDownCoverall());
        pirateCaptain.setCharacter(new OnTopOfObjectDecorator(pirateCaptain.getCharacter(), ((ThroneRoom)throneRoom).getThrone()));
  }

    @Override
    protected void setUpOtherStuff(GameData gameData) {
        //wizard.getCharacter().giveItem(new MissionDetails(this), null);
        //assignTraitorObjectivesAndGivePDAs(gameData); // TODO, modify this so specific traitor objectives are given
        getObjectives().put(pirateCaptain, new PirateCaptainTraitorObjective(gameData));
        //wizard.getItems().removeIf((GameItem it) -> it instanceof PDA);
        pirateCaptain.setCharacter(new PirateCommanderDecorator(pirateCaptain.getCharacter()));
        getEvents().get("pirate attack").setProbability(0.0);
        dockPirateShip(gameData);

        addNewAlgiersNPCs(gameData, pirateCaptain);



    }

    private void addNewAlgiersNPCs(GameData gameData, Player pirateCaptain) {
        gameData.addNPC(new CommandablePirateNPC(pirateCaptain.getPosition(),
                "Cpl. Orlov", pirateCaptain, new TanRolledDownCoverall(),
                new PirateBackStory("A former Nanotrasen miner, recruited by Eastwood during a raid on the Mining Station.", "Pilot"),
                new GameItem[]{}));
        gameData.addNPC(new CommandablePirateNPC(pirateCaptain.getPosition(),
                "Pvt. Colovisto", pirateCaptain, new GreenRolledDownCoverall(),
                new PirateBackStory("A low-life from Pondiataros. Hired by Eastwood as muscle for various jobs.", "Mechanic"),
                new GameItem[]{new Blowtorch(), new Crowbar()}));
        gameData.addNPC(new CommandablePirateNPC(pirateCaptain.getPosition(),
                "Sgt. Hernandez", pirateCaptain, new BlueRolledDownCoverall(),
                new PirateBackStory("An ex-military Nanotrasen official. After a dishonorable discharge, " +
                        "Eastwood offered Hernandez a permanent position as a Barbie Pirate based out of New Algiers.", "Tough"),
                new GameItem[]{}));
        gameData.addNPC(new CommandablePirateNPC(pirateCaptain.getPosition(),
                "Pvt. Yee", pirateCaptain, new GreenRolledDownCoverall(),
                new PirateBackStory("A former Syndicate operative, trained for black-ops missions. Ruthless and bloodthirsty.", "Mechanic"),
                new GameItem[]{new Blowtorch(), new Crowbar()}));
        gameData.addNPC(new CommandablePirateNPC(pirateCaptain.getPosition(),
                "Cpl. Kyoshi", pirateCaptain, new TanRolledDownCoverall(),
                new PirateBackStory("A former Syndicate spy. After being exposed, Eastwood broke into the penal facility where " +
                        "Kyoshi was detained and thus recruited another pirate for his gang.", "Medic"),
                new GameItem[]{new MedKit(), new MedPatch(), new MedPatch()}));
        gameData.addNPC(new CommandablePirateNPC(pirateCaptain.getPosition(),
                "Sgt. Mendez", pirateCaptain, new BlueRolledDownCoverall(),
                new PirateBackStory("A demolitions expert trained by the Syndicate to sabotage Nanotrasen facilities. " +
                        "Joined Eastwood after a visit to New Algiers and seeing how much fun they were having there.", "Plasma"),
                new GameItem[]{new FragGrenade(), new RemoteBomb()}));
        gameData.addNPC(new CommandablePirateNPC(pirateCaptain.getPosition(),
                "Pvt. Robertson", pirateCaptain, new GreenRolledDownCoverall(),
                new PirateBackStory("A Nanotrasen AI technician gone rogue.", "Hacker"),
                new GameItem[]{new RepairTools(), new Multimeter()}));
        gameData.addNPC(new CommandablePirateNPC(pirateCaptain.getPosition(),
                "Cpl. Sarma", pirateCaptain, new TanRolledDownCoverall(),
                new PirateBackStory("A Nanotrasen Pilot who once took a space taxi to the wrong station, and never came back...", "Pilot"),
                new GameItem[]{}));

        gameData.addNPC(new PleasureNPC(new PleasureBoyCharacter(pirateCaptain.getPosition().getID()), pirateCaptain.getPosition()));
        if (MyRandom.nextDouble() < 0.5) {
            gameData.addNPC(new PleasureNPC(new PleasureGirlCharacter(pirateCaptain.getPosition().getID()), pirateCaptain.getPosition()));
        } else {
            gameData.addNPC(new CommandableGeishaNPC(pirateCaptain.getPosition()));
        }

        try {
            Room eastWoodsQuarters = gameData.getRoom("Eastwood's Chambers");
            gameData.addNPC(new EastwoodsPetNPC(eastWoodsQuarters));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
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
        return "A notorious pirate lord has made a deal with a traitor on SS13 and is coming to make sure the deal " +
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
        pirateShip.getItems().clear();
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
