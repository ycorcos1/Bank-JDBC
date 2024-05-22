package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import core.Account;
import dao.BankDAO;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.awt.event.ActionEvent;

public class CreateAccountDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField tfLastName;
	private JTextField tfFirstName;
	private BankDAO dao;
	private JTextField tfUsername;
	private JTextField tfPassword;
	private JTextField tfConfirm;

	/**
	 * Create the dialog.
	 */
	public CreateAccountDialog(BankDAO dao) {
		setResizable(false);
		setTitle("Create Account");
		this.dao = dao;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblLastName = new JLabel("Last Name:");
			GridBagConstraints gbc_lblLastName = new GridBagConstraints();
			gbc_lblLastName.insets = new Insets(0, 0, 5, 5);
			gbc_lblLastName.anchor = GridBagConstraints.EAST;
			gbc_lblLastName.gridx = 0;
			gbc_lblLastName.gridy = 0;
			contentPanel.add(lblLastName, gbc_lblLastName);
		}
		{
			tfLastName = new JTextField();
			GridBagConstraints gbc_tfLastName = new GridBagConstraints();
			gbc_tfLastName.insets = new Insets(0, 0, 5, 0);
			gbc_tfLastName.fill = GridBagConstraints.HORIZONTAL;
			gbc_tfLastName.gridx = 1;
			gbc_tfLastName.gridy = 0;
			contentPanel.add(tfLastName, gbc_tfLastName);
			tfLastName.setColumns(10);
		}
		{
			JLabel lblFirstName = new JLabel("First Name:");
			GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
			gbc_lblFirstName.anchor = GridBagConstraints.EAST;
			gbc_lblFirstName.insets = new Insets(0, 0, 5, 5);
			gbc_lblFirstName.gridx = 0;
			gbc_lblFirstName.gridy = 1;
			contentPanel.add(lblFirstName, gbc_lblFirstName);
		}
		{
			tfFirstName = new JTextField();
			GridBagConstraints gbc_tfFirstName = new GridBagConstraints();
			gbc_tfFirstName.insets = new Insets(0, 0, 5, 0);
			gbc_tfFirstName.fill = GridBagConstraints.HORIZONTAL;
			gbc_tfFirstName.gridx = 1;
			gbc_tfFirstName.gridy = 1;
			contentPanel.add(tfFirstName, gbc_tfFirstName);
			tfFirstName.setColumns(10);
		}
		{
			JLabel lblUsername = new JLabel("Username:");
			GridBagConstraints gbc_lblUsername = new GridBagConstraints();
			gbc_lblUsername.anchor = GridBagConstraints.EAST;
			gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
			gbc_lblUsername.gridx = 0;
			gbc_lblUsername.gridy = 2;
			contentPanel.add(lblUsername, gbc_lblUsername);
		}
		{
			tfUsername = new JTextField();
			GridBagConstraints gbc_tfUsername = new GridBagConstraints();
			gbc_tfUsername.insets = new Insets(0, 0, 5, 0);
			gbc_tfUsername.fill = GridBagConstraints.HORIZONTAL;
			gbc_tfUsername.gridx = 1;
			gbc_tfUsername.gridy = 2;
			contentPanel.add(tfUsername, gbc_tfUsername);
			tfUsername.setColumns(10);
		}
		{
			JLabel lblPassword = new JLabel("Password:");
			GridBagConstraints gbc_lblPassword = new GridBagConstraints();
			gbc_lblPassword.anchor = GridBagConstraints.EAST;
			gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
			gbc_lblPassword.gridx = 0;
			gbc_lblPassword.gridy = 3;
			contentPanel.add(lblPassword, gbc_lblPassword);
		}
		{
			tfPassword = new JTextField();
			GridBagConstraints gbc_tfPassword = new GridBagConstraints();
			gbc_tfPassword.insets = new Insets(0, 0, 5, 0);
			gbc_tfPassword.fill = GridBagConstraints.HORIZONTAL;
			gbc_tfPassword.gridx = 1;
			gbc_tfPassword.gridy = 3;
			contentPanel.add(tfPassword, gbc_tfPassword);
			tfPassword.setColumns(10);
		}
		{
			JLabel lblConfirm = new JLabel("Confirm Password:");
			GridBagConstraints gbc_lblConfirm = new GridBagConstraints();
			gbc_lblConfirm.anchor = GridBagConstraints.EAST;
			gbc_lblConfirm.insets = new Insets(0, 0, 0, 5);
			gbc_lblConfirm.gridx = 0;
			gbc_lblConfirm.gridy = 4;
			contentPanel.add(lblConfirm, gbc_lblConfirm);
		}
		{
			tfConfirm = new JTextField();
			GridBagConstraints gbc_tfConfirm = new GridBagConstraints();
			gbc_tfConfirm.fill = GridBagConstraints.HORIZONTAL;
			gbc_tfConfirm.gridx = 1;
			gbc_tfConfirm.gridy = 4;
			contentPanel.add(tfConfirm, gbc_tfConfirm);
			tfConfirm.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						createAccount();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
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
	
	public void createAccount(){
		if(notValid()) {
			errorMessage("Please fill out all field(s)");
		}else if(usernameTaken()) {
			errorMessage("Account with that username already exists. Please choose another");
		}else {
			String firstName = tfFirstName.getText();
			String lastName = tfLastName.getText();
			String username = tfUsername.getText();
			String password = tfPassword.getText();
			Account newAccount = new Account(firstName, lastName, username, password, new BigDecimal("0.00"), new BigDecimal("0.00"));
			dao.addNewAccount(newAccount);
			close();
		}
	}
	
	private boolean notValid() {
		String fName = this.tfFirstName.getText();
		String lName = this.tfLastName.getText();
		String uName = this.tfUsername.getText();
		String pWord = this.tfPassword.getText();
		String confirm = this.tfConfirm.getText();
		if(fName.equals("") || lName.equals("") || uName.equals("") || pWord.equals("") || confirm.equals("")) return true;
		if(!pWord.equals(confirm)) return true;
		return false;
	}
	
	private boolean usernameTaken() {
		String username = tfUsername.getText();
		List<String> usernames = dao.getAllUsernames();
		if(usernames.contains(username)) return true;
		return false;
	}
	
	private void errorMessage(String message) {
		JOptionPane.showMessageDialog(CreateAccountDialog.this, 
				message, "ERROR", 
				JOptionPane.ERROR_MESSAGE);
	}
	
	private void close(){
		setVisible(false);
		dispose();
	}

}
