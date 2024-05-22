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
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class ChangeUsernameDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private MainFrame mainFrame;
	private Account account;
	private BankDAO dao;
	
	private JTextField tfCurrentUsername;
	private JTextField tfNewUsername;
	private JTextField tfConfirm;

	/**
	 * Create the dialog.
	 */
	public ChangeUsernameDialog(MainFrame mainFrame, Account account, BankDAO dao) {
		setTitle("Change Username");
		this.mainFrame = mainFrame;
		this.account = account;
		this.dao = dao;
		setBounds(100, 100, 450, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblTitle = new JLabel("Change Username");
			lblTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
			GridBagConstraints gbc_lblTitle = new GridBagConstraints();
			gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
			gbc_lblTitle.gridx = 1;
			gbc_lblTitle.gridy = 0;
			contentPanel.add(lblTitle, gbc_lblTitle);
		}
		{
			JLabel lblCurrentUsername = new JLabel("Current Username:");
			GridBagConstraints gbc_lblCurrentUsername = new GridBagConstraints();
			gbc_lblCurrentUsername.insets = new Insets(0, 0, 5, 5);
			gbc_lblCurrentUsername.anchor = GridBagConstraints.EAST;
			gbc_lblCurrentUsername.gridx = 0;
			gbc_lblCurrentUsername.gridy = 1;
			contentPanel.add(lblCurrentUsername, gbc_lblCurrentUsername);
		}
		{
			tfCurrentUsername = new JTextField();
			GridBagConstraints gbc_tfCurrentUsername = new GridBagConstraints();
			gbc_tfCurrentUsername.insets = new Insets(0, 0, 5, 0);
			gbc_tfCurrentUsername.fill = GridBagConstraints.HORIZONTAL;
			gbc_tfCurrentUsername.gridx = 1;
			gbc_tfCurrentUsername.gridy = 1;
			contentPanel.add(tfCurrentUsername, gbc_tfCurrentUsername);
			tfCurrentUsername.setColumns(10);
		}
		{
			JLabel lblNewUsername = new JLabel("New Username:");
			GridBagConstraints gbc_lblNewUsername = new GridBagConstraints();
			gbc_lblNewUsername.anchor = GridBagConstraints.EAST;
			gbc_lblNewUsername.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewUsername.gridx = 0;
			gbc_lblNewUsername.gridy = 2;
			contentPanel.add(lblNewUsername, gbc_lblNewUsername);
		}
		{
			tfNewUsername = new JTextField();
			GridBagConstraints gbc_tfNewUsername = new GridBagConstraints();
			gbc_tfNewUsername.insets = new Insets(0, 0, 5, 0);
			gbc_tfNewUsername.fill = GridBagConstraints.HORIZONTAL;
			gbc_tfNewUsername.gridx = 1;
			gbc_tfNewUsername.gridy = 2;
			contentPanel.add(tfNewUsername, gbc_tfNewUsername);
			tfNewUsername.setColumns(10);
		}
		{
			JLabel lblConfirm = new JLabel("Confirm New Username:");
			GridBagConstraints gbc_lblConfirm = new GridBagConstraints();
			gbc_lblConfirm.anchor = GridBagConstraints.EAST;
			gbc_lblConfirm.insets = new Insets(0, 0, 0, 5);
			gbc_lblConfirm.gridx = 0;
			gbc_lblConfirm.gridy = 3;
			contentPanel.add(lblConfirm, gbc_lblConfirm);
		}
		{
			tfConfirm = new JTextField();
			GridBagConstraints gbc_tfConfirm = new GridBagConstraints();
			gbc_tfConfirm.fill = GridBagConstraints.HORIZONTAL;
			gbc_tfConfirm.gridx = 1;
			gbc_tfConfirm.gridy = 3;
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
					public void actionPerformed(ActionEvent e) {
						change();
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
	
	private void change() {
		String currentUsername = tfCurrentUsername.getText();
		String newUsername = tfNewUsername.getText();
		String confirmUsername = tfConfirm.getText();
		if(currentUsername.equals("") || newUsername.equals("") || confirmUsername.equals("")) {
			errorMessage("Field(s) empty");
		}else if(!currentUsername.equals(account.getUsername())) {
			errorMessage("Current Username is incorrect");
		}else if(!newUsername.equals(confirmUsername)) {
			errorMessage("New usernames do NOT match");
		}else if(usernameTaken()) {
			errorMessage("Account with that username already exists! Please choose another.");
		}else {
			account.changeUsername(newUsername);
			dao.updateUsername(account, currentUsername);
			close();
		}
	}
	
	private boolean usernameTaken() {
		String username = tfNewUsername.getText();
		List<String> usernames = dao.getAllUsernames();
		if(usernames.contains(username)) return true;
		return false;
	}
	
	private void errorMessage(String message) {
		JOptionPane.showMessageDialog(ChangeUsernameDialog.this, 
				message, "ERROR", 
				JOptionPane.ERROR_MESSAGE);
	}
	
	private void close() {
		setVisible(false);
		dispose();
		mainFrame.updateAccount(account);
	}

}
