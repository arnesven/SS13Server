package model;

/**
 * Created by erini02 on 21/12/16.
 */
public class BooleanWrapper {
    private final boolean inner;
    private final boolean uppercase;

    public BooleanWrapper(boolean ready, boolean uppercase) {
        this.inner = ready;
        this.uppercase = uppercase;
    }

    @Override
    public String toString() {
        
        if (uppercase) {
            if (inner) {
                return "TRUE";
            } else {
                return "FALSE";
            }
        }
        if (inner) {
            return "true";
        }
        return "false";
    }
}
