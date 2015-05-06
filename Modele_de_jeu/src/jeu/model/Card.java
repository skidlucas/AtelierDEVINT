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
    private String theme;

    public Card() {
        super(new ImageIcon(dosCarte));
        theme = "";
    }

    public boolean isReady() {
        return image.equals(dosCarte);
    }

    public boolean isReady(String theme) {
        return !image.equals(dosCarte) && this.theme.equals(theme);
    }

    public void turn() {
        if (visible) {
            this.setIcon(new ImageIcon(dosCarte));
        } else {
            this.setIcon(new ImageIcon(image));
        }

        visible = !visible;
    }

    public String getSon() {
        return this.sonCard;
    }

    public void setSonCard (String son) {
        this.sonCard = son;
    }

    public String getImage () {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
