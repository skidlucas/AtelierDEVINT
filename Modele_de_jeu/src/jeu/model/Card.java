package jeu.model;

import javax.swing.*;

/**
 * @author Misuke
 * @version 25/03/15
 */
public class Card extends JButton {
    public static final String dosCarte = "../ressources/images/dosCarte.jpg";

    private boolean visible = false;
    private String image = "../ressources/images/dosCarte.jpg";

    public Card() {
        super(new ImageIcon(dosCarte));
    }

    public boolean isReady() {
        return image.equals(dosCarte);
    }

    public void turn() {
        if (visible) {
            this.setIcon(new ImageIcon(dosCarte));
        } else {
            this.setIcon(new ImageIcon(image));
        }

        visible = !visible;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
