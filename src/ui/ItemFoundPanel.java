package ui;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import gamelogic.Item;
import java.util.Observable;
import java.util.Observer;
import java.awt.Font;

public class ItemFoundPanel extends JPanel implements Observer  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 609296191431481428L;
	
	private JLabel weightInfoLabel;
	private JLabel panelBackground;
	private JLabel valueInfoLabel;
	private JLabel itemNameLabel;
	private int xLocation,yLocation;
	
	private Dimension frameDimension;


    public ItemFoundPanel(Dimension frameDimension)
    {
    	super();
    	this.frameDimension = frameDimension;
    	initializeComponents();
    	setInactive();
    }
    
    public void initializeComponents()
    {
    	
    	weightInfoLabel = new JLabel();
    	weightInfoLabel.setHorizontalTextPosition(SwingConstants.CENTER);
    	weightInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
    	weightInfoLabel.setText("weight");
    	weightInfoLabel.setFont(new Font("Times New Roman", Font.PLAIN, 21));
    	weightInfoLabel.setLocation(131, 486);
    	weightInfoLabel.setSize(75, 30);
    	weightInfoLabel.setLabelFor(this);
    	add(weightInfoLabel);
    	
    	valueInfoLabel = new JLabel();
    	valueInfoLabel.setHorizontalTextPosition(SwingConstants.CENTER);
    	valueInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
    	valueInfoLabel.setFont(new Font("Times New Roman", Font.PLAIN, 21));
    	valueInfoLabel.setText("value");
    	valueInfoLabel.setLocation(311, 486);
    	valueInfoLabel.setSize(75, 30);
    	valueInfoLabel.setLabelFor(this);
    	add(valueInfoLabel);
    	
    	itemNameLabel = new JLabel();
    	itemNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
    	itemNameLabel.setText("Label Name");
    	itemNameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 35));
    	itemNameLabel.setLocation(53, 48);
    	itemNameLabel.setSize(303, 44);
    	itemNameLabel.setLabelFor(this);
    	add(itemNameLabel);
    	
    	setLayout(null);
    	panelBackground = new JLabel();
    	panelBackground.setHorizontalTextPosition(SwingConstants.CENTER);
    	panelBackground.setHorizontalAlignment(SwingConstants.LEFT);
    	panelBackground.setOpaque(true);
    	panelBackground.setLabelFor(this);
    	panelBackground.setVerticalAlignment(SwingConstants.TOP);
    	panelBackground.setIcon(new ImageIcon(ItemFoundPanel.class.getResource("/ui/assets/mainIcons/itemFoundDefault.png")));
    	panelBackground.setBounds(-1, 0, panelBackground.getIcon().getIconWidth(), panelBackground.getIcon().getIconHeight());
    	add(panelBackground);
    	xLocation = (int) (0.72*frameDimension.width);	//1400 for 1080p
    	yLocation = 0;
    	setBounds(xLocation, yLocation, panelBackground.getIcon().getIconWidth()-1, panelBackground.getIcon().getIconHeight());
        
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
    public void updateScreenSize( Dimension screenSize)
    {
    	this.frameDimension = screenSize;
    	xLocation = (int) (0.72*frameDimension.width);	//1400 for 1080p
    	yLocation = 0;
    	setBounds(xLocation, yLocation, panelBackground.getIcon().getIconWidth()-1, panelBackground.getIcon().getIconHeight());
    }
    
    private void openAndClose()
    {
    	this.setActive();
        Thread th = new Thread()
        {
        public void run()
            {
                try{
                	int	upperBound = 0-getHeight();
                    int	lowerBound = 0;
                    int x = getX();
                    int y = getY();
                    int width =  getWidth();
                    int height =  getHeight();
                for(int a=upperBound;a<=lowerBound;a+=4)
                    {
                        Thread.sleep(1);
                        setBounds(x,a,width, height);
                    }
                
                Thread.sleep(2000);
                
                	upperBound = 0-getHeight();
                	lowerBound = getY();;
                	x = getX();
                	y = getY();
                	width =  getWidth();
                	height =  getHeight();
            for(int a=lowerBound;a>=upperBound;a-=4)
                {
                    Thread.sleep(1);
                    setBounds(x,a,width, height);
                }
                }
                catch(Exception e)
                {
                	}
            }
        };
        th.start();
    }

	@Override
	public void update(Observable player, Object arg) {
		if(arg!=null)
		{
		if(arg instanceof Item)
		{
			itemNameLabel.setText(((Item) arg).getName());
			weightInfoLabel.setText(((Item) arg).getWeight() +"");
			valueInfoLabel.setText(((Item) arg).getPrice() +"");
			
			openAndClose();
		}
		}
	}
    
}
