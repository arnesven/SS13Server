package model.plebOS;

import model.GameData;
import model.Player;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.map.rooms.Room;

public class LoggedInDecorator extends CharacterDecorator {

    private final ComputerSystemSession loginInstance;
    private final Room loginRoom;

    public LoggedInDecorator(GameCharacter chara, String name, ComputerSystemSession computerSystemLogin) {
        super(chara, name);
        this.loginInstance = computerSystemLogin;
        loginRoom = chara.getActor().getPosition();
    }

    public ComputerSystemSession getLogin() {
        return loginInstance;
    }

    @Override
    public String getFullName() {
        return super.getFullName() + " (Logged In)";
    }

    @Override
    public void doAtEndOfTurn(GameData gameData) {
        super.doAtEndOfTurn(gameData);
        checkForLogout(gameData);

    }

    @Override
    public void doAfterMovement(GameData gameData) {
        super.doAfterMovement(gameData);
        checkForLogout(gameData);
    }

    private void checkForLogout(GameData gameData) {
        if (loginRoom != getActor().getPosition()) {
            loginInstance.logOut(gameData);
        }
    }


}