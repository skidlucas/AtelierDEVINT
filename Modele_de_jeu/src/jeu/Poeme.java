package jeu;

import devintAPI.FenetreAbstraite;
import devintAPI.Preferences;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by user on 18/02/2015.
 */
public class Poeme extends FenetreAbstraite implements ActionListener{

    public Poeme(String title) {
        super(title);
    }

    // un label
    // est une variable d'instance car il doit être accessible
    // dans la méthode changeColor, qui gère les préférences
    private JTextArea lb1, f6;
    private JButton bouton;
    String text;

    // renvoie le fichier wave contenant un son vide pour lire directement le poème
    protected  String wavAccueil() {
    return "../ressources/sons/vide.wav";
}

    // renvoie le fichier wave contenant la règle du jeu
    protected  String wavRegleJeu() {
        return "../ressources/sons/aideF1.wav";
    }

    // renvoie le fichier wave contenant la règle du jeu
    protected  String wavAide() {
        return "../ressources/sons/aide.wav";
    }

    public void init() {
        text = "LE MAL, ARTHUR RIMBAUD\n\n";
        text+= "Tandis que les crachats rouges de la mitraille\n" +
                "Sifflent tout le jour par l'infini du ciel bleu ;\n" +
                "Qu'écarlates ou verts, près du Roi qui les raille,\n" +
                "Croulent les bataillons en masse dans le feu ;\n" +
                "\n" +
                "Tandis qu'une folie épouvantable broie\n" +
                "Et fait de cent milliers d'hommes un tas fumant ;\n" +
                "- Pauvres morts ! dans l'été, dans l'herbe, dans ta joie,\n" +
                "Nature ! ô toi qui fis ces hommes saintement !...\n" +
                "\n" +
                "- Il est un Dieu, qui rit aux nappes damassées\n" +
                "Des autels, à l'encens, aux grands calices d'or ;\n" +
                "Qui dans le bercement des hosannah s'endort,\n" +
                "\n" +
                "Et se réveille, quand des mères, ramassées\n" +
                "Dans l'angoisse, et pleurant sous leur vieux bonnet noir,\n" +
                "Lui donnent un gros sou lié dans leur mouchoir ! \n";
        lb1 = new JTextArea (text);
        lb1.setLineWrap(true);
        lb1.setEditable(false);
        // met la police par défaut de DeViNT
        lb1.setFont(Preferences.getData().getCurrentFont());
        this.add(lb1, BorderLayout.CENTER);

        f6 = new JTextArea("F6 pour relire le texte");
        f6.setLineWrap(true);
        f6.setEditable(false);
        f6.setFont(new Font("Arial", 2, 40));
        this.add(f6, BorderLayout.SOUTH);

        voix.play(text, true);

        // bouton pour dire bonjour à tous
        bouton = new JButton();
        bouton.setText("Bonjour à tous");
        bouton.setBackground(new Color(155,150,255));
        bouton.setBorder(new LineBorder(Color.BLACK, 10));
        bouton.setFont(new Font("Georgia",1,40));
        // c'est l'objet Jeu lui-même qui réagit au clic souris
        bouton.addActionListener(this);
        // on met le bouton à droite
        this.add(bouton,BorderLayout.EAST);
    }

    // lire la question si clic sur le bouton
    public void actionPerformed(ActionEvent ae){
        // toujours stopper la voix avant de parler
        voix.stop();
        // on récupère la source de l'évènement
        Object source = ae.getSource();
        // si c'est le bouton "question" on lit la question
        // le contenu des questions est variable donc on les lit avec SI_VOX
        if (source.equals(bouton)) {
            String text = "Bonjour à tous";
            voix.playText(text);
        }
        // on redonne le focus au JFrame principal
        // (après un clic, le focus est sur le bouton)
        this.requestFocus();
    }

    // évènements clavier
    public void keyPressed(KeyEvent e) {
        // appel à la méthode mère qui gère les évènements ESC, F1, F3, F4
        super.keyPressed(e);
        // cas particulier pour ce jeu : la touche F6
        if (e.getKeyCode()==KeyEvent.VK_F6){
            voix.playText(text);
        }
    }

    /**
     * Pour modifier les couleurs de fond et de premier plan
     * Cette fonction est appelée par la fonction "changeColor" de la classe "devintAPI.Preferences"
     * à chaque fois que l'on presse F3
     *
     * ici on décide que le changement des couleurs s'applique sur le label lbl1
     **/
    public  void changeColor() {
        // on récupère les couleurs de base dans la classe Preferences
        Preferences pref = Preferences.getData();
        lb1.setBackground(pref.getCurrentBackgroundColor());
        lb1.setForeground(pref.getCurrentForegroundColor());
        f6.setBackground(pref.getCurrentBackgroundColor());
        f6.setForeground(pref.getCurrentForegroundColor());
    }

    /**
     * Pour modifier la police des textes à chaque fois que l'on presse F4
     */
    public void changeSize(){
        Font f = Preferences.getData().getCurrentFont();
        lb1.setFont(f);
    }

}
