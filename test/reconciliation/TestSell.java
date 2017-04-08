package reconciliation;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class TestSell {
	
	Sell sell;
	
	@Before
	public void setup(){
		sell = new Sell();
	}
	/**
	 * test sell transaction in which stock already exists in portfolio
	 */
	@Test
	public void testSellAction(){
		Map<String,Double> curPort = new HashMap<>();
		String item = "AAPL";
		curPort.put(item, 100.0);
		curPort.put("Cash", 200.0);
		double shares = 50;
		double cashChange = 100;
		curPort = sell.applyAction(curPort, item, shares, cashChange);
		assertEquals(curPort.get(item), 50.0, .001);
		assertEquals(curPort.get("Cash"), 300.0, .001);
	}
	/**
	 * test sell transaction in which stock does not already exists in portfolio
	 */
	@Test
	public void testShortSellAction(){
		Map<String,Double> curPort = new HashMap<>();
		String item = "AAPL";
		curPort.put("Cash", 200.0);
		double shares = 50;
		double cashChange = 100;
		curPort = sell.applyAction(curPort, item, shares, cashChange);
		assertEquals(curPort.get(item), -50.0, .001);
		assertEquals(curPort.get("Cash"), 300.0, .001);
	}
}
