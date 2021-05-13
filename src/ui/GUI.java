package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Rectangle;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;

import gamelogic.Game;

import java.awt.Cursor;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.common.*;
import java.awt.FlowLayout;
import java.awt.ComponentOrientation;
import javax.swing.BoxLayout;
import java.awt.Point;
import java.awt.BorderLayout;

public class GUI {

	JFrame gameFrame;
	Container con;
	JPanel titleNamePanel, mainTextPanel, choiceButtonPanel;
	 ResizeablePanel backgroundPanel;
	//JPanel backgroundPanel;
	JMenuItem player1, player2, player3, player4;
	JPanel playerChoice, outputPanel, inputPanel1;
	JMenu playerPopup;
	JLabel titleNameLabel;
	Game game;

	PlayerChoiceHandler playerChoiceHandler = new PlayerChoiceHandler();
	InputHandler inputHandler = new InputHandler();
	// MouseListener popupListener = new PopupListener();
	private JTextArea output;
	private JComboBox<?> playerList;
	private ImageIcon isaacIcon, derkhanIcon, lemuelIcon, yagharekIcon;
	private HashMap<String, ImageIcon> playerIcons, interactiveObjectIcons;
	private JLabel characterPicLabel;
	private InventoryPanel inventory;
	private ItemFoundPanel itemFoundPanel;
	static final ClassLoader loader = Game.class.getClassLoader();

	private String npcIconsPath = "/ui/assets/objectIcons/";
	@SuppressWarnings("unused")
	private String playerIconsPath = "/ui/assets/mainIcons/";
	private JPanel contentPanel;

	static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

	public GUI(Game game) throws IOException {
		this.game = game;
		
		
		
		initializeComponents();
		inventory.setInactive();
	}

	public void initializeComponents() throws IOException {

		Dimension dim1920x1080 = new Dimension(1920, 1080);
		Dimension dimPortPane = new Dimension(326, 421);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenSize = new Dimension(1600, 1050);

		gameFrame = new JFrame(); // make the frame resizeable??
		
		//JOptionPane.showMessageDialog( gameFrame, "This version only supports 1920x1080 resolution. Please maximize the window for it to be displayed correctly.");
		
		gameFrame.setSize(screenSize);
		gameFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
		gameFrame.setMaximumSize(screenSize);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setPreferredSize(screenSize);
		gameFrame.getContentPane().setBackground(Color.ORANGE);
		gameFrame.getContentPane().setLayout(null);
		gameFrame.setVisible(true);
		con = gameFrame.getContentPane();
		con.setLayout(null);
		con.setBackground(Color.black);
		con.setPreferredSize(screenSize);

		backgroundPanel = new ResizeablePanel();
		backgroundPanel.setForeground(Color.WHITE);
		//backgroundPanel.setBounds(0, 0, gameFrame.getWidth(), gameFrame.getHeight());
		backgroundPanel.setBounds(0, 0, screenSize.width, screenSize.height);
		backgroundPanel.setPreferredSize(screenSize);
		backgroundPanel.setBackground(Color.ORANGE);
		backgroundPanel.setVisible(true);
		backgroundPanel.setOpaque(true);
		backgroundPanel.setLayout(null);

		String[] playerNames = { "Isaac", "Lemuel", "Derkhan", "Yagharek" }; // get names from game

		// adding main players' icons
		isaacIcon = new ImageIcon(GUI.class.getResource("/ui/assets/mainIcons/Isaac.png"));
		derkhanIcon = new ImageIcon(GUI.class.getResource("/ui/assets/mainIcons/Derkhan.png"));
		lemuelIcon = new ImageIcon(GUI.class.getResource("/ui/assets/mainIcons/Lemuel.png"));
		yagharekIcon = new ImageIcon(GUI.class.getResource("/ui/assets/mainIcons/Yagharek.png"));
		playerIcons = new HashMap<>();
		playerIcons.put("Isaac", isaacIcon);
		playerIcons.put("Derkhan", derkhanIcon);
		playerIcons.put("Lemuel", lemuelIcon);
		playerIcons.put("Yagharek", yagharekIcon);

		// adding npcs' icons
		// ImageIcon npcIcon = new
		// ImageIcon(GUI.class.getResource("/ui/assets/npc.png"));
		interactiveObjectIcons = new HashMap<>();
		addIcons();

		itemFoundPanel = new ItemFoundPanel(gameFrame.getPreferredSize());
		backgroundPanel.add(itemFoundPanel);

		// backgroundPanel.add(portraitPane);

		con.add(backgroundPanel);

		JLabel portraitLabel = new JLabel();
		portraitLabel.setHorizontalAlignment(SwingConstants.CENTER);
		portraitLabel.setBackground(Color.GRAY);
		portraitLabel.setOpaque(true);
		portraitLabel.setBounds(6, 0, 315, 427);

		JPanel portraitPanel = new JPanel();
		portraitPanel.setBounds(0, 0, 326, 427);
		portraitPanel.setVisible(true);
		portraitPanel.setOpaque(false);
		portraitPanel.setLayout(null);
		characterPicLabel = new JLabel("");
		characterPicLabel.setHorizontalAlignment(SwingConstants.CENTER);
		characterPicLabel.setPreferredSize(new Dimension(288, 362));
		characterPicLabel.setBounds(19, 13, 288, 362);
		portraitPanel.add(characterPicLabel);
		portraitPanel.add(portraitLabel);

		JPanel portraitPane = new JPanel();
		portraitPane.setLayout(null);
		// portraitPane.setBounds(135, 100, 326, 421);
		portraitPane.setBounds(0, 0, 326, 421);
		portraitPane.setPreferredSize(dimPortPane);
		portraitPane.setVisible(true);
		portraitPane.setOpaque(false);

		playerChoice = new JPanel();
		playerChoice.setBorder(new EmptyBorder(0, 0, 0, 0));
		playerChoice.setOpaque(false);
		playerChoice.setBounds(20, 386, 110, 25);
		playerChoice.setForeground(Color.WHITE);
		playerChoice.setFont(new Font("Courier New", Font.PLAIN, 24));

		playerList = new JComboBox<Object>(playerNames); // make dynamic later
		playerList.setBounds(0, 0, 174, 25);

		playerList.setIgnoreRepaint(true);
		playerList.setFocusable(false);
		playerList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		playerList.setBackground(Color.GRAY);
		playerList.setOpaque(false);
		playerList.setBorder(null);
		playerList.setForeground(Color.WHITE);
		playerList.setFont(new Font("Courier New", Font.PLAIN, 20));
		playerList.setSelectedIndex(0);
		playerList.addActionListener(playerChoiceHandler);

		makeTransparent(playerList.getComponents());
		playerList.setUI(new BasicComboBoxUI() {
			public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
			}
		});
		((JLabel) playerList.getRenderer()).setOpaque(false);

		playerChoice.setLayout(null);
		playerChoice.add(playerList);
		portraitPane.add(playerChoice);

		portraitPane.add(portraitPanel);
		portraitLabel.setVisible(true);

		inventory = new InventoryPanel(game.getCurrentPlayer(),screenSize);

		backgroundPanel.add(inventory);
		gameFrame.addMouseListener(new MouseClickedOutside(inventory));

		contentPanel = new JPanel();
		contentPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		contentPanel.setOpaque(false);
		//contentPanel.setBounds(0, 0, gameFrame.getWidth(), gameFrame.getHeight());
		contentPanel.setBounds(0, 0, backgroundPanel.getWidth(), backgroundPanel.getHeight());

		backgroundPanel.add(contentPanel);
		contentPanel.setLayout(null);

		JPanel controlPanelWrapper = new JPanel();
		//1333,18 for 1080p (int) 0.69*1920, (int)0.016*1080
		controlPanelWrapper.setBounds((screenSize.width - 380), 18, 350, 90);
		controlPanelWrapper.setOpaque(false);
		FlowLayout flowLayout = (FlowLayout) controlPanelWrapper.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPanel.add(controlPanelWrapper);

		JPanel controlPanel = new JPanel();
		controlPanel.setOpaque(false);
		controlPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		controlPanelWrapper.add(controlPanel);
		controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel inventoryButtonLabel = new JLabel();
		inventoryButtonLabel.setPreferredSize(new Dimension(70, 70));
		inventoryButtonLabel.setIcon(new ImageIcon(GUI.class.getResource("/ui/assets/mainIcons/inventoryButtonIcon.png")));
		inventoryButtonLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		inventoryButtonLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		inventoryButtonLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		inventoryButtonLabel.setFont(new Font("Tahoma", Font.PLAIN, 36));
		inventoryButtonLabel.setBackground(Color.WHITE);
		inventoryButtonLabel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				inventoryButtonLabelMouseClicked(evt);
			}
		});
		controlPanel.add(inventoryButtonLabel);

		playerPanelWrapper = new JPanel();
		//location 114,114 for 1080p
		playerPanelWrapper.setLocation((int)(0.06*screenSize.width), 114);
		playerPanelWrapper.setSize(326, 421);
		//playerPanelWrapper.setBounds(114, 114, 326, 421);
		playerPanelWrapper.setOpaque(false);
		FlowLayout fl_playerPanelWrapper = (FlowLayout) playerPanelWrapper.getLayout();
		playerPanelWrapper.add(portraitPane);
		playerPanelWrapper.setPreferredSize(dimPortPane);
		contentPanel.add(playerPanelWrapper);

		infoPanelWrapper = new JPanel();
		//location 0,860 for 1080p
		//size 420x218 for 1080p
		//infoPanelWrapper.setSize( ((int) 0.21* screenSize.width), ((int)0.20*screenSize.height));
		//infoPanelWrapper.setPreferredSize(new Dimension(((int) 0.21* screenSize.width), ((int)0.20*screenSize.height)));
		//infoPanelWrapper.setPreferredSize(new Dimension(420,218));
		infoPanelWrapper.setSize( ((int) (0.21* screenSize.width)), ((int)(0.20*screenSize.height)));
		infoPanelWrapper.setPreferredSize(new Dimension( ((int) (0.21* screenSize.width)), ((int)(0.20*screenSize.height))));
		infoPanelWrapper.setLocation(0, screenSize.height - infoPanelWrapper.getHeight());
		//infoPanelWrapper.setBounds(0, 860, ((int) 0.21* screenSize.width), ((int)0.20*screenSize.height));
		
		infoPanelWrapper.setOpaque(false);
		contentPanel.add(infoPanelWrapper);
		infoPanelWrapper.setLayout(null);
		

		infoPanel = new ResizeablePanel();
		infoPanel.setBounds(0, 5, 0, 0);
		//infoPanel = new JPanel();
		infoPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		infoPanel.setOpaque(false);
		infoPanel.setBounds(0, 0, infoPanelWrapper.getWidth(),infoPanelWrapper.getHeight());
		infoPanel.setPreferredSize(new Dimension(infoPanelWrapper.getWidth(),infoPanelWrapper.getHeight()));
		infoPanelWrapper.add(infoPanel);
		infoPanel.setLayout(null);

		statusNameLabel = new JLabel((String) null);
		statusNameLabel.setForeground(Color.GRAY);
		statusNameLabel.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		statusNameLabel.setBounds(45, 166, 149, 29);
		infoPanel.add(statusNameLabel);

		statusDescriptionLabel = new JLabel((String) null);
		statusDescriptionLabel.setForeground(Color.BLACK);
		statusDescriptionLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		statusDescriptionLabel.setBounds(246, 166, 160, 29);
		infoPanel.add(statusDescriptionLabel);

		healthPersentageLabel = new JLabel("0%");
		healthPersentageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		healthPersentageLabel.setFont(new Font("Times New Roman", Font.PLAIN, 32));
		healthPersentageLabel.setBounds(183, 62, 78, 77);
		infoPanel.add(healthPersentageLabel);

		healthInfoLabel = new JLabel("0/0");
		healthInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		healthInfoLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		healthInfoLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		healthInfoLabel.setBounds(35, 98, 135, 57);
		infoPanel.add(healthInfoLabel);
		
		JLabel infoPanelLabel = new JLabel("New label");
		infoPanelLabel.setIcon(new ImageIcon(GUI.class.getResource("/ui/assets/mainIcons/infoPanel.png")));
		infoPanelLabel.setBounds(0, 0, 418,227);
		infoPanelLabel.setPreferredSize(new Dimension(418, 213));
		infoPanel.addBackground(infoPanelLabel);
		//infoPanel.add(infoPanelLabel);
		
		
		
		
		JPanel textPanelWrapper = new JPanel();
		textPanelWrapper.setOpaque(false);
		//418, 1080
		textPanelWrapper.setSize(( (int)(screenSize.getWidth()*0.24)), (int) screenSize.getHeight());
		textPanelWrapper.setLocation((playerPanelWrapper.getX()+playerPanelWrapper.getWidth() + 15), 0);
		//textPanelWrapper.setBounds((playerPanelWrapper.getX()+playerPanelWrapper.getWidth() + 10), 0, ( (int)(screenSize.getWidth()*0.21)), (int) screenSize.getHeight());
		contentPanel.add(textPanelWrapper);
		textPanelWrapper.setLayout(new BorderLayout(0, 0));
		
		textHeaderPanel = new JPanel();
		textHeaderPanel.setOpaque(false);
		textHeaderPanel.setSize(textPanelWrapper.getWidth(),playerPanelWrapper.getY());
		textHeaderPanel.setPreferredSize(new Dimension(textPanelWrapper.getWidth(),playerPanelWrapper.getY()));
		textPanelWrapper.add(textHeaderPanel, BorderLayout.NORTH);
		textHeaderPanel.setLayout(null);

		outputPanelWrapper = new JPanel();
		outputPanelWrapper.setLocation(0, playerPanelWrapper.getY());
		//outputPanelWrapper.setBounds(401, 114, 428, 582);
		outputPanelWrapper.setOpaque(false);
		textPanelWrapper.add(outputPanelWrapper, BorderLayout.CENTER);

		output = new JTextArea();
		output.setFocusable(false);
		output.setSelectionColor(Color.LIGHT_GRAY);
		output.setOpaque(false);
		output.setBorder(null);
		output.setBounds(0, 0, 418, 763);
		output.setWrapStyleWord(true);
		output.setLineWrap(true);
		output.setForeground(Color.DARK_GRAY);
		output.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		output.setEditable(false);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.setAutoscrolls(true);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBorder(null);
		scrollPane.setBounds(0, 0, 418, 763);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setViewportView(output);

		outputPanel = new JPanel();
		outputPanel.setPreferredSize(new Dimension(418, 760));
		outputPanel.setOpaque(false);
		outputPanel.setBorder(null);
		outputPanel.setBounds(new Rectangle(0, 0, 418, 763));
		outputPanel.setLayout(null);
		outputPanel.add(scrollPane);

		output.addMouseListener(new MouseClickedOutside(inventory));
		outputPanelWrapper.add(outputPanel);
		
		
		JPanel inputPanelWrapper = new JPanel();
		//inputPanelWrapper.setBounds(401, 970, 420, 110);
		inputPanelWrapper.setSize(textPanelWrapper.getWidth(), (int)(infoPanel.getHeight()/2));
		inputPanelWrapper.setPreferredSize(new Dimension(textPanelWrapper.getWidth(), infoPanel.getHeight()));
		inputPanelWrapper.setOpaque(false);
		textPanelWrapper.add(inputPanelWrapper, BorderLayout.SOUTH);

		//inputPanel = new JPanel();
		//inputPanel.setPreferredSize(new Dimension(410, 100));
		//inputPanel.setOpaque(false);
		//inputPanelWrapper.add(inputPanel);
		//inputPanel.setLayout(null);

		input = new JTextField();
		input.setBackground(Color.LIGHT_GRAY);
		input.setBounds(0, 0, textPanelWrapper.getWidth(), 26);
		input.setText("");
		input.setSelectionColor(Color.LIGHT_GRAY);
		input.setHorizontalAlignment(SwingConstants.LEFT);
		input.setForeground(Color.GRAY);
		input.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		input.setColumns(10);
		input.setCaretColor(Color.GRAY);
		input.setBorder(null);
		input.setAutoscrolls(false);
		input.setAlignmentX(0.0f);
		input.addKeyListener(inputHandler);
		inputPanelWrapper.add(input);
		


		

		JLabel backgroundLabel = new JLabel("New label");
		backgroundLabel.setIcon(new ImageIcon(GUI.class.getResource("/ui/assets/mainIcons/background.png")));
		backgroundLabel.setBounds(0, 0, 1920, 1080);
		backgroundLabel.setPreferredSize(screenSize);
		backgroundPanel.addBackground(backgroundLabel);
		//backgroundPanel.add(backgroundLabel);
		
		gameFrame.addWindowStateListener(windowStateListener);
		updatePlayerScreen();
		gameFrame.pack();
	}

	public void updatePlayerScreen() { // implement observer pattern??
		if (inventory.isEnabled()) {
			inventory.close();
		}
		game.getCurrentPlayer().addObserver(itemFoundPanel); // if already exists not added

		inventory.updatePlayer(game.getCurrentPlayer());
		inventory.update(game.getCurrentPlayer().getName(), game.getCurrentPlayer().getCurrentWeight(),
				game.getCurrentPlayer().getMaxWeight(), game.getCurrentPlayer().getItems(),
				game.getCurrentPlayer().getInventoryCounts());
		int currentHP = game.getCurrentPlayer().getHpCurrent();
		int maxHP = game.getCurrentPlayer().getHpMax();
		float healthPersentage = Math.round(((float) currentHP / maxHP) * 100);
		healthPersentageLabel.setText((int) healthPersentage + "%");
		healthInfoLabel.setText(currentHP + "/" + maxHP);

		suspendPlayerSelection(game.getCurrentPlayer().isInteracting()); // suspend player selection if current player
																			// is interacting

		try // try to set an icon of the object player is interacting with
		{
			characterPicLabel
					.setIcon(interactiveObjectIcons.get(game.getCurrentPlayer().getInteractiveObject().getName()));
		} catch (Exception e) // if not keep/set the current player's
		{
			characterPicLabel.setIcon(playerIcons.get(game.getCurrentPlayer().getName()));
		}
		// updating game status
		statusNameLabel.setText(game.getCurrentPlayer().getStatusName());
		statusDescriptionLabel.setText(game.getCurrentPlayer().getStatusDescription());
	}

	private void suspendPlayerSelection(boolean suspend) {
		if (suspend) {
			playerList.setEnabled(false);
			playerList.setVisible(false);
		} else {
			playerList.setEnabled(true);
			playerList.setVisible(true);

		}
	}

	private void makeTransparent(Component[] comp) {
		for (int x = 0; x < comp.length; x++) {
			if (comp[x] instanceof javax.swing.plaf.metal.MetalComboBoxButton) {
				((javax.swing.plaf.metal.MetalComboBoxButton) comp[x]).setOpaque(false);
				((javax.swing.plaf.metal.MetalComboBoxButton) comp[x]).setBorder(null);
			} else if (comp[x] instanceof JTextField) {
				((JTextField) comp[x]).setOpaque(false);
				((JTextField) comp[x]).setBorder(null);
			}
		}
	}

	@SuppressWarnings("unused")
	private void addIcons() {
		// String path =
		// GUI.class.getProtectionDomain().getCodeSource().getLocation().getPath() +
		// npcIconsPath;
		// String path =
		// File[] contents = new File(path).listFiles();
		// for (File imageFile : contents) {
		ImageIcon icon = new ImageIcon(GUI.class.getResource(npcIconsPath + "npc.png")); // casting???
		interactiveObjectIcons.put("npc", icon); // adding without extension
		// }
	}

	WindowStateListener windowStateListener = new WindowAdapter() {
		public void windowStateChanged(WindowEvent evt) {
			int oldState = evt.getOldState();
			int newState = evt.getNewState();

			if ((oldState & Frame.ICONIFIED) == 0 && (newState & Frame.ICONIFIED) != 0) {
				// Frame was iconized
			} else if ((oldState & Frame.ICONIFIED) != 0 && (newState & Frame.ICONIFIED) == 0) {
				// Frame was deiconized
			}

			if ((oldState & Frame.MAXIMIZED_BOTH) == 0 && (newState & Frame.MAXIMIZED_BOTH) != 0) {
				// Frame was maximized
				device.setFullScreenWindow(gameFrame);
				updateScale();

			} else if ((oldState & Frame.MAXIMIZED_BOTH) != 0 && (newState & Frame.MAXIMIZED_BOTH) == 0) {
				// Frame was minimized
			}
		}
	};
	private JPanel playerPanelWrapper;
	private JPanel infoPanelWrapper;
	private ResizeablePanel infoPanel;
	//private JPanel infoPanel;
	private JLabel healthPersentageLabel;
	private JLabel healthInfoLabel;
	private JLabel statusNameLabel;
	private JLabel statusDescriptionLabel;
	private JPanel inputPanel;
	private JTextField input;
	private JPanel outputPanelWrapper;
	private JPanel textHeaderPanel;

	private void inventoryButtonLabelMouseClicked(MouseEvent evt) {// GEN-FIRST:event_inventoryButtonLabelMouseClicked
		if (!inventory.isEnabled()) {
			inventory.open();
		} else {
			inventory.close();
		}
	}
	public void updateScale()
	{
		//gameFrame.pack();
		backgroundPanel.setSize(gameFrame.getSize());
		//contentPanel.setBounds(0, 0, gameFrame.getWidth(), gameFrame.getHeight());
		contentPanel.setSize(gameFrame.getSize());
		infoPanel.setSize(infoPanelWrapper.getWidth(),infoPanelWrapper.getHeight());
		infoPanel.scaleBackground();
		backgroundPanel.scaleBackground();
		
	}

	class MouseClickedOutside extends MouseAdapter implements MouseListener {
		private Closable target;

		MouseClickedOutside(Closable target) {
			this.target = target;
		}

		@Override
		public void mouseReleased(MouseEvent e) {

			Component comp = e.getComponent();

			if (comp != null) {
				String compName = recursivelyCheckForTarget(comp);
				if (compName != null) {
					// Target pressed
					return;
				} else {
					// Target not pressed
					inventory.close();
				}
			}
		}

		private String recursivelyCheckForTarget(Component comp) {
			if (comp == target) {
				return comp.toString();

			} else {
				comp = comp.getParent();
				if (comp != null) {
					return recursivelyCheckForTarget(comp);
				}
			}
			return null;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}
	}

	class PlayerChoiceHandler implements ActionListener {

		public void actionPerformed(ActionEvent event) {

			JComboBox<?> cb = (JComboBox<?>) event.getSource(); // dirty cast?
			String playerName = (String) cb.getSelectedItem();
			if (!game.getCurrentPlayer().isInteracting()) { // imageIcon???

				game.updateCurrentPlayer(playerName);
				updatePlayerScreen();
			}
		}
	}

	class InputHandler implements KeyListener {

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {

				game.play(input.getText());
				updatePlayerScreen();
				input.setText("");
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
	}

	public void updateOutput(String string) {

		output.append(string);

	}
}