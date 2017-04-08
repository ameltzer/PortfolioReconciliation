package reconciliation;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class TestDividend {
	
	Dividend dividend;
	
	@Before
	public void setup(){
		dividend = new Dividend();
	}
	/**
	 * test dividend transaction
	 */
	@Test
	public void testDividendAction(){
		Map<String,Double> curPort = new HashMap<>();
		curPort.put("Cash", 200.0);
		double cashChange = 100;
		curPort = dividend.applyAction(curPort, "Cash", 0.0, cashChange);
		assertEquals(curPort.get("Cash"), 300.0, .001);
	}
}
