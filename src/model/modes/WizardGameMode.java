package model.modes;

import model.GameData;
import model.Player;
import model.characters.decorators.TraitorCharacter;
import model.characters.general.AICharacter;
import model.characters.general.GameCharacter;
import model.characters.general.WizardCharacter;
import model.characters.special.SpectatorCharacter;
import model.fancyframe.SinglePageFancyFrame;
import model.items.general.GameItem;
import model.items.general.PDA;
import model.items.suits.WizardsHat;
import model.items.suits.WizardsRobes;
import model.map.MapLevel;
import model.map.rooms.BrigRoom;
import model.map.rooms.ShuttleRoom;
import model.map.rooms.WizardDinghyRoom;
import model.modes.objectives.TraitorObjective;
import model.npcs.NPC;
import util.HTMLText;
import util.Logger;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

public class WizardGameMode extends TraitorGameMode {

    private static final String WIZARD_START_STRING = "You are a " + HTMLText.makeWikiLink("modes/wizard", "Wizard") + "!";

    private Player wizard;
    private NPC decoy;

    @Override
    public String getName() {
        return "Wizard";
    }

    @Override
    protected int getNoOfTraitors(GameData gameData) {
        return 1;
    }

    @Override
    protected void assignOtherRoles(ArrayList<GameCharacter> listOfCharacters, GameData gameData) {
        super.assignOtherRoles(listOfCharacters, gameData);


        ShuttleRoom wizarddinghy = new WizardDinghyRoom(gameData);
        Integer[] randLevel = MyRandom.sample(gameData.getMap().getJumpableToLevels());
        String wizLevel = gameData.getMap().getLevelForCoordinates(randLevel, gameData);
        gameData.getMap().addRoom(wizarddinghy, wizLevel, "space");


        assignTraitors(gameData);
        wizard = getTraitors().get(0);
        wizard.getItems().removeIf((GameItem it) -> it instanceof PDA);
        decoy = makeDecoy(wizard, gameData);

        GameCharacter wizardChar = new WizardCharacter(wizarddinghy.getID());
        wizard.setCharacter(wizardChar);
        wizard.putOnSuit(new WizardsRobes());
        wizard.putOnSuit(new WizardsHat());
    }

    @Override
    protected void setUpOtherStuff(GameData gameData) {

    }

    @Override
    public String getSummary(GameData gameData) {
        return (new WizardModeStats(gameData, this)).toString();
    }

    public void setAntagonistFancyFrame(Player c) {
        c.setFancyFrame(new SinglePageFancyFrame(c.getFancyFrame(), "Secret Role!",
                HTMLText.makeColoredBackground("Yellow", HTMLText.makeCentered("<br/><br/><b>" +
                        HTMLText.makeText("Red", WIZARD_START_STRING) + "</b><br/>" + // TODO: write about decoy
                        HTMLText.makeImage(new PDA(this).getSprite(null)) + "<br/>" + // TODO: change to wizard's hat.
                        getObjectiveText(c) + "<br/>" + "<i>You can access this dialog again by using your Mission Details.</i>")))); // TODO: make mission details
    }

    @Override
    protected void addProtagonistStartingMessage(Player c) {
        c.addTolastTurnInfo("A wizard's dinghy has been spotted in a nearby sector. Once he gets on the station - stop him!");
    }

    @Override
    public String getModeDescription() {
        return "An intergalactic hedge wizard has been contracted by the Syndicate to infiltrate SS13 and carry out a secret mission." +
                " Armed with spells and magical items, can the crew scramble to kill, or better yet, capture this magical menace?";
    }


    public boolean wizardIsCaptured() {
        return pointsFromCapturedWizard() > 0;
    }

    public int pointsFromCapturedWizard() {
        return (wizard.getPosition() instanceof BrigRoom)?200:0;
    }

    @Override
    protected int extraExtendedPoints(GameData gameData) {
        return pointsFromCapturedWizard();
    }

    @Override
    public String getAntagonistName(Player p) {
        return "Wizard";
    }

    @Override
    public String getImageURL() {
        return "https://www.ida.liu.se/~erini02/ss13/wizard.jpg";
    }

}
