package model.fancyframe;

import graphics.sprites.Sprite;
import model.GameData;
import model.Player;
import model.items.CosmicMonolith;
import model.items.ExperimentNotes;
import sounds.Sound;
import util.HTMLText;
import util.Logger;
import util.MyRandom;

public class ExperimentNotesFancyFrame extends FancyFrame {

    private final ExperimentNotes notes;
    private int page = 1;
    private int minPage = 1;

    public ExperimentNotesFancyFrame(Player p, GameData gameData, ExperimentNotes notes) {
        super(p.getFancyFrame());
        this.notes = notes;
        setWidth(getWidth()+50);
        buildContent(p, gameData);
    }

    private void buildContent(Player p, GameData gameData) {
        StringBuilder content = new StringBuilder();

        makeTopContent(content, gameData);

        content.append("<center><table width=\"90%\" bgcolor=\"white\">");
        content.append("<tr><td>" + getContentForPage(gameData) + "</td></tr>");
        content.append("</table></center>");
        content.append("<br/>");
        if (page == 0) {
            content.append(HTMLText.makeCentered(HTMLText.makeFancyFrameLink("SENDCENTCOM", "[send to CentCom]")));
            content.append(HTMLText.makeCentered("<b>or</b><br/>"));
            content.append(HTMLText.makeCentered(HTMLText.makeFancyFrameLink("STARTOVER", "[shred and start over]")));
        } else if (page == 1) {
            content.append(HTMLText.makeCentered(HTMLText.makeFancyFrameLink("STARTOVER", "[start over]")));
        }
        content.append("<br/>");
        setData("Experiment Notes", false, HTMLText.makeColoredBackground("#87673f", content.toString()));
    }

    private String getContentForPage(GameData gameData) {
        StringBuilder content = new StringBuilder();
        if (page == 1) {
            int index = 2;
            for (CosmicMonolith ca : CosmicMonolith.getAllTypes(gameData)) {
                if (notes.getCrossedOut().contains(ca.getBaseName())) {
                    content.append("<strike>" + ca.getBaseName() + "</strike>");
                } else {
                    content.append(HTMLText.makeFancyFrameLink("SCRATCH " + ca.getBaseName(),
                                    HTMLText.makeText("blue", "sans", 2, "[scratch]📁")) + " " + ca.getBaseName());
                }
                content.append(" " + HTMLText.makeFancyFrameLink("GOTOPAGE " + index, HTMLText.makeText("blue", "sans", 2, "[goto]")) + "<br/>");
                index++;
            }
        } else if (page == 0) {
            content.append("<center><b>Conclusions on Object 'Monolith' </b><br/><i>Report</i></center>");
            content.append("<br/><i>After various experiment, I have concluded that the object recently " +
                    "delivered to Nanotrasen facility SS13 is a <b>" + notes.getConclusionArtifact(gameData).getBaseName() + "</b>. " +
                    "For further details, please see my attached notes.<br/><br/>");
            content.append("Please arrange for transport of object ASAP since " + notes.getConclusionArtifact(gameData).getEnding() + ".</i>");
        } else {
            CosmicMonolith ca = CosmicMonolith.getAllTypes(gameData).get(page-2);
            content.append("<b> Regarding " + ca.getNamePlural() + "</b><br/>");
            content.append("<i>" + ca.getNotesText() + "</i>");
        }
        return content.toString();
    }

    private void makeTopContent(StringBuilder content, GameData gameData) {
        content.append("<center><table width=\"100%\"><tr><td width=\"20%\">");
        if (page > minPage) {
            content.append(HTMLText.makeFancyFrameLink("PREV", "[prev]"));
        }
        content.append("</td>");
        content.append("<td width=\"20%\"></td>");
        content.append("<td width=\"20%\" bgcolor=\"black\" style=\"text-align:center\">");
        if (page != 1) {
            content.append(HTMLText.makeFancyFrameLink("RETURN", HTMLText.makeText("yellow", "[list]")) + "</td>");
        } else {
            content.append("</td>");
        }
        content.append("<td width=\"20%\"></td>");
        content.append("<td style=\"text-align:right\" width=\"20%\">");
        if (page < getMaxPage(gameData)) {
            content.append(HTMLText.makeFancyFrameLink("NEXT", "[next]"));
        }
        content.append("</td></tr></table></center>");
    }

    private int getMaxPage(GameData gameData) {
        return CosmicMonolith.getAllTypes(gameData).size() + 1;
    }

    @Override
    public void handleEvent(GameData gameData, Player player, String event) {
        Logger.log("GOT EVENT " + event);
        super.handleEvent(gameData, player, event);
        if (event.contains("NEXT")) {
            page++;
            buildContent(player, gameData);
            addPageSound(player);
        } else if (event.contains("PREV")) {
            page--;
            buildContent(player, gameData);
            addPageSound(player);
        } else if (event.contains("RETURN")) {
            page = 1;
            buildContent(player, gameData);
            addPageSound(player);
        } else if (event.contains("GOTOPAGE")) {
            page = Integer.parseInt(event.replaceAll("GOTOPAGE ", ""));
            buildContent(player, gameData);
            addPageSound(player);
        } else if (event.contains("SCRATCH")) {
            notes.getCrossedOut().add(event.replace("SCRATCH ", ""));
            if (notes.checkForReport(gameData, player)) {
                page = 0;
                minPage = 0;
            }
            buildContent(player, gameData);
        } else if (event.contains("STARTOVER")) {
            notes.getCrossedOut().clear();
            minPage = 1;
            page = 1;
            buildContent(player, gameData);
        } else if (event.contains("SENDCENTCOM")) {
            notes.sendInReport(player, gameData);
            player.setFancyFrame(new SinglePageFancyFrame(player.getFancyFrame(), "Report Submitted",
                    HTMLText.makeCentered(HTMLText.makeText("White", "<br/>" +
                            HTMLText.makeImage(new Sprite("ss13logo32", "ss13_32.png", 0, null)) +
                            "<br/><br/>You have submitted your report on the Monolith to Central Command.<br/><br/>" +
                            "It is now pending peer review.<br/><br/><i>(Crew points will be rewarded at the end" +
                            " of the game if the conclusion was correct.)</i>"))));
        }

        if (!event.contains("SCRATCH")) {
            readyThePlayer(gameData, player);
        }
    }

    private void addPageSound(Player player) {
        int i = MyRandom.nextInt(3) + 1;
        player.getSoundQueue().add(new Sound("pageturn" + i));
    }
}
