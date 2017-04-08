package reconciliation;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class TestDeposit {
	
	Deposit deposit;
	
	@Before
	public void setup(){
		deposit = new Deposit();
	}
	
	@Test
	public void testSellAction(){
		Map<String,Double> curPort = new HashMap<>();
		curPort.put("Cash", 200.0);
		double cashChange = 100;
		curPort = deposit.applyAction(curPort, "Cash", 0, cashChange);
		assertEquals(curPort.get("Cash"), 300.0, .001);
	}
}
