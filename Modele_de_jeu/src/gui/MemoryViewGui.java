package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import t2s.SIVOXDevint;

import java.io.File;

import api.Card;
import api.MemoryController;
import api.MemoryView;
import api.Pair;
import api.Player;

/**
 * This class provides the default implementation for the view in MVC design.
 */
public class MemoryViewGui extends JFrame implements MemoryView, MouseListener,
		KeyListener, MouseMotionListener {

	private static final Dimension LABEL_DIMENSION = new Dimension(170, 170);

	private static final Color BACKGROUND = new Color(111, 111, 111);// new
	// Color(29,
	// 131,
	// 14);

	/** */
	private static final ImageIcon CARD_BACK = new ImageIcon(new String(
			"../ressources/res/doscarte.gif"));

	/** The UID. */
	private static final long serialVersionUID = -2321030124363089570L;

	/** The <code>MemoryController</code>. */
	private MemoryController listener;

	private Map<JLabel, Card> cardsPicture = new HashMap<JLabel, Card>();

	private List<JLabel> listLabel;

	private JLabel currentCardPicture;

	private JPanel panel;

	private JLabel firstLabel;

	private static int currentIndex = 0;

	private List<Pair> pairList;

	private int nbCardPerLine;

	private Border border = BorderFactory.createLineBorder(Color.BLACK, 20);

	private JFrame frame;

	private SIVOXDevint voix;

	/**
	 * Constructor.
	 * 
	 * @param difficulty
	 *            The difficulty of the current play.
	 * @param nbPairs
	 *            The number of pairs.
	 * @param themeDir
	 *            The current theme file.
	 */
	public MemoryViewGui(int nbPairs, File themeDir) {
		pairList = new ArrayList<Pair>();
		listLabel = new ArrayList<JLabel>();
		File[] listFiles = themeDir.listFiles();
		File[] resSound = (new File(themeDir + "/../../sons/"
				+ themeDir.getName())).listFiles();

		voix = new SIVOXDevint(2);

		for (int index = 0; index < nbPairs; index++) {
			pairList.add(new Pair(listFiles[index].getPath(), resSound[index]
					.getPath()));
		}
		nbCardPerLine = (nbPairs * 2 + 2) / 4;
		panel = new JPanel();
		panel.setBackground(BACKGROUND);
		panel.setLayout(new GridLayout(4, 4));

		List<Card> cards = createRandomizedCardsList(pairList);
		for (Card card : cards) {
			createCardPicture(card);
		}

		setContentPane(panel);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addKeyListener(this);
		currentCardPicture = listLabel.get(currentIndex);
		currentCardPicture.setBorder(BorderFactory.createLineBorder(
				Color.BLACK, 15));
		Cursor cursor = getCursor();
		System.out.println(cursor.getName());
		pack();
		setExtendedState(Frame.MAXIMIZED_BOTH);
	}

	private void setFocus(int index) {
		if (index < 0) {
			index = 0;
		}
		if (index >= listLabel.size()) {
			index = listLabel.size() - 1;
		}
		currentCardPicture.setBorder(BorderFactory.createLineBorder(BACKGROUND,
				0));
		currentCardPicture = listLabel.get(index);
		currentCardPicture.setBorder(BorderFactory.createLineBorder(
				Color.BLACK, 15));
	}

	private void createCardPicture(Card card) {
		JLabel cardLabel = new JLabel();
		cardLabel.addMouseListener(this);
		cardLabel.addMouseMotionListener(this);
		cardsPicture.put(cardLabel, card);
		listLabel.add(cardLabel);
		cardLabel.setSize(LABEL_DIMENSION);
		cardLabel.setPreferredSize(LABEL_DIMENSION);
		cardLabel.setMinimumSize(LABEL_DIMENSION);
		cardLabel.setIcon(CARD_BACK);

		JPanel temp = new JPanel();
		temp.setBackground(BACKGROUND);
		temp.add(cardLabel);
		temp.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel.add(temp);
	}

	/**
	 * Inherited method.
	 * 
	 * @see api.MemoryView#managePair(api.Card, boolean)
	 */
	public void managePair(Card secondCard, boolean valid) {
		JLabel tempCard = null;

		for (JLabel label : cardsPicture.keySet()) {
			if (cardsPicture.get(label) == secondCard) {
				label.setIcon(secondCard.getFaceCarte());
				if (!isTheEnd()) {
					zoom(label, secondCard.getSon());
				}
				tempCard = label;
			}
		}

		if (!valid) {
			tempCard.setIcon(CARD_BACK);
			firstLabel.setIcon(CARD_BACK);
			firstLabel.addMouseListener(this);
		} else {
			firstLabel.removeMouseListener(this);
			tempCard.removeMouseListener(this);
		}
	}

	public boolean isTheEnd() {
		for (JLabel label : cardsPicture.keySet()) {
			if (label.getIcon().equals(CARD_BACK)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Inherited method.
	 * 
	 * @see api.MemoryView#turnFirstCard(api.Card)
	 */
	public void turnFirstCard(Card card) {
		for (JLabel label : cardsPicture.keySet()) {
			if (cardsPicture.get(label) == card) {
				firstLabel = label;
				label.setIcon(card.getFaceCarte());
				zoom(label, card.getSon());
				firstLabel.removeMouseListener(this);
				break;
			}
		}
	}

	private void zoom(JLabel label, String string) {
		new ZoomFrame(label.getIcon(), string);
	}

	/**
	 * Inherited method.
	 * 
	 * @see api.MemoryView#updatePlayerScore(java.util.List)
	 */
	public void updatePlayerScore(List<Player> players) {
		String[] message = new String[players.size()];
		int i = 0;

		for (int j = 0; j < players.size(); j++) {
			message[j] = new String();
		}

		for (Player player : players) {
			if (player.getScore() < 0) {
				player.setScore(0);
			}
			message[i++] = "Le score du " + player.getName() + " est de : "
					+ player.getScore();
		}
		displayPlayerScore(message);
	}

	private void displayPlayerScore(String[] message) {
		String title = "Fin de la partie";
		Font f = new Font("Tahoma", 1, 56);

		frame = new JFrame();
		frame.setTitle(title);
		frame.setFont(f);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		Container contenu = frame.getContentPane();
		JPanel panel = new JPanel();
		JLabel label;
		for (int i = 0; i < message.length; i++) {
			label = new JLabel(message[i]);
			label.setFont(f);
			panel.add(label);
		}
		contenu.add(panel);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					frame.dispose();
				}
			}
		});
		// mettre ici le son des scores
		voix.playWav("../ressources/sons/fin_partie.wav");
		frame.setVisible(true);
	}

	/**
	 * Used to set the action listener.
	 * 
	 * @param listener
	 *            The listener to set.
	 */
	public void setListener(MemoryController listener) {
		this.listener = listener;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent evt) {
		if (listener == null) {
			System.err.println("err");
			return;
		}
		if (evt.getSource() instanceof JLabel) {
			currentCardPicture = (JLabel) evt.getSource();
			listener.selectCard(cardsPicture.get(currentCardPicture));
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			dispose();
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			Card temp = null;
			for (JLabel label : cardsPicture.keySet()) {
				if (label.equals(currentCardPicture)
						&& label.getIcon().equals(CARD_BACK)) {
					temp = cardsPicture.get(label);
					listener.selectCard(temp);
					break;
				}
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (++currentIndex >= listLabel.size()) {
				currentIndex = listLabel.size() - 1;
			}
			setFocus(currentIndex);
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (--currentIndex < 0) {
				currentIndex = 0;
			}
			setFocus(currentIndex);
		}

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			int newPosition = currentIndex - nbCardPerLine;
			if (newPosition >= 0) {
				currentIndex = newPosition;
				setFocus(currentIndex);
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			int newPosition = currentIndex + nbCardPerLine;
			if (newPosition < listLabel.size()) {
				currentIndex = newPosition;
				setFocus(currentIndex);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private List<Card> createRandomizedCardsList(List<Pair> pairs) {
		List<Card> cards = new ArrayList<Card>();
		List<Card> listeMelangee = new ArrayList<Card>();
		for (Pair pair : pairs) {
			cards.add(pair.getFirst());
			cards.add(pair.getSecond());
		}

		int indice;

		while (cards.size() != 0) {
			indice = (int) (Math.random() * cards.size());
			listeMelangee.add(cards.get(indice));
			cards.remove(indice);
		}
		return listeMelangee;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// NOTHING TO DO.
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		JLabel source = (JLabel) e.getSource();
		for (int i = 0; i < listLabel.size(); i++) {
			if (source.equals(listLabel.get(i))) {
				currentIndex = i;
			}
		}
		setFocus(currentIndex);
	}
}
