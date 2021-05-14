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
import java.awt.FlowLayout;
import java.awt.ComponentOrientation;
import java.awt.BorderLayout;

public class GUI {

	JFrame gameFrame;
	Container con;
	JPanel titleNamePanel, mainTextPanel, choiceButtonPanel;
	// ResizeablePanel backgroundPanel;
	ScaleablePanel backgroundPanel;
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
	private ScaleablePanel textStripWrapper;
	private int verticalScrollBarMaximumValue;
	private JPanel inputPanel;
	private JScrollPane scrollPane;
	private JPanel controlPanelWrapper;

	//private int leftPole, rightPole;
	static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

	public GUI(Game game) throws IOException {
		this.game = game;

		initializeComponents();
		inventory.setInactive();
	}

	public void initializeComponents() throws IOException {

		new Dimension(1920, 1080);
		Dimension dimPortPane = new Dimension(326, 421);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	     //screenSize = new Dimension(1600, 1050);
		
		int leftPole = (int) (0.39 * screenSize.height);
		int rightPole = (int) (screenSize.width - (0.88 * screenSize.height));

		gameFrame = new JFrame(); // make the frame resizeable??
		//gameFrame.setResizable(false);

		// JOptionPane.showMessageDialog( gameFrame, "This version only supports
		// 1920x1080 resolution. Please maximize the window for it to be displayed
		// correctly.");

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

		backgroundPanel = new ScaleablePanel(screenSize,leftPole,rightPole);
		backgroundPanel.setForeground(Color.WHITE);
		

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

		inventory = new InventoryPanel(game.getCurrentPlayer(), screenSize);

		backgroundPanel.add(inventory);
		gameFrame.addMouseListener(new MouseClickedOutside(inventory));

	

		// 416x1080 for 1080p related to height
		leftPanel = new JPanel();
		leftPanel.setOpaque(false);
		leftPanel.setBounds(backgroundPanel.getLeftBounds());
		leftPanel.setLayout(null);
		backgroundPanel.add(leftPanel);

		// 960x1080 for 1080p - related to height
		rightPanel = new JPanel();
		rightPanel.setForeground(Color.ORANGE);
		rightPanel.setOpaque(false);
		rightPanel.setBounds(backgroundPanel.getRightBounds());
		rightPanel.setLayout(null);
		backgroundPanel.add(rightPanel);
		

		centerPanel = new JPanel();
		centerPanel.setOpaque(false);
		centerPanel.setBounds(backgroundPanel.getCenterBounds());
		centerPanel.setLayout(null);
		backgroundPanel.add(centerPanel);

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
		playerChoice.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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

		controlPanelWrapper = new JPanel();
		//controlPanelWrapper.setLocation((int) (0.69*screenSize.width), (int)(0.016*screenSize.height));
		// 1333,18 for 1080p (int) 0.69*1920, (int)0.016*1080
		controlPanelWrapper.setSize(200, 200);
		controlPanelWrapper.setLocation((int) (screenSize.width - controlPanelWrapper.getWidth())- 20, (int)(0.016*screenSize.height));
		
		controlPanelWrapper.setOpaque(false);
		backgroundPanel.add(controlPanelWrapper);
		controlPanelWrapper.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		JPanel controlPanel = new JPanel();
		controlPanel.setOpaque(false);
		controlPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		controlPanelWrapper.add(controlPanel);
		controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel inventoryButtonLabel = new JLabel();
		inventoryButtonLabel.setPreferredSize(new Dimension(70, 70));
		inventoryButtonLabel
				.setIcon(new ImageIcon(GUI.class.getResource("/ui/assets/mainIcons/inventoryButtonIcon.png")));
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
		// location 114,114 for 1080p
		playerPanelWrapper.setSize(326, 421);
		playerPanelWrapper.setPreferredSize(playerPanelWrapper.getSize());
		playerPanelWrapper.setLocation((int) backgroundPanel.getLeftPole() - playerPanelWrapper.getWidth() + 24 , 114);
		// playerPanelWrapper.setBounds(114, 114, 326, 421);
		playerPanelWrapper.setOpaque(false);
		playerPanelWrapper.setLayout(new BorderLayout(0, 0));
		playerPanelWrapper.add(portraitPane);
		playerPanelWrapper.setPreferredSize(dimPortPane);
		backgroundPanel.add(playerPanelWrapper);

		infoPanelWrapper = new JPanel();
		// location 0,860 for 1080p
		infoPanelWrapper.setSize(backgroundPanel.getLeftPole(),218);
		infoPanelWrapper.setPreferredSize(infoPanelWrapper.getSize());
		infoPanelWrapper.setLocation(1, screenSize.height - infoPanelWrapper.getHeight());
		infoPanelWrapper.setOpaque(false);
		
		backgroundPanel.add(infoPanelWrapper);
		infoPanelWrapper.setLayout(new BorderLayout(0, 0));

		infoPanel = new JPanel();
		//infoPanel.setBounds(0, 5, 0, 0);
		// infoPanel = new JPanel();
		//infoPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		//infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		infoPanel.setOpaque(false);
		infoPanel.setSize(infoPanelWrapper.getWidth(), infoPanelWrapper.getHeight());
		infoPanel.setPreferredSize(infoPanel.getSize());
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
		infoPanelLabel.setBounds(0, 0, 420, 227);
		infoPanelLabel.setPreferredSize(new Dimension(418, 213));
		// infoPanel.addBackground(infoPanelLabel);
		infoPanel.add(infoPanelLabel);

		textStripWrapper = new ScaleablePanel(new Dimension(rightPole - leftPole,screenSize.height),68,rightPole - leftPole - 68);
		textStripWrapper.setOpaque(false);
		// 418, 1080
		textStripWrapper.setSize(backgroundPanel.getRightPole() - backgroundPanel.getLeftPole(),screenSize.height);
		textStripWrapper.setLocation(backgroundPanel.getLeftPole(), 0);
		// textPanelWrapper.setBounds((playerPanelWrapper.getX()+playerPanelWrapper.getWidth()
		// + 10), 0, ( (int)(screenSize.getWidth()*0.21)), (int)
		// screenSize.getHeight());
		backgroundPanel.add(textStripWrapper);
		textStripWrapper.setLayout(null);


		inputPanel = new JPanel();
		inputPanel.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		inputPanel.setBackground(Color.LIGHT_GRAY);
		//inputPanel.setBounds(401, 970, 420, 110);
		inputPanel.setSize(textStripWrapper.getCenterWidth(), 30);
		inputPanel.setPreferredSize(inputPanel.getSize());
		inputPanel.setLocation(textStripWrapper.leftPole, screenSize.height - (int) (infoPanel.getHeight()/2));
		inputPanel.setOpaque(false);
		textStripWrapper.add(inputPanel);

		// inputPanel = new JPanel();
		// inputPanel.setPreferredSize(new Dimension(410, 100));
		// inputPanel.setOpaque(false);
		// inputPanelWrapper.add(inputPanel);
		// inputPanel.setLayout(null);

		input = new JTextField();
		input.setBackground(Color.LIGHT_GRAY);
		input.setBounds(0, 0, inputPanel.getWidth(), 26);
		input.setText("");
		input.setSelectionColor(Color.GRAY);
		input.setHorizontalAlignment(SwingConstants.LEFT);
		input.setForeground(Color.DARK_GRAY);
		input.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		input.setColumns(10);
		input.setCaretColor(Color.DARK_GRAY);
		input.setBorder(null);
		input.setAutoscrolls(false);
		input.setAlignmentX(0.0f);
		input.addKeyListener(inputHandler);
		inputPanel.setLayout(new BorderLayout(0, 0));
		inputPanel.add(input);

		outputPanel = new JPanel();
		outputPanel.setLocation(textStripWrapper.leftPole,playerPanelWrapper.getY());
		outputPanel.setSize(textStripWrapper.getCenterWidth(), (screenSize.height - playerPanelWrapper.getY() - infoPanel.getHeight()));
		outputPanel.setPreferredSize(outputPanel.getSize());
		outputPanel.setOpaque(false);
		outputPanel.setBorder(null);
		//outputPanel.setBounds(new Rectangle(0, playerPanelWrapper.getY(), backgroundPanel.rightPole - backgroundPanel.leftPole, screenSize.height - playerPanelWrapper.getY() - inputPanel.getY()));
		outputPanel.setLayout(null);
		textStripWrapper.add(outputPanel);

		
		

		output = new JTextArea();
		output.setFocusable(false);
		output.setSelectionColor(Color.LIGHT_GRAY);
		output.setOpaque(false);
		output.setBorder(null);
		output.setBounds(0, 0, outputPanel.getWidth(), outputPanel.getHeight());
		output.setWrapStyleWord(true);
		output.setLineWrap(true);
		output.setForeground(Color.DARK_GRAY);
		output.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		output.setEditable(false);

		scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.setAutoscrolls(true);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBorder(null);
		scrollPane.setBounds(0, 0, output.getWidth(), output.getHeight());
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setViewportView(output);
		
		 verticalScrollBarMaximumValue = scrollPane.getVerticalScrollBar().getMaximum();
	    scrollPane.getVerticalScrollBar().addAdjustmentListener(
	            e -> {
	                if ((verticalScrollBarMaximumValue - e.getAdjustable().getMaximum()) == 0)
	                    return;
	                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
	                verticalScrollBarMaximumValue = scrollPane.getVerticalScrollBar().getMaximum();
	            });
		outputPanel.add(scrollPane);
		output.addMouseListener(new MouseClickedOutside(inventory));
		
		textStripWrapper.setLeflLabel(new ImageIcon(GUI.class.getResource("/ui/assets/mainIcons/text_left.png")));
		textStripWrapper.setRightLabel(new ImageIcon(GUI.class.getResource("/ui/assets/mainIcons/text_right.png")));
		textStripWrapper.setCenterLabel(new ImageIcon(GUI.class.getResource("/ui/assets/mainIcons/text_center.png")));
		
		backgroundPanel.setLeflLabel(new ImageIcon(GUI.class.getResource("/ui/assets/mainIcons/background_left.png")));
		backgroundPanel.setRightLabel(new ImageIcon(GUI.class.getResource("/ui/assets/mainIcons/background_right.png")));
		//backgroundPanel.setCenterLabel(new ImageIcon(GUI.class.getResource("/ui/assets/mainIcons/textCenter.png")));

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
	// private ResizeablePanel infoPanel;
	private JPanel infoPanel;
	private JLabel healthPersentageLabel;
	private JLabel healthInfoLabel;
	private JLabel statusNameLabel;
	private JLabel statusDescriptionLabel;
	//private JPanel inputPanel;
	private JTextField input;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel centerPanel;
	private void inventoryButtonLabelMouseClicked(MouseEvent evt) {// GEN-FIRST:event_inventoryButtonLabelMouseClicked
		if (!inventory.isEnabled()) {
			inventory.open();
		} else {
			inventory.close();
		}
	}

	public void updateScale() {
		// gameFrame.pack();
		Dimension screenSize = gameFrame.getSize();
		inventory.updateScreenSize(screenSize);
		itemFoundPanel.updateScreenSize(screenSize);
		backgroundPanel.setSize(screenSize);
		backgroundPanel.scale();
		controlPanelWrapper.setLocation((int) (screenSize.width - controlPanelWrapper.getWidth())- 20, (int)(0.016*screenSize.height));
		playerPanelWrapper.setLocation((int) backgroundPanel.getLeftPole() - playerPanelWrapper.getWidth() + 24 , 114);
		infoPanelWrapper.setSize(backgroundPanel.getLeftPole(),218);
		infoPanelWrapper.setLocation(1, screenSize.height - infoPanelWrapper.getHeight());
		
		textStripWrapper.setSize(backgroundPanel.getRightPole() - backgroundPanel.getLeftPole(),screenSize.height);
		textStripWrapper.scale();
		textStripWrapper.setLocation(backgroundPanel.getLeftPole(), 0);
		
		inputPanel.setLocation(textStripWrapper.leftPole, screenSize.height - (int) (infoPanel.getHeight()/2));
		inputPanel.setSize(textStripWrapper.getCenterWidth(), 30);
		input.setBounds(0, 0, inputPanel.getWidth(), 26);
		
		outputPanel.setLocation(textStripWrapper.leftPole,playerPanelWrapper.getY());
		outputPanel.setSize(textStripWrapper.getCenterWidth(), (screenSize.height - playerPanelWrapper.getY() - infoPanel.getHeight()));
		output.setBounds(0, 0, outputPanel.getWidth(), outputPanel.getHeight());
		scrollPane.setBounds(0, 0, output.getWidth(), output.getHeight());

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
				input.setText("");
				updatePlayerScreen();
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