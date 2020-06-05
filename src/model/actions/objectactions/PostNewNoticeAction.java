package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.fancyframe.NoticeboardFancyFrame;
import model.objects.general.GameObject;
import model.objects.general.Noticeboard;

import java.util.List;

public class PostNewNoticeAction extends Action {
    private final NoticeboardFancyFrame fancyFrame;
    private String text;

    public PostNewNoticeAction(NoticeboardFancyFrame noticeboardFancyFrame) {
        super("Post New Notice", SensoryLevel.PHYSICAL_ACTIVITY);
        this.fancyFrame = noticeboardFancyFrame;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "posted a note";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        Noticeboard noticeboard = null;
        for (GameObject obj : performingClient.getPosition().getObjects()) {
            if (obj instanceof Noticeboard) {
                noticeboard = (Noticeboard)obj;
            }
        }
        if (noticeboard == null) {
            performingClient.addTolastTurnInfo("What, no noticeboard to post on? " + failed(gameData, performingClient));
            return;
        }
        noticeboard.addNote(text);
        fancyFrame.reset(gameData, (Player)performingClient, text);
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        this.text = args.get(0);
    }
}
