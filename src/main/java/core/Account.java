package core;

import java.math.BigDecimal;

public class Account {
	
	private int id;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private BigDecimal checkingsBalance;
	private BigDecimal savingsBalance;
	
	public Account(String firstName, String lastName, String username, String password, BigDecimal checkingsBalance, BigDecimal savingsBalance) {
		this(0, firstName, lastName, username, password, checkingsBalance, savingsBalance);
	}

	public Account(int id, String firstName, String lastName, String username, String password, BigDecimal checkingsBalance, BigDecimal savingsBalance) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.username = username;
		this.password = password;
		this.checkingsBalance = checkingsBalance;
		this.savingsBalance = savingsBalance;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public BigDecimal getCheckingsBalance() {
		return this.checkingsBalance;
	}
	
	public void depositCheckings(BigDecimal amount) {
		this.checkingsBalance = this.checkingsBalance.add(amount);
	}
	
	public boolean withdrawCheckings(BigDecimal amount) {
		if(this.checkingsBalance.compareTo(amount) == -1) {
			return false;
		}else {
			this.checkingsBalance = this.checkingsBalance.subtract(amount);
			return true;
		}
	}
	
	public BigDecimal getSavingsBalance() {
		return this.savingsBalance;
	}
	
	public void depositSavings(BigDecimal amount) {
		savingsBalance = this.savingsBalance.add(amount);
	}
	
	public boolean withdrawSavings(BigDecimal amount) {
		if(this.savingsBalance.compareTo(amount) == -1) {
			return false;
		}else {
			this.savingsBalance = this.savingsBalance.subtract(amount);
			return true;
		}
	}
	
	public void changeUsername(String username) {
		this.username = username;
	}
	
	public void changePassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "Account [id=" + this.id + ", lastName=" + this.lastName + ", firstName=" + this.firstName 
				+ ", checkings=" + this.checkingsBalance.toString() + ", savings=" + this.savingsBalance.toString() 
				+ "username=" + this.username + ", password=" + this.password + "]";
	}

}
