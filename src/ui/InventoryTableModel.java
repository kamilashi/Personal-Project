package ui;

import javax.swing.table.AbstractTableModel;

public class InventoryTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 10404977922305278L;
	private final String[] columnNames = { "ITEM", "quantity", "weight", "value" };
	private int lastFilledRow = 0;
	private boolean reset = false;
	final String[][] data = { { " ", " ", " ", " " }, { " ", " ", " ", " " }, { " ", " ", " ", " " },
			{ " ", " ", " ", " " }, { " ", " ", " ", " " }, { " ", " ", " ", " " }, { " ", " ", " ", " " },
			{ " ", " ", " ", " " }, { " ", " ", " ", " " }, { " ", " ", " ", " " }, { " ", " ", " ", " " },
			{ " ", " ", " ", " " }, { " ", " ", " ", " " }, { " ", " ", " ", " " }, { " ", " ", " ", " " },

	};

	public Class<? extends Object> getColumnClass(int column) {
		return getValueAt(0, column).getClass();
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public String getColumnName(int column) {
		return columnNames[column];
	}

	public int getRowCount() {
		return data.length;
	}

	public Object getValueAt(int row, int column) {
		return data[row][column];
	}

	public void setValueAt(int row, int column, String value) {
		data[row][column] = value;
	}
	
	public void reset()
	{
		reset = true;
	}
	
	//make 4 tables?
	public void appendRow(String[] row) {
		if(reset)
		{
			lastFilledRow = 0;
			fillWithBlanks();
			reset = false;
		}
		for (int i = 0; i < 4; i++) {
			if (lastFilledRow < data.length) {
				data[lastFilledRow][i] = row[i];
			} 
		}
		lastFilledRow++;
	}

	public void fillWithBlanks() {
		reset = true;
		for(int j = 0; j < 15; j++) {
		for (int i = 0; i < 4; i++) {
			if (lastFilledRow < data.length) {
				data[j][i] = "";
			}
			
		}
		}
	}
}
