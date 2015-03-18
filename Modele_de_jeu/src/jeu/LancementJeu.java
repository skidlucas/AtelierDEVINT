package jeu;

import javax.swing.JFrame;

/**
 * Main class of the application.
 */
public class LancementJeu {

    /**
     * Used to start the game.
     * 
     * @param args
     */
    public static void main(String args[]) {

        jeu.MenuJeu fenetre;
        fenetre = new jeu.MenuJeu();
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
