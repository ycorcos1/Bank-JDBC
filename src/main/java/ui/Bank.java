package ui;

import dao.BankDAO;

public class Bank {
	
	private static BankDAO dao;
	
	public static void main(String[] args){
		dao = new BankDAO();
		new MainFrame(dao);
	}
}