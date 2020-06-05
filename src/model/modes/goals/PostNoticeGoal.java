package model.modes.goals;

import model.actions.objectactions.PostNewNoticeAction;

public class PostNoticeGoal extends DidAnActionGoal{
    public PostNoticeGoal(int i) {
        super(i, PostNewNoticeAction.class);
    }

    @Override
    protected String getNoun() {
        return "new notice on the noticeboard";
    }

    @Override
    protected String getVerb() {
        return "post";
    }
}
