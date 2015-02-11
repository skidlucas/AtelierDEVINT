package jeu;

import devintAPI.*;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
	
/** Etend FenetreAbstraite pour avoir un Frame et réagir aux évênements claviers
 * Contient un exemple d'affichage d'image proportionnel à la taille de l'écran
 * 
 * @author helene
 *
 */

public class GestionImage extends FenetreAbstraite {

	// les éléments dont on change la taille avec F4
	JTextArea theoTexte;
	JLabel jl;
	
	// taille de la fenêtre
	int largeur,hauteur;
	
	public GestionImage(String title) {
		super(title);
	}

	// renvoie le fichier wave contenant le message d'accueil
	protected  String wavAccueil() {
		return "../ressources/sons/accueilImage.wav";
	}

	// renvoie le fichier wave contenant la règle du jeu
	protected  String wavRegleJeu() {
		return "../ressources/sons/accueilImage.wav";
	}

	// renvoie le fichier wave contenant la règle du jeu
	protected  String wavAide() {
		return "../ressources/sons/aide.wav";
	}

	// initialise le frame 
	protected void init() {
		// FlowLayout : les composants ont leur taille fixée par setPreferredSize
		// et sont ajoutés de gauche à droite, de haut en bas
		this.setLayout(new FlowLayout());
		
		// la largeur et la hauteur actuelle de la fenêtre
		// si vous fixez la taille des éléments graphiques 
		// faites le en utilisant des valeurs proportionnelles à la taille
		// de la fenêtre pour que différentes résolutions d'écran soient possibles
		largeur = getToolkit().getScreenSize().width;
		hauteur = getToolkit().getScreenSize().height;

		String  texte = "\nIci le layout est un \"FlowLayout\". Les composants sont ajoutés de gauche à droite et de haut en bas.";
		texte += "\nLa taille des composants est celle de \"setPreferredSize\" ou bien la taille optimale pour obtenir un frame le plus petit possible.";
		texte += "\n\nVoici les personnages du jeu Léa et Théo, 2007.";
		theoTexte = new JTextArea(texte);
		theoTexte.setLineWrap(true);
		theoTexte.setEditable(false);
//		add(theoTexte);

		// une image, voir http://java.sun.com/docs/books/tutorial/uiswing/components/icon.html
		ImageIcon icon = new ImageIcon("../ressources/images/theo.JPG");
		texte =  "Théo est dans un label a un fond bleu qui occupe la moitié de la largeur et le tiers de la hauteur.";
		// on met l'image dans un label
		jl = new JLabel(texte,icon,JLabel.CENTER);
		// fond bleu
		jl.setBackground(Color.BLUE);
		//composant opaque pour voir le fond bleu
		jl.setOpaque(true); 
		// (largeur de la fenetre)/4 et (hauteur fenetre)/2
		jl.setPreferredSize(new Dimension(largeur/3,hauteur/2));
		add(jl);

		// Léa
		icon = new ImageIcon("../ressources/images/lea.JPG");
		texte = "Léa";
		JLabel jl2 = new JLabel(texte,icon,JLabel.CENTER);
		// fond jaune
		jl2.setBackground(Color.YELLOW);
		//composant opaque pour voir le fond bleu
		jl2.setOpaque(true); 
		// de largeur/3 et hauteur
		jl2.setPreferredSize(new Dimension(largeur/8,hauteur));
		add(jl2);
	}

	@Override
	/** 
	 * pour cette fenêtre, changer la couleur n'a pas de sens, alors la méthode
	 * ne fait rien
	 */
	public void changeColor() {
		// TODO Auto-generated method stub
	}

	/**
	 * Pour modifier la police des textes à chaque fois que l'on presse F4 
	 */
	public void changeSize(){
		// on change aussi la taille de l'image
		int size = Preferences.getData().getCurrentSize();
        if (size==Preferences.LARGE_SIZE)
			jl.setIcon(new ImageIcon("../ressources/images/theoGrand.jpg"));
        else if (size==Preferences.MEDIUM_SIZE)
			jl.setIcon(new ImageIcon("../ressources/images/theo.jpg"));
        else
			jl.setIcon(new ImageIcon("../ressources/images/theoPetit.jpg"));
      	

	}
}


