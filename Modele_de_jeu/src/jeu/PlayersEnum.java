package jeu;

/**
 * This enum type allows to select players count.
 */
public enum PlayersEnum {

    /** A type for this enum. */
    JOUEUR1(1),

    /** A type for this enum. */
    JOUEUR2(2),

    /** A type for this enum. */
    JOUEUR3(3),

    /** A type for this enum. */
    JOUEUR4(4);

    /** Used by the display. */
    private static final String JOUEUR = " joueur(s).";

    /** The associated value. */
    private int value;

    /**
     * Constructor.
     * 
     * @param value
     *            The associated value.
     */
    PlayersEnum(int value) {
        this.value = value;
    }

    /**
     * Inherited method.
     * 
     * @see java.lang.Enum#toString()
     */
    public String toString() {
        return Integer.toString(value) + JOUEUR;
    }

    /**
     * Getter.
     * 
     * @return The associated value.
     */
    public int getValue() {
        return value;
    }
}
