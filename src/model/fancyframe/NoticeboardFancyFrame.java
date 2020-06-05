package model.fancyframe;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.PostNewNoticeAction;
import model.objects.general.Noticeboard;
import util.HTMLText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoticeboardFancyFrame extends FancyFrame {

    private final Noticeboard noticeboard;
    private final ArrayList<String> rowContent;
    private boolean posting;
    private boolean posted;

    public NoticeboardFancyFrame(Player performingClient, GameData gameData, Noticeboard noticeboard) {
        super(performingClient.getFancyFrame());
        this.noticeboard = noticeboard;
        setWidth(getWidth() * 2);
        posting = false;
        posted = false;

        rowContent = new ArrayList<>();
        rowContent.addAll(noticeboard.getNotes());
        while (rowContent.size() % 5 != 0) {
            rowContent.add("");
        }
        Collections.shuffle(rowContent);


        buildContent(gameData, performingClient);
    }

    private void buildContent(GameData gameData, Player performingClient) {
        StringBuilder content = new StringBuilder();

        content.append("<center><table width=\"100%\" border=\"1\">");

        for (int row = 1; row < rowContent.size() / 5 + 1; ++row) {
            content.append("<tr>");
            for (int i = 0; i < 5; ++i) {
                String note = rowContent.get((row - 1) * 5 + i);
                if (note.equals("")) {
                    content.append("<td width=\"20%\"></td>");
                } else {
                    content.append("<td width=\"20%\" bgcolor=\"white\" style=\"vertical-align:top\"><i>" +
                            HTMLText.makeText("black", "serif", 2, note) + "</i></td>");
                }
            }
            content.append("</tr>");
        }
        content.append("</table></center>");
        if (posted) {
            content.append(HTMLText.makeCentered("Posting..."));
        } else if (posting) {
            content.append(HTMLText.makeCentered("Please write your message in the input field below. You may use // to symbolize new lines."));
        } else {
            content.append(HTMLText.makeCentered(HTMLText.makeFancyFrameLink("POSTNEW", "[post new note]")));
        }

        setData("Noticeboard - " + noticeboard.getPosition().getName(), posting,
                HTMLText.makeColoredBackground("#8e7348", content.toString()));
    }

    @Override
    public void handleEvent(GameData gameData, Player player, String event) {
        super.handleEvent(gameData, player, event);
        if (event.equals("POSTNEW")) {
            posting = true;
            buildContent(gameData, player);
        }
    }

    @Override
    public void handleInput(GameData gameData, Player player, String data) {
        super.handleInput(gameData, player, data);
        posted = true;
        posting = false;
        Action postingAction = new PostNewNoticeAction(this);
        List<String> args = new ArrayList<>();
        data = data.replaceAll("//", "<br/>");
        args.add(data);
        postingAction.setActionTreeArguments(args, player);
        player.setNextAction(postingAction);
        readyThePlayer(gameData, player);
        buildContent(gameData, player);
    }

    public void reset(GameData gameData, Player performingClient, String newText) {
        posted = false;
        posting = false;
        int index = -1;
        for (int i = 0; i < rowContent.size(); ++i) {
            if (rowContent.get(i).equals("")) {
                index = i;
            }
        }

        if (index == -1) {
            index = rowContent.size();
            for (int i = 4; i > 0; --i) {
                rowContent.add("");
            }
        }

        rowContent.set(index, newText);
        buildContent(gameData, performingClient);
    }
}
