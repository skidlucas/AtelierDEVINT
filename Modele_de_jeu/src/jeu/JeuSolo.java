package jeu;

import devintAPI.FenetreAbstraite;
import devintAPI.Preferences;
import jeu.model.Card;
import jeu.scores.Score;
import jeu.scores.ScoreManager;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;

/**
 * @author Nicolas HORY
 * @version 26/03/2015.
 */
public class JeuSolo extends FenetreAbstraite implements ActionListener {
    private ScoreManager scoreManager;

    private String strNomJoueur;
    private int minute,seconde;
    private JPanel jp1, jp2;
    private JLabel nomJoueur;
    private JLabel scoreJoueur;
    private JLabel timer;
    private int nbPoints;
    int selectedCard = 0;
    private List<Card> returnedCards;
    private List<Card> selectedCards;
    private int nbCards;
    private int pointsFail;
    private int pointsSuccess;
    private List<Card> cards;
    private Timer chrono;
    private JPanel endOfGame;
    private JButton goToScores;
    private JButton quitterGame;
    private  JLabel imageFin;
    private JPanel messageFin;
    private JLabel textMsg;
    private JPanel boutonsFin;

    // appel au constructeur de la classe mère
    public JeuSolo(String title, int nbCards, String nomJoueur) {
        super(title);
        scoreManager = new ScoreManager();
        this.strNomJoueur = nomJoueur;
        selectedCard = 0;
        minute = 0;
        seconde = 0;
        this.pointsFail = -1;
        switch(nbCards) {
            case 12: this.pointsSuccess = 3; break;
            case 18: this.pointsSuccess = 4; break;
            case 24: this.pointsSuccess = 5; break;
        }
        chrono = new Timer(1000, new ChronoListener());
        chrono.start();
        nbPoints = 0;
        this.nbCards = nbCards;
        init();
    }

    public void initCards(int nbCards) {
        selectedCards = new ArrayList<Card>();
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
        boutonsFin = new JPanel();
        messageFin = new JPanel();
        textMsg = new JLabel();
        imageFin = new JLabel(new ImageIcon("../ressources/images/finJeu.png"));
        goToScores = new JButton("Scores");
        quitterGame = new JButton("Quitter");
        endOfGame = new JPanel();
        endOfGame.setLayout(new GridLayout(2,0));
        returnedCards = new ArrayList<>();
        setLayout(new BorderLayout());
        Preferences pref = Preferences.getData();
        Color foregroundColor = pref.getCurrentForegroundColor();
        Color backgroundColor = pref.getCurrentBackgroundColor();

        nomJoueur = new JLabel(strNomJoueur);
        nomJoueur.setFont(new Font("Georgia", Font.BOLD, 30));
        nomJoueur.setForeground(foregroundColor);
        nomJoueur.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        scoreJoueur = new JLabel("Score : " + nbPoints);
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

        cards.get(selectedCard).setBorder(BorderFactory.createLineBorder(Preferences.getData().getCurrentForegroundColor(), 6));
                setPairCards();
    }


    public void setPairCards() {
        String pathImage = "../ressources/images/";
        String pathSon = "../ressources/sons/";

        Stack<String> images = new Stack<>();
        images.add("loup");
        images.add("loup");
        images.add("vache");
        images.add("vache");
        images.add("dauphin");
        images.add("dauphin");
        images.add("chien");
        images.add("chien");
        images.add("ours");
        images.add("ours");
        images.add("elephant");
        images.add("elephant");
        images.add("cheval");
        images.add("cheval");
        images.add("chevre");
        images.add("chevre");
        images.add("singe");
        images.add("singe");
        images.add("hibou");
        images.add("hibou");
        images.add("chat");
        images.add("chat");
        images.add("lion");
        images.add("lion");

        Random random = new Random();
        for (int i = 0; i < nbCards; i++) {
            int rand;
            do {
                rand = random.nextInt(nbCards);
            } while (!cards.get(rand).isReady());

            cards.get(rand).setImage(pathImage + images.peek() + ".jpg");
            cards.get(rand).setSonCard(pathSon + images.pop() + ".wav");
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

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            cards.get(selectedCard).doClick();
        }

        // Toujours 3 lignes
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            // Supprime la border actuel
            cards.get(selectedCard).setBorder(null);
            selectedCard -= (nbCards/3);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            // Supprime la border actuel
            cards.get(selectedCard).setBorder(null);
            --selectedCard;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            // Supprime la border actuel
            cards.get(selectedCard).setBorder(null);
            selectedCard += (nbCards/3);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            // Supprime la border actuel
            cards.get(selectedCard).setBorder(null);
            ++selectedCard;
        }

        if (selectedCard < 0) {
            selectedCard = 0;
        }
        if (selectedCard >= nbCards) {
            selectedCard = nbCards-1;
        }

        cards.get(selectedCard).setBorder(BorderFactory.createLineBorder(Preferences.getData().getCurrentForegroundColor(), 6));
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
        endOfGame.setBackground(pref.getCurrentBackgroundColor());
        endOfGame.setForeground(pref.getCurrentForegroundColor());
        imageFin.setBackground(pref.getCurrentBackgroundColor());
        imageFin.setForeground(pref.getCurrentForegroundColor());
        messageFin.setBackground(pref.getCurrentBackgroundColor());
        messageFin.setForeground(pref.getCurrentForegroundColor());
        textMsg.setBackground(pref.getCurrentBackgroundColor());
        textMsg.setForeground(pref.getCurrentForegroundColor());
        goToScores.setBackground(pref.getCurrentForegroundColor());
        goToScores.setForeground(pref.getCurrentBackgroundColor());
        quitterGame.setBackground(pref.getCurrentForegroundColor());
        quitterGame.setForeground(pref.getCurrentBackgroundColor());
        goToScores.setBorder(new LineBorder(pref.getCurrentBackgroundColor(), 5));
        quitterGame.setBorder(new LineBorder(pref.getCurrentBackgroundColor(), 5));
        for (Card card : cards) {
            card.setBackground(pref.getCurrentBackgroundColor());
        }
    }

    public void changeViewEndGame () {
        Preferences pref = Preferences.getData();
        endOfGame.setBackground(pref.getCurrentBackgroundColor());
        endOfGame.setForeground(pref.getCurrentForegroundColor());
        remove(jp1);
        remove(jp2);
        endOfGame.add(imageFin);
        messageFin = new JPanel();
        messageFin.setLayout(new BorderLayout());
        messageFin.setBackground(pref.getCurrentBackgroundColor());
        String msgEnd = "<html>Vous avez fini la partie avec " + Integer.toString(nbPoints) + " points<br> en" + Integer.toString(minute) + " minute(s) et" + Integer.toString(seconde) + " seconde(s)</html>\"";
        voix.playText(msgEnd);
        textMsg.setText("<html>Vous avez fini la partie avec " + nbPoints + " points<br> en " + minute + " minute(s) et " + seconde + " seconde(s)</html>");
        textMsg.setFont(new Font("Georgia", Font.BOLD, 30));
        textMsg.setBackground(pref.getCurrentBackgroundColor());
        textMsg.setForeground(pref.getCurrentForegroundColor());
        textMsg.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        messageFin.add(textMsg, BorderLayout.CENTER);
        boutonsFin.setLayout(new GridLayout(0, 2));
        goToScores.setBackground(pref.getCurrentForegroundColor());
        goToScores.setBorder(new LineBorder(pref.getCurrentBackgroundColor(), 5));
        goToScores.setFont(new Font("Georgia", 1, 40));
        goToScores.setForeground(pref.getCurrentBackgroundColor());
        goToScores.addActionListener(this);
        quitterGame.setBackground(pref.getCurrentForegroundColor());
        quitterGame.setBorder(new LineBorder(pref.getCurrentBackgroundColor(), 5));
        quitterGame.setFont(new Font("Georgia", 1, 40));
        quitterGame.setForeground(pref.getCurrentBackgroundColor());
        quitterGame.addActionListener(this);
        boutonsFin.add(goToScores);
        boutonsFin.add(quitterGame);
        goToScores.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        quitterGame.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        quitterGame.addActionListener(new QuitterJeuSoloListener());
        messageFin.add(boutonsFin, BorderLayout.SOUTH);
        endOfGame.add(messageFin);
        add(endOfGame, BorderLayout.CENTER);
    }


    /**
     * Pour modifier la police des textes à chaque fois que l'on presse F4
     */
    public void changeSize() {
        Font f = Preferences.getData().getCurrentFont();
        nomJoueur.setFont(f);
        scoreJoueur.setFont(f);
        timer.setFont(f);
        messageFin.setFont(f);
        textMsg.setFont(f);
        boutonsFin.setFont(f);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // toujours stopper la voix avant de parler
        voix.stop();
        // on récupère la source de l'évènement
        Object source = e.getSource();
        // on redonne le focus au JFrame principal
        // (après un clic, le focus est sur le bouton)
        this.requestFocus();
    }

    public class CardsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Card sourceCard = (Card) e.getSource();
            sourceCard.turn();
            voix.forceStop();
            voix.playWav(sourceCard.getSon(), true);
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
                    voix.playWav("../ressources/sons/reussite.wav", true);
                    nbPoints += pointsSuccess;
                    if (nbPoints < 0) nbPoints = 0;
                    scoreJoueur.setText("Score : " + nbPoints);
                    if(returnedCards.size() == nbCards) {
                        voix.forceStop();
                        chrono.stop();

                        // Add score in json file
                        scoreManager.add(new Score(nomJoueur.getText(), nbPoints, timer.getText()));

                        changeViewEndGame();
                    }
                } else {
                    List<Card> disabledCards = new ArrayList<>();
                    voix.playWav("../ressources/sons/echec.wav", true);
                    for (Card c : cards) {
                        if (!selectedCards.contains(c) && !returnedCards.contains(c)) {
                            disabledCards.add(c);
                            c.setEnabled(false);
                            c.setDisabledIcon(new ImageIcon(c.dosCarte));
                        }
                    }
                    nbPoints += pointsFail;
                    if (nbPoints < 0) nbPoints = 0;
                    scoreJoueur.setText("Score : " + nbPoints);
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

    private class ChronoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
                seconde++;
                if(seconde==60) {
                    seconde = 0;
                    minute++;
                }
            if (minute < 10 && seconde < 10) {
                timer.setText("0"+minute + ":" + "0"+seconde);
            } else if (minute < 10) {
                timer.setText("0"+minute + ":" + seconde);
            } else if (seconde < 10) {
                timer.setText(minute + ":" + "0" + seconde);
            } else {
                timer.setText(minute + ":" + seconde);
            }
        }
    }

    private class QuitterJeuSoloListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}
