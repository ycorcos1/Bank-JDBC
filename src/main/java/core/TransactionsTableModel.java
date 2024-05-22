package core;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TransactionsTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	private static final int DATE_COL = 0;
	private static final int TIME_COL = 1;
	private static final int SUMMARY_COL = 2;
	
	private String[] columnNames = {"Date", "Time", "Summary"};
	
	private List<Transaction> transactions;
	
	public TransactionsTableModel(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return transactions.size();
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override
    public boolean isCellEditable(int row, int col) {
         return false;
    }

	@Override
	public Object getValueAt(int row, int col) {
		Transaction transaction = transactions.get(row);
		switch(col) {
		case DATE_COL:
			return transaction.getDate().toString();
		case TIME_COL:
			return transaction.getTime().toString();
		case SUMMARY_COL:
			return transaction.getSummary();
		default:
			return String.valueOf(transaction.getTransactionId());
		}
	}
}
