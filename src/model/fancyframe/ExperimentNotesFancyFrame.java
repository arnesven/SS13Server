package model.fancyframe;

import model.GameData;
import model.Player;
import model.items.CosmicArtifact;
import model.items.ExperimentNotes;
import util.HTMLText;

public class ExperimentNotesFancyFrame extends FancyFrame {

    private final ExperimentNotes notes;
    private int page = 1;
    private int minPage = 1;

    public ExperimentNotesFancyFrame(Player p, GameData gameData, ExperimentNotes notes) {
        super(p.getFancyFrame());
        this.notes = notes;

        buildContent(p, gameData);
    }

    private void buildContent(Player p, GameData gameData) {
        StringBuilder content = new StringBuilder();

        makeTopContent(content);

        content.append("<center><table width=\"90%\" bgcolor=\"white\">");
        content.append("<tr><td>" + getContentForPage(gameData) + "</td></tr>");
        content.append("</table></center>");

        setData("Experiment Notes", false, HTMLText.makeColoredBackground("#77572f", content.toString()));
    }

    private String getContentForPage(GameData gameData) {
        StringBuilder content = new StringBuilder();
        if (page == 1) {
            for (CosmicArtifact ca : CosmicArtifact.getAllTypes()) {
                if (notes.getCrossedOut().contains(ca.getBaseName())) {
                    content.append("<strike>" + ca.getBaseName() + "</strike><br/>");
                } else {
                    content.append("" + ca.getBaseName() + " " +
                            HTMLText.makeFancyFrameLink("SCRATCH " + ca.getBaseName(), "[scratch]") + "<br/>");
                }
            }
        } else if (page == 0) {
            content.append("<center><b>Conclusions on Object 'Monolith' </b><br/><i>Report</i></center>");
            content.append("<br/><i>After various experiment, i have concluded that the object recently " +
                    "delivered to Nanotrasen facility SS13 is a " + notes.getConclusionArtifact().getBaseName() +".<br/><br/>");
            content.append("Please arrange for transport of object ASAP since " + notes.getConclusionArtifact().getEnding() + ".</i>");
        } else {
            CosmicArtifact ca = CosmicArtifact.getAllTypes().get(page-2);
            content.append("<b> Regarding " + ca.getNamePlural() + "</b><br/>");
            content.append("<i>" + ca.getNotesText() + "</i>");
        }
        return content.toString();
    }

    private void makeTopContent(StringBuilder content) {
        content.append("<center><table width=\"100%\"><tr><td width=\"20%\">");
        if (page > minPage) {
            content.append(HTMLText.makeFancyFrameLink("PREV", "[prev]"));
        }
        content.append("</td>");
        content.append("<td width=\"20%\"></td>");
        content.append("<td width=\"20%\" bgcolor=\"black\"></td>");
        content.append("<td width=\"20%\"></td>");
        content.append("<td style=\"text-align:right\" width=\"20%\">");
        if (page < getMaxPage()) {
            content.append(HTMLText.makeFancyFrameLink("NEXT", "[next]"));
        }
        content.append("</td></tr></table></center>");
    }

    private int getMaxPage() {
        return CosmicArtifact.getAllTypes().size() + 1;
    }

    @Override
    public void handleEvent(GameData gameData, Player player, String event) {
        super.handleEvent(gameData, player, event);
        if (event.contains("NEXT")) {
            page++;
            buildContent(player, gameData);
        } else if (event.contains("PREV")) {
            page--;
            buildContent(player, gameData);
        } else if (event.contains("SCRATCH")) {
            notes.getCrossedOut().add(event.replace("SCRATCH ", ""));
            if (notes.checkForReport(gameData, player)) {
                page = 0;
                minPage = 0;
            }
            buildContent(player, gameData);
        } else {
            return;
        }
        readyThePlayer(gameData, player);
    }
}
