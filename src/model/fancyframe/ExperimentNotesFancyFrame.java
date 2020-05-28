package model.fancyframe;

import model.GameData;
import model.Player;
import model.items.CosmicArtifact;
import model.items.ExperimentNotes;
import util.HTMLText;

public class ExperimentNotesFancyFrame extends FancyFrame {

    private final ExperimentNotes notes;
    private int page = 0;
    private static final int MAX_PAGE = 18;

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
        if (page == 0) {
            for (CosmicArtifact ca : CosmicArtifact.getAllTypes()) {
                if (notes.getCrossedOut().contains(ca.getBaseName())) {
                    content.append("<strike>" + ca.getBaseName() + "</strike><br/>");
                } else {
                    content.append("" + ca.getBaseName() + " " +
                            HTMLText.makeFancyFrameLink("SCRATCH " + ca.getBaseName(), "[scratch]") + "<br/>");
                }
            }
        }
        return content.toString();
    }

    private void makeTopContent(StringBuilder content) {
        content.append("<center><table width=\"100%\"><tr><td width=\"20%\">");
        if (page > 0) {
            content.append(HTMLText.makeFancyFrameLink("PREV", "[prev]"));
        }
        content.append("</td>");
        content.append("<td width=\"20%\"></td>");
        content.append("<td width=\"20%\" bgcolor=\"black\"></td>");
        content.append("<td width=\"20%\"></td>");
        content.append("<td style=\"text-align:right\" width=\"20%\">");
        if (page < MAX_PAGE) {
            content.append(HTMLText.makeFancyFrameLink("NEXT", "[next]"));
        }
        content.append("</td></tr></table></center>");
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
            buildContent(player, gameData);
        } else {
            return;
        }
        readyThePlayer(gameData, player);
    }
}
