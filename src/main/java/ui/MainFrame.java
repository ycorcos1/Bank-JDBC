package ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import core.Account;
import core.Transaction;
import core.TransactionsTableModel;
import dao.BankDAO;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel container;
	private Account account;
	private BankDAO dao;
//	private Login login;
	
	// Panels
	private JPanel northPanel;
	private JPanel wdPanel;
	private JPanel checkingsPanel;
	private FlowLayout checkingsPanelLayout;
	private JPanel savingsPanel;
	private FlowLayout savingsPanelLayout;
	private JPanel deleteAccountPanel;
	private JPanel titlePanel;
	
	// Table
	private JScrollPane tablePanel;
	private JTable transactionsTable;
	
	// Buttons
	private JButton btnDeposit;
	private JButton btnWithdraw;
	private JButton btnDeleteAccount;
	private JButton btnLogout;
	private JButton btnChangePassword;
	private JButton btnChangeUsername;
	
	// Labels
	private JLabel lblCheckings;
	private JLabel lblCheckingsBalance;
	private JLabel lblSavings;
	private JLabel lblSavingsBalance;
	private JLabel lblName;
	
	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public MainFrame(BankDAO dao) {
		setResizable(false);
		this.dao = dao;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		setVisible(false);
		new Login(this, dao);
	}
	
	public void setAccount(Account account){
		this.account = account;
		this.setTitle("Bank");
		intializeContainer();
		initializeNorthPanel();
		initializeCheckingsPanel();
		initializeSavingsPanel();
		initializeButtons();
		initializeTable();
		initializeDeleteAccountPanel();
		setVisible(true);
	}

	private void intializeContainer() {
		container = new JPanel();
		container.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(container);
		container.setLayout(new BorderLayout(0, 0));
	}
	
	private void initializeNorthPanel() {
		northPanel = new JPanel();
		container.add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
		titlePanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) titlePanel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		northPanel.add(titlePanel);
		lblName = new JLabel(account.getLastName() + ", " + account.getFirstName());
		titlePanel.add(lblName);
		
		// CHANGE USERNAME BUTTON
		btnChangeUsername = new JButton("Change Username");
		btnChangeUsername.setSelected(false);
		btnChangeUsername.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeUsername();
			}
		});
		titlePanel.add(btnChangeUsername);
		
		// CHANGE PASSWORD BUTTON
		btnChangePassword = new JButton("Change Password");
		btnChangePassword.setSelected(false);
		btnChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changePassword();
			}
		});
		titlePanel.add(btnChangePassword);
		
		// LOGOUT BUTTON
		btnLogout = new JButton("Logout");
		btnLogout.setSelected(false);
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logout();
			}
		});
		titlePanel.add(btnLogout);
	}

	private void initializeCheckingsPanel() {
		checkingsPanel = new JPanel();
		checkingsPanelLayout = (FlowLayout) checkingsPanel.getLayout();
		checkingsPanelLayout.setAlignment(FlowLayout.LEFT);
		northPanel.add(checkingsPanel);
		lblCheckings = new JLabel("Checkings:");
		checkingsPanel.add(lblCheckings);
		lblCheckingsBalance = new JLabel("$ " + account.getCheckingsBalance().toString());
		checkingsPanel.add(lblCheckingsBalance);
	}

	private void initializeSavingsPanel() {
		savingsPanel = new JPanel();
		savingsPanelLayout = (FlowLayout) savingsPanel.getLayout();
		savingsPanelLayout.setAlignment(FlowLayout.LEFT);
		northPanel.add(savingsPanel);
		lblSavings = new JLabel("Savings:");
		savingsPanel.add(lblSavings);
		lblSavingsBalance = new JLabel("$ " + account.getSavingsBalance().toString());
		savingsPanel.add(lblSavingsBalance);
		wdPanel = new JPanel();
		northPanel.add(wdPanel);
	}

	private void initializeButtons() {
		// DEPOSIT
		btnDeposit = new JButton("Deposit");
		btnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deposit();
			}
		});
		wdPanel.add(btnDeposit);
		
		// WITHDRAW
		btnWithdraw = new JButton("Withdraw");
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				withdraw();
			}
		});
		wdPanel.add(btnWithdraw);
	}

	private void initializeTable(){
		tablePanel = new JScrollPane();
		container.add(tablePanel, BorderLayout.CENTER);
		transactionsTable = new JTable();
		
		List<Transaction> transactions = null;
		transactions = dao.getTransactionsForAccount(account);
		TransactionsTableModel tableModel = new TransactionsTableModel(transactions);
		transactionsTable.setModel(tableModel);
		tablePanel.setViewportView(transactionsTable);
	}
	
	private void initializeDeleteAccountPanel() {
		deleteAccountPanel = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) deleteAccountPanel.getLayout();
		flowLayout_2.setAlignment(FlowLayout.RIGHT);
		container.add(deleteAccountPanel, BorderLayout.SOUTH);
		
		btnDeleteAccount = new JButton("Delete Account");
		btnDeleteAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confirmDeletion();
			}
		});
		deleteAccountPanel.add(btnDeleteAccount);
	}
	
	public void updateAccount(Account account) {
		setAccount(account);
	}
	
	private void deposit() {
		new DepositDialog(this, account, dao);
	}
	
	private void withdraw() {
		new WithdrawDialog(this, account, dao);
	}
	
	private void changeUsername() {
		new ChangeUsernameDialog(this, account, dao);
	}
	
	private void changePassword() {
		new ChangePasswordDialog(this, account, dao);
	}
	
	private void logout() {
		setVisible(false);
		new Login(this, dao);
	}
	
	private void confirmDeletion() {
		new ConfirmDeletionDialog(this);
	}
	
	public void deleteAccount(){
		dao.beginDeletion(account.getId());
		setVisible(false);
		new Login(this, dao);
	}

}
