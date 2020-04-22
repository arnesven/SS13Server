package model;

import model.items.foods.Doughnut;
import model.items.general.GameItem;
import model.items.general.MoneyStack;
import model.items.suits.SunGlasses;
import model.items.weapons.Crowbar;
import util.HTMLText;
import util.Logger;

import java.io.Serializable;
import java.util.List;

public class CharacterCreation implements Serializable {

    private final List<GameItem> list;
    private GameItem selected = null;

    public CharacterCreation() {
        list = List.of(new Doughnut(null), new Crowbar(), new SunGlasses(), new MoneyStack(15));
    }

    public String getHTML() {
        StringBuilder offeredItems = new StringBuilder();

        for (GameItem it : list) {
            offeredItems.append("<li>" + HTMLText.makeFancyFrameLink("EXTRAITEM " + it.getBaseName(), it.getFullName(null)) +
                    HTMLText.makeImage(it.getSprite(null)) + (it==selected?" YES PLEASE! ":"") + "</li>");
        }

        return HTMLText.makeColoredBackground("white",
                "<h2> " +"Character Creation!" + "</h2>" +
                "Please customize your appearance!</p><br/><br/><br/>" +
                HTMLText.makeCentered("<b><i>Special Offer!</b></i>") +
                "<p>Nanotrasen offers any crew member some extra starting gear, for FREE!<br/><ul>" +
                        offeredItems.toString()
        + "</ul>");
    }

    public void update(String event, GameData gameData) {
        String itemWanted = event.replace("EXTRAITEM ", "");
        Logger.log("Got update for event:" + event + ", smurfing for " + itemWanted);
        for (GameItem it : list) {
            if (it.getBaseName().equals(itemWanted)) {
                this.selected = it;
            }
        }
    }

    public void doAtGameStart(GameData gameData, Player pl) {
        if (selected != null) {
            pl.addItem(selected, null);
        }
    }
}
