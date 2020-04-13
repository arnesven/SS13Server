package model.fancyframe;

public class SinglePageFancyFrame extends FancyFrame {
    public SinglePageFancyFrame(FancyFrame old, String title, String content) {
        super(old);
        setData(title, false, content);
    }
}
