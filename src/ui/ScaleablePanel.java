package ui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScaleablePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2857244617989235244L;

	public int leftPole, rightPole;
	private JLabel backgroundLeft, backgroundRight, backgroundCenter;
	private Rectangle leftBounds, rightBounds, centerBounds;
	
	public ScaleablePanel (Dimension size, int leftPole, int rightPole) {
		super();
		setLayout(null);
		this.leftPole = leftPole;
		this.rightPole = rightPole;
		setBounds(0,0,size.width,size.height);
		
		leftBounds = new Rectangle( 0, 0, leftPole, getHeight());
		rightBounds = new Rectangle(rightPole, 0, (getWidth() - rightPole), getHeight());
		centerBounds = new Rectangle(leftPole, 0, (rightPole - leftPole), getHeight());
		
	}
	public void setLeflLabel(ImageIcon icon)
	{
		backgroundLeft = new JLabel("left label");
		backgroundLeft.setIcon(icon);
		backgroundLeft.setBounds(0, 0, leftPole, getHeight());
		add(backgroundLeft);
	}
	
	public void setRightLabel(ImageIcon icon)
	{
		backgroundRight = new JLabel("right label");
		backgroundRight.setIcon(icon);
		backgroundRight.setBounds(rightPole, 0, (getWidth() - rightPole), getHeight());
		add(backgroundRight);
	}
	public void setCenterLabel(ImageIcon icon)
	{
		backgroundCenter = new JLabel("center label");
		backgroundCenter.setIcon(icon);
		backgroundCenter.setBounds(leftPole, 0, (rightPole - leftPole), getHeight());
		add(backgroundCenter);
		scale();
	}
	public void scale()
	{//scales the labels to fit the current size of the panel - related to the panel's height
		if(backgroundLeft!=null)
		{
			
		double ratio = ((double)backgroundLeft.getWidth()/ (double)backgroundLeft.getHeight());
		
		backgroundLeft.setBounds(0,0,(int)(ratio*getHeight()),getHeight());
		ImageIcon icon = (ImageIcon) backgroundLeft.getIcon();
		Image orig = icon.getImage();
		Image scaledImage = orig.getScaledInstance(backgroundLeft.getWidth(), backgroundLeft.getHeight(), orig.SCALE_SMOOTH);
		backgroundLeft.setIcon(new ImageIcon(scaledImage));
		
		leftBounds =  backgroundLeft.getBounds();
		leftPole = backgroundLeft.getWidth();
		}
		
		
				
		if(backgroundRight!=null)
		{
			double ratio = ((double)backgroundRight.getWidth()/ (double)backgroundRight.getHeight());
			
		backgroundRight.setBounds(rightPole,0,(int)(ratio*getHeight()),getHeight());
		ImageIcon icon = (ImageIcon) backgroundRight.getIcon();
		Image orig = icon.getImage();
		Image scaledImage = orig.getScaledInstance(backgroundRight.getWidth(), backgroundRight.getHeight(), orig.SCALE_SMOOTH);
		backgroundRight.setIcon(new ImageIcon(scaledImage));
		
		
		rightBounds =  backgroundRight.getBounds();
		rightPole = (getWidth() - backgroundRight.getWidth());
		backgroundRight.setLocation(rightPole,0);
		
		}
		
		
		
		if(backgroundCenter!=null)
		{
		//double ratio = backgroundCenter.getWidth()/ backgroundCenter.getHeight();
		backgroundCenter.setBounds(leftPole,0,(int)( rightPole-leftPole),getHeight());
		ImageIcon icon = (ImageIcon) backgroundCenter.getIcon();
		Image orig = icon.getImage();
		Image scaledImage = orig.getScaledInstance(backgroundCenter.getWidth(), backgroundCenter.getHeight(), orig.SCALE_SMOOTH);
		backgroundCenter.setIcon(new ImageIcon(scaledImage));
		
		centerBounds =  backgroundCenter.getBounds();
		}
		
	}
	public int getLeftPole()
	{
		return leftPole;
	}
	public int getRightPole()
	{
		return rightPole;
	}
	public Rectangle getLeftBounds()
	{
		return leftBounds;
	}
	public Rectangle getRightBounds()
	{
		return rightBounds;
	}
	public Rectangle getCenterBounds()
	{
		return centerBounds;
	}
	public int getCenterWidth()
	{
		return (rightPole - leftPole);
	}
	
}
