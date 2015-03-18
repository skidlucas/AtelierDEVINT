package jeu;

import gui.MemoryViewGui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import t2s.SIVOXDevint;

import api.MemoryManager;
import api.Player;

/**
 * This class displays an interface to select parameters for the game
 */
public class JeuInterfaceBis extends JFrame implements ActionListener,
        KeyListener {

    /** The UID. */
    private static final long serialVersionUID = -2147757216588818945L;

    /** */
    private static final int PICTURES_PER_THEME = 12;
    private JComboBox selectTheme;
    private JComboBox selectNbCartes;
    private JComboBox selectNbJoueurs;
    private JButton commencer;
    private static final String THEMES_PATH = "../ressources/res/";
    private Map<String, File> filesPerName = new HashMap<String, File>();

    /** The instance of <code>SIVOXDevint</code> used to manage vocal signs. */
    private SIVOXDevint voix;

    /**
     * Constructor.
     * 
     * @param title
     *            The title of the frame
     */
    public JeuInterfaceBis(String title) {
        super(title);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        init();
        retrieveThemes();
        createContenu();
        setVisible(true);
        requestFocus();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        voix = new SIVOXDevint();
    }

    private void retrieveThemes() {
        File resDirectory = new File(THEMES_PATH);
        if (!resDirectory.exists() || !resDirectory.isDirectory()) {

            dispose();
            System.err.println("Erreur lors du chargement des images");
            return;
        }

        for (File file : resDirectory.listFiles()) {
            if (isValid(file)) {
                filesPerName.put(file.getName(), file);
                selectTheme.addItem(file.getName());
            }
        }
    }

    private boolean isValid(File file) {
        if (!file.isDirectory() || file.listFiles().length < PICTURES_PER_THEME) {
            return false;
        }
        return true;
    }

    public void init() {
        Vector<NBCards> nbCartes = new Vector<NBCards>();
        Vector<PlayersEnum> nbJoueurs = new Vector<PlayersEnum>();

        nbCartes.add(NBCards.EASY);
        nbCartes.add(NBCards.MEDIUM);
        nbCartes.add(NBCards.DIFFICULT);
        nbJoueurs.add(PlayersEnum.JOUEUR1);
        nbJoueurs.add(PlayersEnum.JOUEUR2);
        nbJoueurs.add(PlayersEnum.JOUEUR3);
        nbJoueurs.add(PlayersEnum.JOUEUR4);

        selectTheme = new JComboBox();
        selectNbCartes = new JComboBox(nbCartes);
        selectNbJoueurs = new JComboBox(nbJoueurs);
    }

    public void createContenu() {
        JPanel temp;
        Container contenu = getContentPane();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel theme = new JLabel("Thème : ");
        JLabel nbCarte = new JLabel("Nombre de cartes : ");
        JLabel nbJoueur = new JLabel("Nombre de joueurs : ");

        Font f = new Font("Tahoma", 1, 56);

        theme.setFont(f);
        selectTheme.setFont(f);
        nbCarte.setFont(f);
        selectNbCartes.setFont(f);
        nbJoueur.setFont(f);
        selectNbJoueurs.setFont(f);

        temp = new JPanel();
        temp.add(theme);
        panel.add(temp);
        temp = new JPanel();
        temp.add(selectTheme);
        panel.add(temp);
        temp = new JPanel();
        temp.add(nbCarte);
        panel.add(temp);
        temp = new JPanel();
        temp.add(selectNbCartes);
        panel.add(temp);
        temp = new JPanel();
        temp.add(nbJoueur);
        panel.add(temp);
        temp = new JPanel();
        temp.add(selectNbJoueurs);
        panel.add(temp);

        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        mainPanel.add(panel);

        commencer = new JButton("Commencer");
        commencer.addActionListener(this);
        commencer.addKeyListener(this);
        commencer.setFont(f);
        temp = new JPanel();
        temp.add(commencer);
        temp.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        mainPanel.add(temp, BorderLayout.SOUTH);
        
        contenu.add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        
        JButton source = (JButton) ev.getSource();

        if (source.equals(commencer)) {
            voix.playShortText(commencer.getText());
            MemoryViewGui gui = new MemoryViewGui(
                    ((NBCards) (selectNbCartes.getSelectedItem())).getValue(),
                    filesPerName.get(selectTheme.getSelectedItem()));

            ArrayList<Player> playersList = new ArrayList<Player>();
            int nbMax = ((PlayersEnum) (selectNbJoueurs.getSelectedItem()))
                    .getValue();
            for (int i = 0; i < nbMax; i++) {
                String name = "Joueur " + (i + 1);
                playersList.add(new Player(name));
            }

            gui.setListener(new MemoryManager(playersList, gui));
            gui.setVisible(true);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            MemoryViewGui gui = new MemoryViewGui(
                    ((NBCards) (selectNbCartes.getSelectedItem())).getValue(),
                    filesPerName.get(selectTheme.getSelectedItem()));

            ArrayList<Player> playersList = new ArrayList<Player>();
            int nbMax = ((PlayersEnum) (selectNbJoueurs.getSelectedItem()))
                    .getValue();
            for (int i = 0; i < nbMax; i++) {
                String name = "Joueur " + (i + 1);
                playersList.add(new Player(name));
            }

            gui.setListener(new MemoryManager(playersList, gui));
            gui.setVisible(true);
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            dispose();
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
}
