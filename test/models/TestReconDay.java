package models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import reconciliation.Buy;

public class TestReconDay {

	private ReconDay reconDay;
	
	@Before
	public void setup(){
		Map<String,Double> nextDayInFile = new HashMap<>();
		nextDayInFile.put("AAPL", 100.0);
		nextDayInFile.put("GOOG", 10.0);
		nextDayInFile.put("Cash", 10000.0);
		
		List<Transaction> transactions = new ArrayList<>();
		Transaction transaction = Mockito.mock(Transaction.class);
		transactions.add(transaction);
		
		reconDay = new ReconDay(nextDayInFile, transactions);
	}
	
	/**
	 * test recon functionality
	 */
	@Test
	public void testDoRecon(){
		Map<String,Double> nextDayShould = new HashMap<>();
		nextDayShould.put("AAPL", 100.0);
		nextDayShould.put("MSFT", 30.0);
		nextDayShould.put("Cash", 10000.0);
		
		Map<String,Double> discrepencies = reconDay.doRecon(nextDayShould);
		assertTrue(!discrepencies.containsKey("AAPL"));
		assertEquals(-30.0, discrepencies.get("MSFT"),.001);
		assertEquals(10.0, discrepencies.get("GOOG"),.001);
		assertTrue(!discrepencies.containsKey("Cash"));
	}
	
	/**
	 * test applying all transactions functionality. This is not testing the transaction applyAction functionality. Make sure to mock that.
	 */
	@Test
	public void testApplyTransactions(){
		Map<String,Double> nextDayShould = new HashMap<>();
		nextDayShould.put("AAPL", 100.0);
		Mockito.when(reconDay.getTransactions().get(0).applyAction(Mockito.anyMap())).thenReturn(nextDayShould);
		
		Map<String,Double> parameter = new HashMap<>();
		Map<String,Double> result = reconDay.applyTransactions(parameter);
		assertEquals(result.get("AAPL"), 100.0, .001);
	}
	
}
