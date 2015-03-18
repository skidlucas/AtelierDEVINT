/** Cette classe est un exemple d'interface pour les options
 */

package jeu;

import t2s.SIVOXDevint; // pour parler

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class displays the game options.
 */
public class OptionInterface extends JFrame implements KeyListener {

    /** The UID. */
    private static final long serialVersionUID = 125086937616300732L;

    /** The instance of <code>SIVOXDevint</code> used to manage vocal signs. */
    SIVOXDevint voix;

    /**
     * Constructor.
     * 
     * @param title
     *            The application title.
     */
    public OptionInterface(String title) {
        super(title);
        this.setVisible(true);
        this.requestFocus();
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        addKeyListener(this);
        voix = new SIVOXDevint(2);
        init();
    }

    /**
     * Creates the content of the frame.
     */
    private void init() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        String text = "Le but du jeu est d'associer les \n";
        text += "cartes entre elles par paire.\n";
        text += "Pour cela, vous pouvez vous servir du clavier (touche \"Entrée\") ou de la souris (clic gauche) pour retourner une carte (voir une image agrandie de cette carte).\n";
        text += "De plus les flèches directionnelles vous permettront de vous déplacer sans utiliser la souris.\n";
        voix.playText(text);
        JTextArea lb1 = new JTextArea(text);
        lb1.setLineWrap(true);
        lb1.setEditable(false);
        lb1.setFont(new Font("Georgia", 1, 80));
        this.add(lb1);
    }

    /**
     * Inherited method.
     * 
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    public void keyReleased(KeyEvent e) {
        // NOTHING TO DO.
    }

    /**
     * Inherited method.
     * 
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    public void keyTyped(KeyEvent e) {
        // NOTHING TO DO.
    }

    /**
     * Inherited method.
     * 
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    public void keyPressed(KeyEvent e) {
        voix.stop();
        // Escape to exit.
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            dispose();
        } else if (e.getKeyCode() == KeyEvent.VK_F1) {
            voix.playWav("../ressources/aideF1.wav");
        } else if (e.getKeyCode() == KeyEvent.VK_F2) {
            voix.playWav("../ressources/aideF2.wav");
        }
    }

}
