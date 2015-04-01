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
    private String sonCard;

    public Card() {
        super(new ImageIcon(dosCarte));
    }

    public String getSon() {
        return this.sonCard;
    }
    public boolean isReady() {
        return image.equals(dosCarte);
    }

    public void setSonCard (String son) {
        this.sonCard = son;
    }

    public void turn() {
        if (visible) {
            this.setIcon(new ImageIcon(dosCarte));
        } else {
            this.setIcon(new ImageIcon(image));
        }

        visible = !visible;
    }

    public String getImage () {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
