package model.map.rooms;

import model.GameData;
import model.map.doors.Door;
import model.objects.consoles.RequisitionsConsole;
import model.objects.consoles.PersonnelConsole;
import model.objects.decorations.OfficeChair;
import model.objects.general.MailBox;

/**
 * Created by erini02 on 17/12/16.
 */
public class OfficeRoom extends CommandRoom {
    public OfficeRoom(GameData gameData, int id, int x, int y, int w, int h, int[] ints, Door[] doubles) {
        super(id, "Office", "Offc", x, y, w, h, ints, doubles);
        this.addObject(new RequisitionsConsole(this, gameData));
        this.addObject(new PersonnelConsole(this, gameData));
        MailBox mail = new MailBox(this);
        addObject(mail);
        addObject(new OfficeChair(this));
    }

    @Override
    protected String getWallAppearence() {
        return "light";
    }
}
