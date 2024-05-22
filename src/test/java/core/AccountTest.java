package core;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class AccountTest {

	@Test
	void depositCheckingsTest() {
		Account account = new Account(49, "Yahav", "Corcos", "ycorcos26", "test", new BigDecimal("0.00"), new BigDecimal("0.00"));
		
		account.depositCheckings(new BigDecimal("100.00"));
		assertEquals(account.getCheckingsBalance(), new BigDecimal("100.00"));
	}
	
	@Test
	void depositSavingsTest() {
		Account account = new Account(49, "Yahav", "Corcos", "ycorcos26", "test", new BigDecimal("0.00"), new BigDecimal("0.00"));
		
		account.depositSavings(new BigDecimal("100.00"));
		assertEquals(account.getSavingsBalance(), new BigDecimal("100.00"));
	}
	
	@Test
	void withdrawCheckingsTest() {
		Account account = new Account(49, "Yahav", "Corcos", "ycorcos26", "test", new BigDecimal("100.00"), new BigDecimal("0.00"));
		
		assertEquals(account.withdrawCheckings(new BigDecimal("100.00")), true);
		account.withdrawCheckings(new BigDecimal("100.00"));
		assertEquals(account.getCheckingsBalance(), new BigDecimal("0.00"));
	}
	
	@Test
	void withdrawSavingsTest() {
		Account account = new Account(49, "Yahav", "Corcos", "ycorcos26", "test", new BigDecimal("0.00"), new BigDecimal("100.00"));
		
		assertEquals(account.withdrawSavings(new BigDecimal("100.00")), true);
		account.withdrawSavings(new BigDecimal("100.00"));
		assertEquals(account.getSavingsBalance(), new BigDecimal("0.00"));
	}
	
	@Test
	void insufficientCheckings() {
		Account account = new Account(49, "Yahav", "Corcos", "ycorcos26", "test", new BigDecimal("50.00"), new BigDecimal("0.00"));
		
		assertEquals(account.withdrawCheckings(new BigDecimal("100.00")), false);
		account.withdrawCheckings(new BigDecimal("100.00"));
		assertEquals(account.getCheckingsBalance(), new BigDecimal("50.00"));
	}
	
	@Test
	void insufficientSavings() {
		Account account = new Account(49, "Yahav", "Corcos", "ycorcos26", "test", new BigDecimal("0.00"), new BigDecimal("50.00"));
		
		assertEquals(account.withdrawSavings(new BigDecimal("100.00")), false);
		account.withdrawSavings(new BigDecimal("100.00"));
		assertEquals(account.getSavingsBalance(), new BigDecimal("50.00"));
	}

}
