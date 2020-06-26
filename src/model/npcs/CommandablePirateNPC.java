package model.npcs;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.FreeAction;
import model.actions.general.Action;
import model.actions.itemactions.ShowExamineFancyFrameAction;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.fancyframe.SinglePageFancyFrame;
import model.items.foods.SpaceRum;
import model.items.general.FireExtinguisher;
import model.items.general.GameItem;
import model.items.suits.RolledDownCoverall;
import model.items.tools.Blowtorch;
import model.items.weapons.LaserSword;
import model.map.rooms.Room;
import model.map.rooms.SpaceRoom;
import model.modes.PirateBackStory;
import model.npcs.behaviors.DoNothingBehavior;
import model.npcs.behaviors.MeanderingMovement;
import model.objects.consoles.ShuttleControlConsole;
import model.objects.general.GameObject;
import util.HTMLText;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

public class CommandablePirateNPC extends PirateNPC implements CommandableNPC {

    private static int num = 0;
    private final String trueName;
    private final Player pirateCap;
    private final PirateBackStory pbs;
    private int commandPointsBase;

    public CommandablePirateNPC(Room position, String trueName, Player pirateCaptain,
                                RolledDownCoverall coverall, PirateBackStory pbs, GameItem[] extraStartingItems) {
        super(position, ++num, position, false, coverall);
        this.trueName = trueName;
        this.pirateCap = pirateCaptain;
        if (pbs.getSkills().contains("Tough")) {
            setHealth(2.5);
            setMaxHealth(2.5);
            commandPointsBase = 110;
        } else {
            setHealth(1.5);
            setMaxHealth(1.5);
            commandPointsBase = 50;
        }
        if (pbs.getSkills().contains("Pilot")) {
            commandPointsBase += 30;
        }
        this.setCharacter(new ReadBackstoryActionGiver(getCharacter()));
        this.pbs = pbs;
        pbs.setPirate(this);
        for (GameItem it : extraStartingItems) {
            getCharacter().giveItem(it, null);
        }
        getItems().removeIf((GameItem it) -> it instanceof SpaceRum);
        setActionBehavior(new DoNothingBehavior());
        setMoveBehavior(new MeanderingMovement(0.25));
    }

    @Override
    public String getPublicName(Actor whosAsking) {
        if (whosAsking == pirateCap) {
            return trueName;
        }
        return super.getPublicName(whosAsking);
    }

    @Override
    public int getCommandPointCost() {
        int sum = 0;
        for (GameItem it : getItems()) {
            sum += it.getCost();
        }
        return commandPointsBase + (sum / 5);
    }

    @Override
    public List<Action> getExtraActionsFor(GameData gameData, NPC npc) {
        ArrayList<Action> acts = new ArrayList<>();
        if (pbs.getSkills().contains("Pilot")) {
            for (GameObject obj : npc.getPosition().getObjects()) {
                if (obj instanceof ShuttleControlConsole) {
                    obj.addSpecificActionsFor(gameData, npc, acts);
                }
            }
        }
        if (pbs.getSkills().contains("Mechanic")) {
           for (GameItem it : npc.getItems()) {
               if (it instanceof Blowtorch || it instanceof LaserSword) {
                   it.addYourActions(gameData, acts, npc);
               }
           }
        }

        return acts;
    }

    private class ReadBackstoryActionGiver extends CharacterDecorator {
        public ReadBackstoryActionGiver(GameCharacter character) {
            super(character, "Read backstory");
        }

        @Override
        public void addActionsForActorsInRoom(GameData gameData, Actor anyActorInRoom, ArrayList<Action> at) {
            super.addActionsForActorsInRoom(gameData, anyActorInRoom, at);
            if (anyActorInRoom == pirateCap) {
                at.add(new ShowBackStoryFancyFrame(gameData, pirateCap, pbs));
            }
        }
    }

    private class ShowBackStoryFancyFrame extends FreeAction {
        private final PirateBackStory backstory;

        public ShowBackStoryFancyFrame(GameData gameData, Player pirateCap, PirateBackStory p2) {
            super("Show " + CommandablePirateNPC.this.getPublicName(pirateCap) + "'s Backstory", gameData, pirateCap);
            this.backstory = p2;
        }

        @Override
        protected void doTheFreeAction(List<String> args, Player p, GameData gameData) {
            p.setFancyFrame(new SinglePageFancyFrame(p.getFancyFrame(), "Pirate Info",
                    HTMLText.makeColoredBackground("orange",
                    HTMLText.makeCentered("<b>" + getPublicName(p) + "</b><br/>" +
                            HTMLText.makeImage(getSprite(p)) + "<br/>" +
                            "<b>Equipment: </b>" + getEquipmentHTML(p) + "<br/>" +
                            "<b>Skills:</b> " + backstory.getSkills() + "<br/>" +
                            "<b>Command Points:</b> " + getCommandPointCost() + "<br/>" +
                            "<i>" + backstory.getDescription() + "</i>"
                    ))));
        }
    }

    private String getEquipmentHTML(Player whosasking) {
        StringBuilder str = new StringBuilder();
        for (GameItem it : getItems()) {
            str.append(HTMLText.makeImage(it.getSprite(whosasking)));
        }
        return str.toString();
    }


}
