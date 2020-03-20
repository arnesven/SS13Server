package clientview.animation;



public class AnimationHandler {
    private static int state = 0;

    public static void step() {
        state++;
    }

    public static int getState() {
        return state;
    }

}
