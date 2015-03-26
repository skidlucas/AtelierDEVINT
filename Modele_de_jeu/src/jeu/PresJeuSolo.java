package jeu;

import javax.swing.*;
import javax.swing.border.LineBorder;

import devintAPI.FenetreAbstraite;
import devintAPI.Preferences;
import jeu.model.Card;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Timer;

/** Cette classe est un exemple d'interface de jeu.
 *  Elle étend DevintFrame pour avoir un Frame et réagir aux évênements claviers
 * Implémente ActionListener pour réagir au clic souris sur le bouton.
 * On surchage la méthode "keyPressed" pour associer une action à la touche F3
 * 
 * @author helene
 *
 */

public class PresJeuSolo extends FenetreAbstraite implements ActionListener{

	private JComboBox themesList, difficultesList;
    private JLabel theme, difficulte, text;
    private JButton bouton;
    private JPanel jp1, jp2, jp3;

    private List<Card> selectedCards;
    private int nbReturnedCards;
    private int nbCards;
    private List<Card> cards;

	// appel au constructeur de la classe mère
    public PresJeuSolo(String title) {
    	super(title);
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
	protected  String wavAccueil() {
		return "../ressources/sons/accueil.wav";
	}
	
	// renvoie le fichier wave contenant la règle du jeu
	protected  String wavRegleJeu() {
		return "../ressources/sons/aideF1.wav";
	}
	
	// renvoie le fichier wave contenant la règle du jeu
	protected  String wavAide() {
		return "../ressources/sons/aide.wav";
	}

    // définition de la méthode abstraite "init()"
    // initialise le frame 
    protected void init() {// on récupère les couleurs de base dans la classe Preferences
        nbCards = 4;
        initCards(nbCards);

        bouton = new JButton("Commencer");
        Preferences pref = Preferences.getData();
        Color foregroundColor = pref.getCurrentForegroundColor();
        Color backgroundColor = pref.getCurrentBackgroundColor();
    	setLayout(new GridLayout(3, 0));
        jp1 = new JPanel();
        jp1.setLayout(new GridLayout(2, 2));
        jp1.setBackground(backgroundColor);

        jp2 = new JPanel();
        jp2.setLayout(new BorderLayout());
        jp2.setBackground(backgroundColor);

        jp3 = new JPanel();
        jp3.setLayout(new GridLayout(0,nbCards));
        jp3.setBackground(backgroundColor);


        String[] themes = {"Animaux", "Fruits", "Caractères chinois" };
        themesList = new JComboBox(themes);
        themesList.setSelectedIndex(0);
    	themesList.setEditable(false);
    	themesList.setFont(new Font("Georgia", 1, 30));
		themesList.setBackground(backgroundColor);
		themesList.setForeground(foregroundColor);
        themesList.addActionListener(this);

        String[] difficultes = {"Facile", "Moyen", "Difficile"};
        difficultesList = new JComboBox(difficultes);
        difficultesList.setSelectedIndex(0);
        difficultesList.setEditable(false);
        difficultesList.setFont(new Font("Georgia", 1, 30));
        difficultesList.setBackground(backgroundColor);
        difficultesList.setForeground(foregroundColor);
        difficultesList.addActionListener(this);

        theme = new JLabel("Thème");
        theme.setFont(new Font("Georgia", Font.BOLD, 30));
        theme.setForeground(foregroundColor);

        difficulte = new JLabel("Difficulté");
        difficulte.setFont(new Font("Georgia", Font.BOLD, 30));
        difficulte.setForeground(foregroundColor);

        bouton.setBackground(foregroundColor);
        bouton.setBorder(new LineBorder(Color.BLACK, 5));
        bouton.setFont(new Font("Georgia", 1, 40));
        bouton.setForeground(backgroundColor);
        bouton.addActionListener(this);

        text = new JLabel("Trouvez les paires de cartes pour pouvoir commencer");
        text.setFont(new Font("Georgia", Font.BOLD, 30));
        text.setForeground(foregroundColor);
        text.setHorizontalAlignment(SwingConstants.HORIZONTAL);

        jp1.add(theme);
        jp1.add(themesList);
        jp1.add(difficulte);
        jp1.add(difficultesList);
        this.add(jp1);

        for (Card card : cards) {
            card.addActionListener(new CardsListener());
            jp3.add(card);
        }

        this.add(jp3);
        jp2.add(text, BorderLayout.NORTH);
        jp2.add(bouton, BorderLayout.SOUTH);
        this.add(jp2);

        setPairCards();
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
    		String text = "Vous avez appuyé sur le bouton";
    		voix.playText(text);
    	}
    	// on redonne le focus au JFrame principal
    	// (après un clic, le focus est sur le bouton)
    	this.requestFocus();
    }

    public void setPairCards () {
        Stack<String> images = new Stack<>();
        images.add("../ressources/images/girafe.jpg");
        images.add("../ressources/images/girafe.jpg");
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
    	if (e.getKeyCode()==KeyEvent.VK_F5){
    	   	voix.playText("Vous venez d'appuyer sur EFFE 5");
    	}
    }
    
	/**
	 * Pour modifier les couleurs de fond et de premier plan de la fenêtre
	 * Cette fonction est appelée par la fonction "changeColor" de la classe "Preferences"
	 * à chaque fois que l'on presse F3 
	 * 
	 * on change la couleur du texte principal
	 **/
	public  void changeColor() {
    	// on récupère les couleurs de base dans la classe Preferences 
		Preferences pref = Preferences.getData();
		themesList.setBackground(pref.getCurrentBackgroundColor());
		themesList.setForeground(pref.getCurrentForegroundColor());
        difficultesList.setBackground(pref.getCurrentBackgroundColor());
        difficultesList.setForeground(pref.getCurrentForegroundColor());
        jp1.setBackground(pref.getCurrentBackgroundColor());
        jp1.setForeground(pref.getCurrentForegroundColor());
        jp2.setBackground(pref.getCurrentBackgroundColor());
        jp2.setForeground(pref.getCurrentForegroundColor());
        jp3.setBackground(pref.getCurrentBackgroundColor());
        jp3.setForeground(pref.getCurrentForegroundColor());
        theme.setForeground(pref.getCurrentForegroundColor());
        difficulte.setForeground(pref.getCurrentForegroundColor());
        bouton.setBackground(pref.getCurrentForegroundColor());
        bouton.setForeground(pref.getCurrentBackgroundColor());
        text.setForeground(pref.getCurrentForegroundColor());

        for (Card card : cards) {
            card.setBackground(pref.getCurrentBackgroundColor());
        }
    }
	
	
	/**
	 * Pour modifier la police des textes à chaque fois que l'on presse F4 
	 */
	public void changeSize(){
		Font f = Preferences.getData().getCurrentFont();
		themesList.setFont(f);
        difficultesList.setFont(f);
        theme.setFont(f);
        difficulte.setFont(f);
        text.setFont(f);
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
                    selectedCards = new ArrayList<Card>();
                    nbReturnedCards += 2;
                    if(nbReturnedCards == nbCards) {
                        bouton.addActionListener(new startGameListener());
                    }
                } else {
                    List<Card> disabledCards = new ArrayList<>();
                    for (Card c : cards) {
                        if (!selectedCards.contains(c)) {
                            disabledCards.add(c);
                            c.setEnabled(false);
                            c.setDisabledIcon(new ImageIcon(c.dosCarte));
                        }
                    }
                    int delay = 2000;
                    Timer timer = new Timer();
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

    private class startGameListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String difficulte = (String)difficultesList.getSelectedItem();
            int cardsForGame = 0;
            switch (difficulte) {
                case "Facile": cardsForGame = 12; break;
                case "Moyen": cardsForGame = 18; break;
                case "Difficile": cardsForGame = 24; break;
            }
            JeuSolo frameSoloGame = new JeuSolo("Partie Solo", cardsForGame);
            dispose();
        }
    }
}
