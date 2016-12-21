package model.modes;

/**
 * Created by erini02 on 20/12/16.
 */
public class GameCouldNotBeStartedException extends RuntimeException {
    public GameCouldNotBeStartedException(String mess) {
        super(mess);
    }
}
