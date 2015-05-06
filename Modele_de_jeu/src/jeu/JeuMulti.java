package jeu;

import devintAPI.FenetreAbstraite;
import devintAPI.Preferences;
import jeu.model.Card;
import jeu.model.Profil;

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
 * Created by user on 17/04/2015.
 */
public class JeuMulti extends FenetreAbstraite implements ActionListener {

    private Profil currentProfil = new Profil();
    private JPanel jp1, jp2, jp3;
    private JLabel nomJoueur1, nomJoueur2;
    private JLabel scoreJoueur1, scoreJoueur2;
    private int nbPairesJoueur1, nbPairesJoueur2, nbPairesJoueurGagnant;
    private String strNomJoueur1, strNomJoueur2, strNomJoueurGagnant;
    int selectedCard = 0;
    private List<Card> returnedCards;
    private List<Card> selectedCards;
    private int nbCards;
    private boolean tour;
    //tour = false => joueur 1, tour = true => joueur2
    private List<Card> cards;
    private JPanel endOfGame;
    private JButton goToScores;
    private JButton quitterGame;
    private  JLabel imageFin;
    private JPanel messageFin;
    private JLabel textMsg;
    private JPanel boutonsFin;

    public JeuMulti(String title, String nomJoueur1, String nomJoueur2) {
        super(title);
        tour = false;
        this.strNomJoueur1 = nomJoueur1;
        this.strNomJoueur2 = nomJoueur2;
        selectedCard = 0;
        nbPairesJoueur1 = 0;
        nbPairesJoueur2 = 0;
        this.nbCards = 18;
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



        nomJoueur1 = new JLabel(strNomJoueur1);
        nomJoueur1.setFont(new Font("Georgia", Font.BOLD, 30));
        nomJoueur1.setForeground(foregroundColor);
        nomJoueur1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        scoreJoueur1 = new JLabel("Score : " + nbPairesJoueur1);
        scoreJoueur1.setFont(new Font("Georgia", Font.BOLD, 30));
        scoreJoueur1.setForeground(foregroundColor);
        scoreJoueur1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        nomJoueur2 = new JLabel(strNomJoueur2);
        nomJoueur2.setFont(new Font("Georgia", Font.BOLD, 30));
        nomJoueur2.setForeground(foregroundColor);
        nomJoueur2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        scoreJoueur2 = new JLabel("Score : " + nbPairesJoueur2);
        scoreJoueur2.setFont(new Font("Georgia", Font.BOLD, 30));
        scoreJoueur2.setForeground(foregroundColor);
        scoreJoueur2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);


        jp1 = new JPanel();
        jp1.setLayout(new GridLayout(2, 2));
        jp1.setBackground(backgroundColor);

        jp2 = new JPanel();
        jp2.setLayout(new GridLayout(3, nbCards/3));
        jp2.setBackground(backgroundColor);

        jp3 = new JPanel();
        jp3.setLayout(new GridLayout(2, 2));
        jp3.setBackground(backgroundColor);

        jp1.add(nomJoueur1);
        jp1.add(scoreJoueur1);
        jp3.add(nomJoueur2);
        jp3.add(scoreJoueur2);

        this.add(jp1, BorderLayout.WEST);
        this.add(jp3, BorderLayout.EAST);

        for (Card card : cards) {
            card.addActionListener(new CardsListener());
            jp2.add(card);
        }

        this.add(jp2, BorderLayout.CENTER);

        currentProfil = Utils.getProfilFromName(strNomJoueur1);
        Utils.changeParam(currentProfil);
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
        jp3.setBackground(pref.getCurrentBackgroundColor());
        jp3.setForeground(pref.getCurrentForegroundColor());
        scoreJoueur1.setBackground(pref.getCurrentBackgroundColor());
        scoreJoueur1.setForeground(pref.getCurrentForegroundColor());
        scoreJoueur2.setBackground(pref.getCurrentBackgroundColor());
        scoreJoueur2.setForeground(pref.getCurrentForegroundColor());
        nomJoueur1.setBackground(pref.getCurrentBackgroundColor());
        nomJoueur1.setForeground(pref.getCurrentForegroundColor());
        nomJoueur2.setBackground(pref.getCurrentBackgroundColor());
        nomJoueur2.setForeground(pref.getCurrentForegroundColor());
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
        remove(jp3);
        endOfGame.add(imageFin);
        messageFin = new JPanel();
        messageFin.setLayout(new BorderLayout());
        messageFin.setBackground(pref.getCurrentBackgroundColor());
        String msgEnd = "<html>Le joueur " + strNomJoueurGagnant + " a gagné avec " + nbPairesJoueurGagnant + " points<br></html>";
        voix.playText(msgEnd);
        textMsg.setText(msgEnd);
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
        nomJoueur1.setFont(f);
        scoreJoueur1.setFont(f);
        nomJoueur2.setFont(f);
        scoreJoueur2.setFont(f);
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

            //si deux cartes retournées
            if (selectedCards.size() == 2) {
                //si c'est les mêmes
                if (selectedCards.get(0).getImage().equals(selectedCards.get(1).getImage())) {
                    selectedCards.get(0).setEnabled(false);
                    selectedCards.get(0).setDisabledIcon(new ImageIcon(selectedCards.get(0).getImage()));
                    selectedCards.get(1).setEnabled(false);
                    selectedCards.get(1).setDisabledIcon(new ImageIcon(selectedCards.get(0).getImage()));
                    returnedCards.add(selectedCards.get(0));
                    returnedCards.add(selectedCards.get(1));
                    selectedCards = new ArrayList<Card>();
                    voix.playWav("../ressources/sons/reussite.wav", true);

                    //joueur1
                    if (!tour) {
                        ++nbPairesJoueur1;
                        scoreJoueur1.setText("Score : " + nbPairesJoueur1);
                    } else { //joueur2
                        ++nbPairesJoueur2;
                        scoreJoueur2.setText("Score : " + nbPairesJoueur2);
                    }
                    if (returnedCards.size() == nbCards) {
                        voix.forceStop();
                        if (nbPairesJoueur1 > nbPairesJoueur2) {
                            strNomJoueurGagnant = strNomJoueur1;
                            nbPairesJoueurGagnant = nbPairesJoueur1;
                        } else {
                            strNomJoueurGagnant = strNomJoueur2;
                            nbPairesJoueurGagnant = nbPairesJoueur2;
                        }
                        changeViewEndGame();
                    }
                //si c'est pas les mêmes
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
                            tour = !tour;
                            if (!tour) {
                                currentProfil = Utils.getProfilFromName(strNomJoueur1);
                            } else {
                                currentProfil = Utils.getProfilFromName(strNomJoueur2);
                            }
                            Utils.changeParam(currentProfil);
                            cards.get(selectedCard).setBorder(BorderFactory.createLineBorder(Preferences.getData().getCurrentForegroundColor(), 6));
                        }
                    }, delay);
                }
            }
            requestFocus();
        }
    }

    private class QuitterJeuSoloListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}
