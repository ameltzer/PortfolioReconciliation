package reconciliation;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class TestFee {
	
	Fee fee;
	
	@Before
	public void setup(){
		fee = new Fee();
	}
	/**
	 * test fee transaction
	 */
	@Test
	public void testSellAction(){
		Map<String,Double> curPort = new HashMap<>();
		curPort.put("Cash", 200.0);
		double cashChange = 100;
		curPort = fee.applyAction(curPort, "Cash", 0, cashChange);
		assertEquals(curPort.get("Cash"), 100.0, .001);
	}
}
