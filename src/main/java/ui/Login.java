package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import core.Account;
import dao.BankDAO;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JPasswordField;

public class Login extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private BankDAO dao;
	private MainFrame mainFrame;
	
	// Panel
	private JPanel buttonPane;
	
	// Buttons
	private JButton btnLogin;
	private JButton btnCreateAccount;
	private JLabel lblTitle;
	private JPanel panel;
	
	// Labels + Textfields
	private JLabel lblUsername;
	private JTextField tfUsername;
	private JLabel lblPassword;
	private JPasswordField tfPassword;

	/**
	 * Create the dialog.
	 */
	public Login(MainFrame mainFrame, BankDAO dao) {
		setResizable(false);
		this.dao = dao;
		this.mainFrame = mainFrame;
		setTitle("Login");
		setBounds(100, 100, 450, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			lblTitle = new JLabel("Corcos Bank");
			lblTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 36));
			lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblTitle, BorderLayout.NORTH);
		}
		{
			panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0, 0};
			gbl_panel.rowHeights = new int[]{0, 0, 0};
			gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				lblUsername = new JLabel("Username:");
				GridBagConstraints gbc_lblUsername = new GridBagConstraints();
				gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
				gbc_lblUsername.anchor = GridBagConstraints.EAST;
				gbc_lblUsername.gridx = 0;
				gbc_lblUsername.gridy = 0;
				panel.add(lblUsername, gbc_lblUsername);
			}
			{
				tfUsername = new JTextField();
				GridBagConstraints gbc_tfUsername = new GridBagConstraints();
				gbc_tfUsername.insets = new Insets(0, 0, 5, 0);
				gbc_tfUsername.fill = GridBagConstraints.HORIZONTAL;
				gbc_tfUsername.gridx = 1;
				gbc_tfUsername.gridy = 0;
				panel.add(tfUsername, gbc_tfUsername);
				tfUsername.setColumns(10);
			}
			{
				lblPassword = new JLabel("Password:");
				GridBagConstraints gbc_lblPassword = new GridBagConstraints();
				gbc_lblPassword.anchor = GridBagConstraints.EAST;
				gbc_lblPassword.insets = new Insets(0, 0, 0, 5);
				gbc_lblPassword.gridx = 0;
				gbc_lblPassword.gridy = 1;
				panel.add(lblPassword, gbc_lblPassword);
			}
			{
				tfPassword = new JPasswordField();
				GridBagConstraints gbc_passwordField = new GridBagConstraints();
				gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
				gbc_passwordField.gridx = 1;
				gbc_passwordField.gridy = 1;
				panel.add(tfPassword, gbc_passwordField);
			}
		}
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnLogin = new JButton("Login");
				btnLogin.setSelected(false);
				btnLogin.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						login();
					}
				});
				btnLogin.setActionCommand("");
				buttonPane.add(btnLogin);
				getRootPane().setDefaultButton(btnLogin);
			}
			{
				btnCreateAccount = new JButton("Create Account");
				btnCreateAccount.setSelected(false);
				btnCreateAccount.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						createAccount();
					}
				});
				btnCreateAccount.setActionCommand("Cancel");
				buttonPane.add(btnCreateAccount);
			}
		}
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	private void login(){
		String username = tfUsername.getText();
		@SuppressWarnings("deprecation")
		String password = tfPassword.getText();
		if(username.equals("") || password.equals("")) {
			errorMessage("Field(s) empty");
		}else {
			Account account = dao.getAccount(username, password);
			if(account == null) {
				errorMessage("Account Not Found OR Username and Password Do Not Match");
			}else {
				mainFrame.setAccount(account);
				close();
			}
		}
	}
	
	private void errorMessage(String message) {
		JOptionPane.showMessageDialog(Login.this, 
				message, "ERROR", 
				JOptionPane.ERROR_MESSAGE);
	}
	
	private void close() {
		setVisible(false);
		dispose();
	}
	
	private void createAccount() {
		new CreateAccountDialog(dao);
	}

}
