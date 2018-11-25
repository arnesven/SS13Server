package model.plebOS;

import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;

public class LoggedInDecorator extends CharacterDecorator {

    private final ComputerSystemLogin loginInstance;

    public LoggedInDecorator(GameCharacter chara, String name, ComputerSystemLogin computerSystemLogin) {
        super(chara, name);
        this.loginInstance = computerSystemLogin;
    }

    public ComputerSystemLogin getLogin() {
        return loginInstance;
    }
}
