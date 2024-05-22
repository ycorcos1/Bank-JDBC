package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import core.Account;
import core.Transaction;

public class BankDAO {
	
	private Connection conn;
	private String url = "jdbc:postgresql://localhost/bank?user=postgres&password=Airjordan1";
	
	public BankDAO() {
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			dbConnectionError();
			e.printStackTrace();
		}
	}
	
	public void depositCheckings(Account account, BigDecimal amount){
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement("update checkings set checkingsBalance=" 
									+ account.getCheckingsBalance().toString() 
									+ " where account_id=" + account.getId());
			stat.executeUpdate();
		} catch (SQLException e) {
			dbConnectionError();
			e.printStackTrace();
		}finally {
			close(stat);
			addNewTransaction(new Transaction(account.getId(), "Deposited $" + amount.toString() 
														+ " from CHECKINGS account. Updated Balance: "
														+ account.getCheckingsBalance().toString()));
		}
	}
	
	public void depositSavings(Account account, BigDecimal amount){
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement("update savings set savingsBalance=" 
					+ account.getSavingsBalance().toString() 
					+ " where account_id=" + account.getId());
			stat.executeUpdate();
		} catch (SQLException e) {
			dbConnectionError();
			e.printStackTrace();
		}finally {
			close(stat);
			addNewTransaction(new Transaction(account.getId(), "Deposited $" + amount.toString() 
														+ " from SAVINGS account. Updated Balance: "
														+ account.getSavingsBalance().toString()));
		}
	}
	
	public void withdrawCheckings(Account account, BigDecimal amount){
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement("update checkings set checkingsBalance=" 
					+ account.getCheckingsBalance().toString() 
					+ " where account_id=" + account.getId());
			stat.executeUpdate();
		} catch (SQLException e) {
			dbConnectionError();
			e.printStackTrace();
		}finally {
			close(stat);
			addNewTransaction(new Transaction(account.getId(), "Withdrew $" + amount.toString() 
														+ " from CHECKINGS account. Updated Balance: " 
														+ account.getCheckingsBalance().toString()));
		}
	}
	
	public void withdrawSavings(Account account, BigDecimal amount){
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement("update savings set savingsBalance=" 
					+ account.getSavingsBalance().toString() 
					+ " where account_id=" + account.getId());
			stat.executeUpdate();
		} catch (SQLException e) {
			dbConnectionError();
			e.printStackTrace();
		}finally {
			close(stat);
			addNewTransaction(new Transaction(account.getId(), "Withdrew $" + amount.toString() 
														+ " from SAVINGS account. Updated Balance: " 
														+ account.getSavingsBalance().toString()));
		}
	}
	
	public void updateUsername(Account account, String oldUsername) {
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement("update login set username='" 
					+ account.getUsername() 
					+ "' where account_id=" + account.getId());
			stat.executeUpdate();
		} catch (SQLException e) {
			dbConnectionError();
			e.printStackTrace();
		}finally {
			close(stat);
			addNewTransaction(new Transaction(account.getId(), "Username Change: " + oldUsername + " to " + account.getUsername()));
		}
	}
	
	public void updatePassword(Account account, String oldPassword) {
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement("update login set pword='" 
					+ account.getPassword() 
					+ "' where account_id=" + account.getId());
			stat.executeUpdate();
		} catch (SQLException e) {
			dbConnectionError();
			e.printStackTrace();
		}finally {
			close(stat);
			addNewTransaction(new Transaction(account.getId(), "Password Change: " + oldPassword + " to " + account.getPassword()));
		}
	}
	
	public void addNewAccount(Account account){
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement("insert into accounts " 
							+ "(firstName, lastName) "
							+ "values (?, ?)");
			stat.setString(1, account.getFirstName());
			stat.setString(2, account.getLastName());
			stat.executeUpdate();
		} catch (SQLException e) {
			dbConnectionError();
			e.printStackTrace();
		}finally {
			close(stat);
			int id = getRecentAccount();
			addNewCheckings(id, account);
			addNewSavings(id, account);
			addLoginDetails(id, account);
			addNewTransaction(new Transaction(id, "Account Created"));
		}
	}
	
	private int getRecentAccount(){
		Statement stat = null;
		ResultSet rs = null;
		List<Integer> accountIds = new ArrayList<>();
		try {
			stat = conn.createStatement();
			rs = stat.executeQuery("select * from accounts");
			while(rs.next()) {
				int accountId = rs.getInt("account_id");
				accountIds.add(accountId);
			}
		} catch (SQLException e) {
			dbConnectionError();
			e.printStackTrace();
		}finally {
			close(stat, rs);
		}
		int n = accountIds.size() - 1;
		return accountIds.get(n);
	}
	
	private void addLoginDetails(int account_id, Account account){
		String username = account.getUsername();
		String password = account.getPassword();
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement("insert into login "
							+ "(account_id, username, pword) "
							+ "values (?, ?, ?)");
			stat.setInt(1, account_id);
			stat.setString(2, username);
			stat.setString(3, password);
			stat.executeUpdate();
		} catch (SQLException e) {
			dbConnectionError();
			e.printStackTrace();
		}finally {
			close(stat);
		}
	}
	
	private void addNewCheckings(int account_id, Account account){
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement("insert into checkings "
							+ "(account_id, checkingsBalance) "
							+ "values (?, ?)");
			stat.setInt(1, account_id);
			stat.setBigDecimal(2, account.getCheckingsBalance());
			stat.executeUpdate();
		} catch (SQLException e) {
			dbConnectionError();
			e.printStackTrace();
		}finally {
			close(stat);
		}
	}
	
	private void addNewSavings(int account_id, Account account){
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement("insert into savings "
							+ "(account_id, savingsBalance) "
							+ "values (?, ?)");
			stat.setInt(1, account_id);
			stat.setBigDecimal(2, account.getSavingsBalance());
			stat.executeUpdate();
		} catch (SQLException e) {
			dbConnectionError();
			e.printStackTrace();
		}finally {
			close(stat);
		}
	}
	
	public void addNewTransaction(Transaction transaction){
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement("insert into transactions " 
							+ "(account_id, transaction_date, transaction_time, summary) "
							+ "values (?, ?, ?, ?)");
			stat.setInt(1, transaction.getAccountId());
			stat.setDate(2, transaction.getDate());
			stat.setTime(3, transaction.getTime());
			stat.setString(4, transaction.getSummary());
			stat.executeUpdate();
		} catch (SQLException e) {
			dbConnectionError();
			e.printStackTrace();
		}finally {
			close(stat);
		}
	}
	
	public Account getAccount(String username, String password){
		Account account = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			stat = conn.createStatement();
			rs = stat.executeQuery("select accounts.account_id, login.username, login.pword, "
					+ "accounts.firstName, accounts.lastName, checkings.checkingsBalance, savings.savingsBalance "
					+ "from accounts join login on login.account_id = accounts.account_id join checkings "
					+ "on checkings.account_id = accounts.account_id join savings on savings.account_id = accounts.account_id "
					+ "where accounts.account_id = (select account_id from login "
					+ "where (username='" + username + "' and pword='" + password + "'))");
			if(rs.next()) {
				account = convertToAccount(rs);
			}
		} catch (SQLException e) {
			dbConnectionError();
			e.printStackTrace();
		}finally {
			close(stat, rs);
		}
		return account;
	}
	
	public List<Transaction> getTransactionsForAccount(Account account){
		List<Transaction> transactions = new ArrayList<>();
		Statement stat = null;
		ResultSet rs = null;
		try {
			stat = conn.createStatement();
			rs = stat.executeQuery("select * from transactions where account_id = " + account.getId() 
					+ " order by transaction_id desc");
			while(rs.next()) {
				Transaction transaction = convertToTransaction(rs);
				transactions.add(transaction);
			}
		} catch (SQLException e) {
			dbConnectionError();
			e.printStackTrace();
		}finally {
			close(stat, rs);
		}
		return transactions;
	}
	
	public List<String> getAllUsernames(){
		List<String> usernames = new ArrayList<>();
		Statement stat = null;
		ResultSet rs = null;
		try {
			stat = conn.createStatement();
			rs = stat.executeQuery("select username from login");
			while(rs.next()) {
				String username = rs.getString("username");
				usernames.add(username);
			}
		}catch (SQLException e) {
			dbConnectionError();
			e.printStackTrace();
		}finally {
			close(stat, rs);
		}
		return usernames;
	}
	
	private Account convertToAccount(ResultSet rs) throws SQLException{
		int id = rs.getInt("account_id");
		String firstName = rs.getString("firstName");
		String lastName = rs.getString("lastName");
		String username = rs.getString("username");
		String password = rs.getString("pWord");
		BigDecimal checkingsBalance = rs.getBigDecimal("checkingsBalance");
		BigDecimal savingsBalance = rs.getBigDecimal("savingsBalance");
		Account account = new Account(id, firstName, lastName, username, password, checkingsBalance, savingsBalance);
		return account;
	}
	
	private Transaction convertToTransaction(ResultSet rs) throws SQLException{
		int transaction_id = rs.getInt("transaction_id");
		int account_id = rs.getInt("account_id");
		String summary = rs.getString("summary");
		Transaction transaction = new Transaction(transaction_id, account_id, summary);
		return transaction;
	}
	
	public void beginDeletion(int account_id){
		deleteCheckings(account_id);
	}
	
	private void deleteCheckings(int account_id){
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement("delete from checkings where account_id = " + account_id);
			stat.executeUpdate();
		} catch (SQLException e) {
			dbConnectionError();
			e.printStackTrace();
		}finally {
			close(stat);
			deleteSavings(account_id);
		}
	}
	
	private void deleteSavings(int account_id){
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement("delete from savings where account_id = " + account_id);
			stat.executeUpdate();
		} catch (SQLException e) {
			dbConnectionError();
			e.printStackTrace();
		}finally {
			close(stat);
			deleteLogin(account_id);
		}
	}
	
	private void deleteLogin(int account_id){
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement("delete from login where account_id = " + account_id);
			stat.executeUpdate();
		} catch (SQLException e) {
			dbConnectionError();
			e.printStackTrace();
		}finally {
			close(stat);
			deleteTransactions(account_id);
		}
	}
	
	private void deleteTransactions(int account_id){
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement("delete from transactions where account_id = " + account_id);
			stat.executeUpdate();
		} catch (SQLException e) {
			dbConnectionError();
			e.printStackTrace();
		}finally {
			close(stat);
			deleteAccount(account_id);
		}
	}
	
	private void deleteAccount(int account_id){
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement("delete from accounts where account_id = " + account_id);
			stat.executeUpdate();
		} catch (SQLException e) {
			dbConnectionError();
			e.printStackTrace();
		}finally {
			close(stat);
		}
	}
	
	private void close(Connection conn, Statement stat, ResultSet rs){
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				dbConnectionError();
				e.printStackTrace();
			}
		}
		if(stat != null) {
			try {
				stat.close();
			} catch (SQLException e) {
				dbConnectionError();
				e.printStackTrace();
			}
		}
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				dbConnectionError();
				e.printStackTrace();
			}
		}
	}
	
	private void close(Statement stat, ResultSet rs){
		close(null, stat, rs);
	}
	
	private void close(Statement stat){
		close(null, stat, null);
	}
	
	private void dbConnectionError() {
		System.out.println("Database Connection Error");
	}
	
}