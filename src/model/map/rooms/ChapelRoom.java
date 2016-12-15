package model.map.rooms;

import model.objects.general.MailBox;

/**
 * Created by erini02 on 15/12/16.
 */
public class ChapelRoom extends Room {
    public ChapelRoom(int i, int i1, int i2, int i3, int i4, int[] ints, double[] doubles, RoomType support) {
        super(i, "Chapel", "", i1, i2, i3, i4, ints, doubles, support);
        MailBox mail = new MailBox(this);
        addObject(mail);
    }
}
