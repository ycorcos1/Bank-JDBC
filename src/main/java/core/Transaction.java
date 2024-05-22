package core;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
	
	private int transaction_id;
	private int account_id;
	private Date date;
	private Time time;
	private String summary;
	
	public Transaction(int account_id, String summary) {
		this(0, account_id, summary);
	}
	
	public Transaction(int transaction_id, int account_id, String summary) {
		super();
		this.transaction_id = transaction_id;
		this.account_id = account_id;
		this.date = Date.valueOf(LocalDate.now());
		this.time = Time.valueOf(LocalTime.now());
		this.summary = summary;
	}
	
	public String getSummary() {
		return this.summary;
	}
	
	public int getAccountId() {
		return this.account_id;
	}
	
	public int getTransactionId() {
		return this.transaction_id;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public Time getTime() {
		return this.time;
	}
	
}