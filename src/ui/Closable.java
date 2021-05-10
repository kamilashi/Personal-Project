package ui;

import java.io.Serializable;

public interface Closable extends Serializable{
	public void close();

	public boolean isEnabled();

	public int getX();
	public int getY();
	public int getWidth();

	public int getHeight();

}
