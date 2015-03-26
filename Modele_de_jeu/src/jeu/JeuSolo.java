package jeu;

import devintAPI.FenetreAbstraite;
import devintAPI.Preferences;
import jeu.model.Card;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

/**
 * @author Nicolas HORY
 * @version 26/03/2015.
 */
public class JeuSolo extends FenetreAbstraite implements ActionListener {

    private JPanel jp1, jp2;
    private JLabel nomJoueur;
    private JLabel scoreJoueur;
    private JLabel timer;
    private List<Card> returnedCards;
    private List<Card> selectedCards;
    private int nbReturnedCards;
    private int nbCards;
    private List<Card> cards;

    // appel au constructeur de la classe mère
    public JeuSolo(String title, int nbCards) {
        super(title);
        this.nbCards = nbCards;
        init();
    }

    public void initCards(int nbCards) {
        selectedCards = new ArrayList<Card>();
        nbReturnedCards = 0;
        cards = new ArrayList<>();
        for (int i = 0; i < nbCards; i++) {
            Card card = new Card();
            card.setBackground(Preferences.getData().getCurrentBackgroundColor());
            card.setBorder(null);
            cards.add(card);
        }
    }

    // renvoie le fichier wave contenant le message d'accueil
    protected String wavAccueil() {
        return "../ressources/sons/accueil.wav";
    }

    // renvoie le fichier wave contenant la règle du jeu
    protected String wavRegleJeu() {
        return "../ressources/sons/aideF1.wav";
    }

    // renvoie le fichier wave contenant la règle du jeu
    protected String wavAide() {
        return "../ressources/sons/aide.wav";
    }

    // définition de la méthode abstraite "init()"
    // initialise le frame
    protected void init() {// on récupère les couleurs de base dans la classe Preferences
        initCards(nbCards);
        returnedCards = new ArrayList<>();
        setLayout(new BorderLayout());
        Preferences pref = Preferences.getData();
        Color foregroundColor = pref.getCurrentForegroundColor();
        Color backgroundColor = pref.getCurrentBackgroundColor();

        nomJoueur = new JLabel("Nom");
        nomJoueur.setFont(new Font("Georgia", Font.BOLD, 30));
        nomJoueur.setForeground(foregroundColor);
        nomJoueur.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        scoreJoueur = new JLabel("Score");
        scoreJoueur.setFont(new Font("Georgia", Font.BOLD, 30));
        scoreJoueur.setForeground(foregroundColor);
        scoreJoueur.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        timer = new JLabel("Timer");
        timer.setFont(new Font("Georgia", Font.BOLD, 30));
        timer.setForeground(foregroundColor);
        timer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jp1 = new JPanel();
        jp1.setLayout(new GridLayout(0, 3));
        jp1.setBackground(backgroundColor);

        jp2 = new JPanel();
        jp2.setLayout(new GridLayout(3, nbCards/3));
        jp2.setBackground(backgroundColor);

        jp1.add(nomJoueur);
        jp1.add(scoreJoueur);
        jp1.add(timer);

        this.add(jp1, BorderLayout.NORTH);

        for (Card card : cards) {
            card.addActionListener(new CardsListener());
            jp2.add(card);
        }

        this.add(jp2, BorderLayout.CENTER);

        setPairCards();
    }


    public void setPairCards() {
        Stack<String> images = new Stack<>();
        images.add("../ressources/images/girafe.jpg");
        images.add("../ressources/images/girafe.jpg");
        images.add("../ressources/images/girafe.jpg");
        images.add("../ressources/images/girafe.jpg");
        images.add("../ressources/images/girafe.jpg");
        images.add("../ressources/images/girafe.jpg");
        images.add("../ressources/images/girafe.jpg");
        images.add("../ressources/images/girafe.jpg");
        images.add("../ressources/images/girafe.jpg");
        images.add("../ressources/images/girafe.jpg");
        images.add("../ressources/images/girafe.jpg");
        images.add("../ressources/images/girafe.jpg");
        images.add("../ressources/images/lion.jpg");
        images.add("../ressources/images/lion.jpg");
        images.add("../ressources/images/lion.jpg");
        images.add("../ressources/images/lion.jpg");
        images.add("../ressources/images/lion.jpg");
        images.add("../ressources/images/lion.jpg");
        images.add("../ressources/images/lion.jpg");
        images.add("../ressources/images/lion.jpg");
        images.add("../ressources/images/lion.jpg");
        images.add("../ressources/images/lion.jpg");
        images.add("../ressources/images/lion.jpg");
        images.add("../ressources/images/lion.jpg");
        images.add("../ressources/images/lion.jpg");
        images.add("../ressources/images/lion.jpg");


        Random random = new Random();
        for (int i = 0; i < nbCards; i++) {
            int rand;
            do {
                rand = random.nextInt(nbCards);
            } while (!cards.get(rand).isReady());

            cards.get(rand).setImage(images.pop());
        }

    }

    // évènements clavier
    public void keyPressed(KeyEvent e) {
        // appel à la méthode mère qui gère les évènements ESC, F1, F3, F4
        super.keyPressed(e);
        // cas particulier pour ce jeu : la touche F5
        if (e.getKeyCode() == KeyEvent.VK_F5) {
            voix.playText("Vous venez d'appuyer sur EFFE 5");
        }
    }

    /**
     * Pour modifier les couleurs de fond et de premier plan de la fenêtre
     * Cette fonction est appelée par la fonction "changeColor" de la classe "Preferences"
     * à chaque fois que l'on presse F3
     * <p>
     * on change la couleur du texte principal
     */
    public void changeColor() {
        // on récupère les couleurs de base dans la classe Preferences
        Preferences pref = Preferences.getData();
        jp1.setBackground(pref.getCurrentBackgroundColor());
        jp1.setForeground(pref.getCurrentForegroundColor());
        jp2.setBackground(pref.getCurrentBackgroundColor());
        jp2.setForeground(pref.getCurrentForegroundColor());
        scoreJoueur.setBackground(pref.getCurrentBackgroundColor());
        scoreJoueur.setForeground(pref.getCurrentForegroundColor());
        nomJoueur.setBackground(pref.getCurrentBackgroundColor());
        nomJoueur.setForeground(pref.getCurrentForegroundColor());
        timer.setBackground(pref.getCurrentBackgroundColor());
        timer.setForeground(pref.getCurrentForegroundColor());
        for (Card card : cards) {
            card.setBackground(pref.getCurrentBackgroundColor());
        }
    }


    /**
     * Pour modifier la police des textes à chaque fois que l'on presse F4
     */
    public void changeSize() {
        Font f = Preferences.getData().getCurrentFont();
        nomJoueur.setFont(f);
        scoreJoueur.setFont(f);
        timer.setFont(f);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public class CardsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Card sourceCard = (Card) e.getSource();
            sourceCard.turn();
            sourceCard.setEnabled(false);
            sourceCard.setDisabledIcon(new ImageIcon(sourceCard.getImage()));
            selectedCards.add(sourceCard);
            if (selectedCards.size() == 2) {
                if (selectedCards.get(0).getImage().equals(selectedCards.get(1).getImage())) {
                    selectedCards.get(0).setEnabled(false);
                    selectedCards.get(0).setDisabledIcon(new ImageIcon(selectedCards.get(0).getImage()));
                    selectedCards.get(1).setEnabled(false);
                    selectedCards.get(1).setDisabledIcon(new ImageIcon(selectedCards.get(0).getImage()));
                    returnedCards.add(selectedCards.get(0));
                    returnedCards.add(selectedCards.get(1));
                    selectedCards = new ArrayList<Card>();
                    nbReturnedCards += 2;
                    if(nbReturnedCards == nbCards) {
                        System.out.println("end of game");
                    }
                } else {
                    List<Card> disabledCards = new ArrayList<>();
                    for (Card c : cards) {
                        if (!selectedCards.contains(c) && !returnedCards.contains(c)) {
                            disabledCards.add(c);
                            c.setEnabled(false);
                            c.setDisabledIcon(new ImageIcon(c.dosCarte));
                        }
                    }
                    int delay = 2000;
                    java.util.Timer timer = new java.util.Timer();
                    timer.schedule(new TimerTask() {
                        public void run() {
                            selectedCards.get(0).turn();
                            selectedCards.get(0).setEnabled(true);
                            selectedCards.get(1).turn();
                            selectedCards.get(1).setEnabled(true);
                            selectedCards = new ArrayList<Card>();
                            for (Card c : disabledCards) {
                                c.setEnabled(true);
                            }
                        }
                    }, delay);

                }
            }
            requestFocus();
        }
    }
}
