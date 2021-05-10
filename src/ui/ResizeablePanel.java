package ui;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResizeablePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6898679870000605225L;
	/**
	 * 
	 */
	private JLabel background;
	
	public ResizeablePanel()
	{
		super();
		//this.setLayout(null);
		//this.setPreferredSize(size);
		
		//background = new JLabel();
		//background.setIcon(icon);
		//background.setBounds(0,0,size.width,size.height);
		//background.setPreferredSize(size);
		
		//this.add(background);
		//scaleBackground();
	}
	public void addBackground(JLabel label)
	{
		background = new JLabel(); 
		background = label;
	
		this.add(background);
		scaleBackground();
	}
	public void scaleBackground()
	
	{	
		if(background!=null)
		{
		Dimension newSize = new Dimension (getWidth(),getHeight());
	
		background.setBounds(0,0,getWidth(),getHeight());
		ImageIcon icon = (ImageIcon) background.getIcon();
		Image orig = icon.getImage();
		Image scaledImage = orig.getScaledInstance(background.getWidth(), background.getHeight(), orig.SCALE_SMOOTH);
		background.setIcon(new ImageIcon(scaledImage));
		}
	}

}
