package jeu;

import devintAPI.FenetreAbstraite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by user on 17/04/2015.
 */
public class PresJeuMulti extends FenetreAbstraite implements ActionListener {
    public PresJeuMulti(String title) {
        super(title);
        init();
        voix.stop();
        voix.playText("Mode solo. Trouvez les deux paires de carte pour pouvoir commencer la partie !");
    }

    @Override
    protected void init() {

    }

    @Override
    protected String wavAide() {
        return null;
    }

    @Override
    protected String wavAccueil() {
        return null;
    }

    @Override
    protected String wavRegleJeu() {
        return null;
    }

    @Override
    public void changeColor() {

    }

    @Override
    public void changeSize() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
