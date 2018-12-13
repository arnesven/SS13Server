package model.plebOS;

import model.GameData;
import model.Player;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.AICharacter;
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
        if (!getActor().getCharacter().checkInstance((GameCharacter gc) -> gc instanceof AICharacter)) {
            checkForLogout(gameData);
        }
    }


    @Override
    public void doAfterActions(GameData gameData) {
        super.doAfterActions(gameData);
        if (!getActor().getCharacter().checkInstance((GameCharacter gc) -> gc instanceof AICharacter)) {
            checkForLogout(gameData);
        }
    }

    private void checkForLogout(GameData gameData) {
        if (loginRoom != getActor().getPosition() || loginInstance.getConsole().isBroken()) {
            loginInstance.logOut(gameData);
        }
    }


}
