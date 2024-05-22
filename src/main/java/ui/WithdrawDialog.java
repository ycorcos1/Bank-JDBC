package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import core.Account;
import core.Transaction;
import dao.BankDAO;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class WithdrawDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private Account account;
	private BankDAO dao;
	private MainFrame mainFrame;
	private final JPanel contentPanel = new JPanel();
	
	// TextField
	private JTextField tfAmount;
	
	// Labels
	private JLabel lblCheckingsBalance;
	private JLabel lblSavingsBalance;
	
	// ComboBox
	private JComboBox<String> comboBox;

	/**
	 * Create the dialog.
	 */
	public WithdrawDialog(MainFrame mainFrame, Account account, BankDAO dao) {
		this.account = account;
		this.dao = dao;
		this.mainFrame = mainFrame;
		setResizable(false);
		setTitle("Withdraw");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblCheckings = new JLabel("Checkings Balance:");
			GridBagConstraints gbc_lblCheckings = new GridBagConstraints();
			gbc_lblCheckings.insets = new Insets(0, 0, 5, 5);
			gbc_lblCheckings.gridx = 0;
			gbc_lblCheckings.gridy = 0;
			contentPanel.add(lblCheckings, gbc_lblCheckings);
		}
		{
			lblCheckingsBalance = new JLabel("$ " + this.account.getCheckingsBalance().toString());
			GridBagConstraints gbc_lblCheckingsBalance = new GridBagConstraints();
			gbc_lblCheckingsBalance.insets = new Insets(0, 0, 5, 0);
			gbc_lblCheckingsBalance.gridx = 1;
			gbc_lblCheckingsBalance.gridy = 0;
			contentPanel.add(lblCheckingsBalance, gbc_lblCheckingsBalance);
		}
		{
			JLabel lblSavings = new JLabel("Savings Balance");
			GridBagConstraints gbc_lblSavings = new GridBagConstraints();
			gbc_lblSavings.insets = new Insets(0, 0, 5, 5);
			gbc_lblSavings.gridx = 0;
			gbc_lblSavings.gridy = 1;
			contentPanel.add(lblSavings, gbc_lblSavings);
		}
		{
			lblSavingsBalance = new JLabel("$ " + this.account.getSavingsBalance().toString());
			GridBagConstraints gbc_lblSavingsBalance = new GridBagConstraints();
			gbc_lblSavingsBalance.insets = new Insets(0, 0, 5, 0);
			gbc_lblSavingsBalance.gridx = 1;
			gbc_lblSavingsBalance.gridy = 1;
			contentPanel.add(lblSavingsBalance, gbc_lblSavingsBalance);
		}
		{
			comboBox = new JComboBox<>();
			comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"CHECKINGS", "SAVINGS"}));
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.insets = new Insets(0, 0, 5, 0);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 1;
			gbc_comboBox.gridy = 2;
			contentPanel.add(comboBox, gbc_comboBox);
		}
		{
			JLabel lblAmount = new JLabel("Amount:");
			GridBagConstraints gbc_lblAmount = new GridBagConstraints();
			gbc_lblAmount.anchor = GridBagConstraints.EAST;
			gbc_lblAmount.insets = new Insets(0, 0, 0, 5);
			gbc_lblAmount.gridx = 0;
			gbc_lblAmount.gridy = 7;
			contentPanel.add(lblAmount, gbc_lblAmount);
		}
		{
			tfAmount = new JTextField();
			GridBagConstraints gbc_tfAmount = new GridBagConstraints();
			gbc_tfAmount.fill = GridBagConstraints.HORIZONTAL;
			gbc_tfAmount.gridx = 1;
			gbc_tfAmount.gridy = 7;
			contentPanel.add(tfAmount, gbc_tfAmount);
			tfAmount.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							ok();
						} catch (SQLException e1) {
							// data base error
							e1.printStackTrace();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						close();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	private void ok() throws SQLException{
		if(comboBox.getSelectedIndex() == 0) {
			checkingsHandler();
		}else {
			savingsHandler();
		}
	}
	
	private void checkingsHandler() {
		BigDecimal amount = new BigDecimal(tfAmount.getText() + ".00");
		if(account.withdrawCheckings(amount)) {
			dao.withdrawCheckings(account, amount);
			close();
		}else {
			errorMessage("Insufficient Funds!");
			dao.addNewTransaction(new Transaction(account.getId(), "Insufficient Funds: Attempted to withdraw " 
					+ amount.toString() + "from CHECKINGS"));
		}
	}
	
	private void savingsHandler() {
		BigDecimal amount = new BigDecimal(tfAmount.getText() + ".00");
		if(account.withdrawSavings(amount)) {
			dao.withdrawSavings(account, amount);
			close();
		}else {
			errorMessage("Insufficient Funds!");
			dao.addNewTransaction(new Transaction(account.getId(), "Insufficient Funds: Attempted to withdraw " 
																	+ amount.toString() + "from SAVINGS"));
		}
	}
	
	private void errorMessage(String message) {
		JOptionPane.showMessageDialog(WithdrawDialog.this, 
				message, "ERROR", 
				JOptionPane.ERROR_MESSAGE);
	}

	private void close() {
		setVisible(false);
		dispose();
		mainFrame.updateAccount(account);
	}
}
