package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import gamelogic.Item;
import gamelogic.Player;

public class InventoryPanel extends JPanel implements Closable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 609296191431481428L;
	
	private JLabel weightInfoLabel;
	private JLabel inventoryPlayerName;
    private JLabel inventoryBackground;
    private InventoryTableModel inventoryTableModel;
    //private JPanel inventoryPanel;
    private JPanel inventoryTablePanel;
    private JTable inventoryTable;
    private Player currentPlayer;
    //private Dimension frameDimension;
    

	private JScrollPane scrollPaneTable;

    public InventoryPanel(Player player)
    {
    	super();
    	this.currentPlayer = player;
    	//this.frameDimension = frameDimension;
    	initializeComponents();
    }
    
    public void initializeComponents()
    {
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	
    	//this = new JPanel();
        inventoryBackground = new JLabel();
        inventoryBackground.setLabelFor(this);
        inventoryBackground.setVerticalAlignment(SwingConstants.TOP);
        inventoryBackground.setBounds(0, 0, 636, 935);
        this.setLayout(null);
        this.setBounds(screenSize.width - this.getWidth(), 80, 636, 935);
        
        inventoryTableModel = new InventoryTableModel();
        
        
        inventoryTable = new JTable(inventoryTableModel);
        inventoryTable.setShowVerticalLines(false);
        inventoryTable.setShowHorizontalLines(false);
        inventoryTable.setShowGrid(false);
        inventoryTable.setFillsViewportHeight(true);
        inventoryTable.setIntercellSpacing(new Dimension(0, 0));
        inventoryTable.setFocusTraversalKeysEnabled(false);
        inventoryTable.setFocusable(false);
        inventoryTable.setLocation(new Point(0, -35));
        inventoryTable.setOpaque(false);
        inventoryTable.setRowHeight(37);
        inventoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        inventoryTable.setAutoCreateRowSorter(true);
        inventoryTable.setAutoscrolls(false);
        inventoryTable.setRowSelectionAllowed(false);
        inventoryTable.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        inventoryTable.setBounds(73, 894, 553, -541);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        centerRenderer.setVerticalAlignment( JLabel.CENTER );
        centerRenderer.setOpaque(false);
        centerRenderer.setBorder(null);
        inventoryTable.setDefaultRenderer(String.class, centerRenderer);
        inventoryTable.getColumnModel().getColumn(0).setMaxWidth(340);
        inventoryTable.getColumnModel().getColumn(1).setMaxWidth(95);
        inventoryTable.getColumnModel().getColumn(2).setMaxWidth(75);
        inventoryTable.getColumnModel().getColumn(3).setMaxWidth(75);
        
        scrollPaneTable = new JScrollPane();
        scrollPaneTable.setRequestFocusEnabled(false);
        scrollPaneTable.setWheelScrollingEnabled(false);
        scrollPaneTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPaneTable.setViewportBorder(null);
        scrollPaneTable.setOpaque(false);
        scrollPaneTable.setBorder(null);
        scrollPaneTable.setBounds(-3, -25, 650, 680);
        scrollPaneTable.getViewport().setOpaque(false);
        scrollPaneTable.setViewportView(inventoryTable);
        
        inventoryTablePanel = new JPanel();
        
        
        inventoryTablePanel.setBounds(61, 352, 560, 551);
        inventoryTablePanel.setOpaque(false);
        inventoryTablePanel.setLayout(null);
        inventoryTablePanel.add(scrollPaneTable);
        
        this.add(inventoryTablePanel);
        //inventoryPanel.add(inventoryTable);
        
        weightInfoLabel = new JLabel("100/100");
        weightInfoLabel.setLabelFor(this);
        weightInfoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        weightInfoLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        weightInfoLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        weightInfoLabel.setForeground(Color.GRAY);
        weightInfoLabel.setBounds(494, 247, 121, 26);
        this.add(weightInfoLabel);
        
        inventoryPlayerName = new JLabel("PLAYER'S");
        inventoryPlayerName.setLabelFor(this);
        inventoryPlayerName.setHorizontalAlignment(SwingConstants.CENTER);
        inventoryPlayerName.setForeground(Color.GRAY);
        inventoryPlayerName.setFont(new Font("Times New Roman", Font.BOLD, 30));
        inventoryPlayerName.setBounds(250, 105, 210, 42);
        this.add(inventoryPlayerName);
        inventoryBackground.setHorizontalAlignment(SwingConstants.LEFT);
        inventoryBackground.setIcon(new ImageIcon(GUI.class.getResource("/ui/assets/mainIcons/inventory.png"))); // NOI18N
        inventoryBackground.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        this.add(inventoryBackground);
        
        
    }
    
    public void setInactive()
    {
    	this.setVisible(false);
    	this.setEnabled(false);
    }

    public void setActive()
    {
    this.setVisible(true);
    this.setEnabled(true);
    }
    
    public void update(String playerName, String currentWeight, String maxWeight, HashMap<String,Item> items, HashMap<String, Integer> itemCounts )
    {
    	inventoryPlayerName.setText(playerName.toUpperCase()+"'S");
    	weightInfoLabel.setText(currentWeight+"/"+maxWeight);
    	inventoryTableModel.fillWithBlanks();
    	
    	if(!itemCounts.isEmpty())
    	{
    	for(String itemName: itemCounts.keySet())
    	{	
    			Item item = items.get(itemName);
        		String data[] = {item.getName(), itemCounts.get(itemName) + "", item.getWeight() + "",item.getPrice() + ""};
        		inventoryTableModel.appendRow(data);
    			
    	}
    	}
    	revalidate();
    }
    

    public void update()
    {
    	inventoryPlayerName.setText(currentPlayer.getName().toUpperCase()+"'S");
    	weightInfoLabel.setText(currentPlayer.getCurrentWeight()+"/"+currentPlayer.getMaxWeight());
    	inventoryTableModel.fillWithBlanks();
    	
    	if(!currentPlayer.getInventoryCounts().isEmpty())
    	{
    		for(String itemName: currentPlayer.getInventoryCounts().keySet())
    		{	
    			Item item = currentPlayer.getItems().get(itemName);
        		String data[] = {item.getName(), currentPlayer.getInventoryCounts().get(itemName) + "", item.getWeight() + "",item.getPrice() + ""};
        		System.out.println(data);
        		inventoryTableModel.appendRow(data);
        		revalidate();
        		
    		}
    	}
    	revalidate();
    }
    
    public void open()
    {
    	Dimension frameDimension = Toolkit.getDefaultToolkit().getScreenSize();
    	this.setActive();
        Thread th = new Thread()
        {
        public void run()
            {
                try{
                	int	rightBound = frameDimension.width;
                    int	leftBound = rightBound - getWidth();
                    int y = getY();
                    int width =  getWidth();
                    int height =  getHeight();
                for(int a=rightBound;a>=leftBound;a-=4)
                    {
                        Thread.sleep(1);
                        setBounds(a,y,width, height);
                    }
                }
                catch(Exception e)
                {
                //System.out.println(e);
                	}
            }
        };
        th.start();
    }
    public void close()
    {
    	Dimension frameDimension = Toolkit.getDefaultToolkit().getScreenSize();
    	Thread th = new Thread()
        {
        public void run()
            {
                try{
                	int	leftBound = getX();
                	int	rightBound = frameDimension.width;
                	 int y = getY();
	                 int width =  getWidth();
	                 int height =  getHeight();
                for(int a=leftBound;a<=rightBound;a+=4)
                    {
                        Thread.sleep(1);
                        setBounds(a,y,width, height);
                    }
                }
                catch(Exception e)
                {
                //System.out.println(e);
                	}
                setInactive();
            }
        };
        th.start();
    }
    
    public void updatePlayer(Player player)
    {
    	this.currentPlayer = player;
    }
}
