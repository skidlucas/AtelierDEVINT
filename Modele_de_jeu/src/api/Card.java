package api;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * This is the representation of a card in memory game.
 */
public class Card {

    private Icon faceCarte;
    private String son;
    
    /** The twin card. */
    private Card twin;

    protected Card(String image, String son) {
        faceCarte = new ImageIcon(image);
        this.son = son;
    }
    /**
     * Getter.
     * @return the son
     */
    public String getSon() {
        return son;
    }
    /**
     * Getter.
     * @return the faceCarte
     */
    public Icon getFaceCarte() {
        return faceCarte;
    }

    /**
     * Getter.
     * 
     * @return the twin card instance.
     */
    public Card getTwin() {
        return twin;
    }

    /**
     * Setter.
     * 
     * @param twin
     *            the twin to set.
     */
    void setTwin(Card twin) {
        this.twin = twin;
    }
}
