package reconciliation;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class TestBuy {
	
	Buy buy;
	
	@Before
	public void setup(){
		buy = new Buy();
	}
	/**
	 * test buy transaction in which item already exists in portfolio
	 */
	@Test
	public void testBuyAction(){
		Map<String,Double> curPort = new HashMap<>();
		String item = "AAPL";
		curPort.put(item, 100.0);
		curPort.put("Cash", 200.0);
		double shares = 50;
		double cashChange = 100;
		curPort = buy.applyAction(curPort, item, shares, cashChange);
		assertEquals(curPort.get(item), 150.0, .001);
		assertEquals(curPort.get("Cash"), 100.0, .001);
	}
	/**
	 * test buy transaction in which item did not already exist in portfolio
	 */
	@Test
	public void testNewBuyAction(){
		Map<String,Double> curPort = new HashMap<>();
		String item = "AAPL";
		curPort.put("Cash", 200.0);
		double shares = 50;
		double cashChange = 100;
		curPort = buy.applyAction(curPort, item, shares, cashChange);
		assertEquals(curPort.get(item), 50.0, .001);
		assertEquals(curPort.get("Cash"), 100.0, .001);
	}
}
