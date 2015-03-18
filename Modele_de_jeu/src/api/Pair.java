package api;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * 
 */
public class Pair {

    /** */
    private Card first;

    /** */
    private Card second;

    /**
     * Constructor.
     */
    public Pair(String image, String son) {
        first = new Card(image, son);
        second = new Card(image, son);
        first.setTwin(second);
        second.setTwin(first);
    }

    public Card getFirst() {
        return first;
    }

    public Card getSecond() {
        return second;
    }
}
