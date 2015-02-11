/**
 * @author LOGRE Ivan, MULLER Stephane, GUYADER Erwan
 * Elèves SI3 en 2010-2011
 **/

package devintAPI;

import java.awt.Color;
import java.awt.Font;
//import java.io.File;
import java.util.ArrayList;
import java.util.List;



import t2s.SIVOXDevint;
import devintAPI.FenetreAbstraite;

/**
 * classe pour stocker les préférences de couleurs et de voix SIVOX
 * 
 * On change de jeu de couleurs en tapant F3.
 * On choisi la voix en tapant 
 *
 */
public class Preferences {
	
	// la taille des polices
	public static int SMALL_SIZE=30;
	public static int MEDIUM_SIZE= 70;
	public static int LARGE_SIZE=100;

	/** The instance of the singleton. */
	private static Preferences instance;

	/** liste des classes (i.e. fenêtres) qui vont utiliser ces préférences
	 *  ces classes héritent de MenuAbstrait ou DevintFrame
	 *  */
	private List<DevintFrameListener> openedWinndows;
	
	/** les valeurs choisies */
	// la voix
	private int currentVoice;
	private SIVOXDevint voix;
	
	// le jeu de couleurs
	private int currentSetOfColor;
	private Color foregroundColor;
	private Color backgroundColor;
	
	// la taille actuelle de la font (ou des images)
	private int currentSize;
	
	/** pour créer l'instance de Data */
	public static Preferences getData() {
		if (instance == null) {
			instance = new Preferences();
		}
		return instance;
	}

	/**
	 * Instantiates a new data.
	 */
	private Preferences() {
		// les fenêtres qui écoutent
		openedWinndows = new ArrayList<DevintFrameListener>();
		// paramètres pour la couleur
		foregroundColor = new Color(155,215,202);
		backgroundColor = Color.BLACK;
		currentSetOfColor = 1;
		// paramètres pour la voix
		currentVoice = 2;
		voix = new SIVOXDevint(currentVoice);
		// police de caractère
		currentSize=MEDIUM_SIZE;
	}

	/**
	 * pour changer la couleur
	 * on passe d'un jeu prédéfini à un autre
	 */
	
	public void changeColor() {
		if (currentSetOfColor == 0) {
			// jeu de couleurs pastel/noir
			this.foregroundColor = new Color(221,138,68); //pastel
			this.backgroundColor = Color.BLACK; // noir
			currentSetOfColor = 1;
		} else if (currentSetOfColor == 1) {
			// jeu de couleurs noir/pastel
			this.foregroundColor = Color.BLACK; //noir
			this.backgroundColor = new Color(221,138,68); //pastel
			currentSetOfColor = 2;
		} else if (currentSetOfColor == 2) {
			// jeu de couleur de base
			this.foregroundColor = Color.BLACK; //noir
			this.backgroundColor = new Color(155,215,202);//
			currentSetOfColor = 0;
		}
		// on met à jour toutes les menus avec ce nouveau jeu de couleurs
		for (DevintFrameListener fenetre : openedWinndows) {
			fenetre.changeColor();
		}

	}
	
	/**
	 * pour changer la police
	 */
	public void changeSize() {
		if (currentSize==SMALL_SIZE)
			currentSize=MEDIUM_SIZE;
		else 
			if (currentSize==MEDIUM_SIZE ) {
				currentSize=LARGE_SIZE;
			}
			else 
				currentSize=SMALL_SIZE;
		// on met à jour toutes les fenêtres avec cette police
		for (DevintFrameListener fenetre : openedWinndows) {
			fenetre.changeSize();
		}
	}

	// pour changer la voix en passant à la suivante
	public void changeVoice() {
		if(currentVoice == 7)
			currentVoice = 0;
		else
			currentVoice++;
		this.voix.setVoix(currentVoice);
	}

	// la police
	public Font getCurrentFont() {
		return new Font("Arial",Font.BOLD,currentSize);
	}
	
	// la taille police
	public int getCurrentSize() {
		return currentSize;
	}
	
	// la couleur de devant
	public Color getCurrentForegroundColor() {
		return this.foregroundColor;
	}

	// la couleur de fond
	public Color getCurrentBackgroundColor() {
		return this.backgroundColor;
	}
	
	// pour ajouter une fenêtre à la liste des fenêtres dont on peut fixer les préférences
	public void addDevintFrame(DevintFrameListener frame) {
		if (!openedWinndows.contains(frame))
			this.openedWinndows.add(frame);
	}

	// la voix
	public SIVOXDevint getVoice() {
		return voix;
	}

}
