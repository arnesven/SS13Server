package model.map.rooms;

import model.GameData;
import model.map.doors.Door;
import model.objects.consoles.RequisitionsConsole;
import model.objects.consoles.PersonnelConsole;
import model.objects.decorations.OfficeChair;
import model.objects.general.GameObject;
import model.objects.general.MailBox;

/**
 * Created by erini02 on 17/12/16.
 */
public class OfficeRoom extends CommandRoom {
    public OfficeRoom(GameData gameData, int id, int x, int y, int w, int h, int[] ints, Door[] doubles) {
        super(id, "Office", "Offc", x, y, w, h, ints, doubles);
        this.addObject(new RequisitionsConsole(this, gameData), RelativePositions.UPPER_RIGHT_CORNER);
        GameObject console = new PersonnelConsole(this, gameData);
        this.addObject(console, RelativePositions.LOWER_RIGHT_CORNER);
        MailBox mail = new MailBox(this);
        addObject(mail, RelativePositions.MID_LEFT);
        addObject(new OfficeChair(this), new RelativePositions.NorthOf(console));
    }

    @Override
    protected String getWallAppearence() {
        return "light";
    }
}
