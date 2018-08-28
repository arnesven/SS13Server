package model.map.rooms;

import model.objects.general.MailBox;

/**
 * Created by erini02 on 15/12/16.
 */
public class ChapelRoom extends Room {
    public ChapelRoom(int id, int x, int y, int w, int h, int[] ints, double[] doubles, RoomType support) {
        super(id, "Chapel", "Chap", x, y, w, h, ints, doubles, support);
        MailBox mail = new MailBox(this);
        addObject(mail);
    }
}
