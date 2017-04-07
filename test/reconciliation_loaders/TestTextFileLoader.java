package reconciliation_loaders;
import static org.junit.Assert.*;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
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
	/*
	 * AAPL 100
GOOG 200
SP500 175.75
Cash 1000

AAPL SELL 100 30000
GOOG BUY 10 10000
Cash DEPOSIT 0 1000
Cash FEE 0 50
GOOG DIVIDEND 0 50
TD BUY 100 10000
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
	public void assertTransaction(Transaction trans, String item, Class action, 
			double shares, double cash){
		assertEquals(trans.getItem(), item);
		assertTrue(action.isInstance(trans.getAction()));
		assertEquals(trans.getShares(), shares, .001);
		assertEquals(trans.getCashChange(), cash, .001);
	}
	@Test
	public void testLoadNextDay() throws Exception{
		ReconDay reconDay = fileLoader.loadNextDay();
		assertNotNull(reconDay);
		assertNotNull(reconDay.getNextDayInFile());
		assertNotNull(reconDay.getTransactions());
	}
	@Test(expected=Exception.class)
	public void testFailOnLoadPos() throws Exception{
		fileLoader.loadPos();
		fileLoader.loadPos();
	}
	@Test(expected=Exception.class)
	public void testFailOnLoadTrans() throws Exception{
		fileLoader.loadTrans();
	}
}
