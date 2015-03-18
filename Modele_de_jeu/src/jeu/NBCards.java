package jeu;

/**
 * This enum type is used to display the number of cards.
 */
public enum NBCards {

    /** A specific type. */
    EASY(6),

    /** A specific type. */
    MEDIUM(9),

    /** A specific type. */
    DIFFICULT(12);

    /** The value associated to this enum. */
    private int value;

    /**
     * Constructor.
     * 
     * @param value
     *            The associated value.
     */
    NBCards(int value) {
        this.value = value;
    }

    /**
     * Inherited method.
     * 
     * @see java.lang.Enum#toString()
     */
    public String toString() {
        return Integer.toString(value*2);
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
