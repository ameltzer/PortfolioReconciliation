package reconciliation_loaders;
import static org.junit.Assert.*;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import models.ReconDay;
import models.Transaction;
import reconciliation.Buy;
import reconciliation.Deposit;
import reconciliation.Dividend;
import reconciliation.Fee;
import reconciliation.Sell;

public class TestTextFileLoader {
	
	private TextFileLoader fileLoader;
	
	@Before
	public void setup() throws IOException{
		fileLoader = new TextFileLoader("test_data.txt");
	}
	
	/**
	 * Test load position correctly
	 * @throws Exception
	 */
	@Test
	public void testLoadPos() throws Exception{
		Map<String,Double> pos = fileLoader.loadPos();
		double applAmt = (double)pos.get("AAPL");
		assertEquals(applAmt, 100.0,0.01);
		double googAmt = (double)pos.get("GOOG");
		assertEquals(googAmt, 200,.01);
		double sp500 = pos.get("SP500");
		assertEquals(sp500, 175.75, .0001);
		double cash = pos.get("Cash");
		assertEquals(cash, 1000, .001);
	}
	
	/**
	 * test load transaction correctly
	 * @throws Exception
	 */
	@Test
	public void testLoadTrns() throws Exception{
		fileLoader.loadPos();
		List<Transaction> transactions = fileLoader.loadTrans();
		assertTransaction(transactions.get(0),"AAPL",Sell.class,100,30000);
		assertTransaction(transactions.get(1),"GOOG",Buy.class,10,10000);
		assertTransaction(transactions.get(2),"Cash", Deposit.class, 0, 1000);
		assertTransaction(transactions.get(3),"Cash", Fee.class, 0, 50);
		assertTransaction(transactions.get(4),"GOOG", Dividend.class, 0, 50);
		 
	}
	
	/** simplify asserting a transaction by factoring common code into a function that can be called in one line
	 */
	public void assertTransaction(Transaction trans, String item, Class action, 
			double shares, double cash){
		assertEquals(trans.getItem(), item);
		assertTrue(action.isInstance(trans.getAction()));
		assertEquals(trans.getShares(), shares, .001);
		assertEquals(trans.getCashChange(), cash, .001);
	}
	/**
	 * test loads next day (transaction and position) correctly
	 * @throws Exception
	 */
	@Test
	public void testLoadNextDay() throws Exception{
		fileLoader.loadPos();
		ReconDay reconDay = fileLoader.loadNextDay();
		assertNotNull(reconDay);
		assertNotNull(reconDay.getNextDayInFile());
		assertNotNull(reconDay.getTransactions());
	}
	/**
	 * test if trying to load pos while not over a position throws an error
	 * @throws Exception
	 */
	@Test(expected=Exception.class)
	public void testFailOnLoadPos() throws Exception{
		fileLoader.loadPos();
		fileLoader.loadPos();
	}
	/**
	 * test if trying to load trans while not over a transaction throws an error
	 * @throws Exception
	 */
	@Test(expected=Exception.class)
	public void testFailOnLoadTrans() throws Exception{
		fileLoader.loadTrans();
	}
	
	/**
	 * test situation in which code needs to manually add Cash to portfolio. If cash was not in initial position, that means
	 * it is implicitly 0, but for code to work Cash needs to be in the portfolio and explicitly 0
	 * @throws Exception
	 */
	@Test
	public void testNoCash() throws Exception{
		fileLoader = new TextFileLoader("test_data_no_cash.txt");
		Map<String,Double> pos0 = fileLoader.loadPos();
		assertEquals(pos0.get("Cash"),0.0,.001);
	}
}
