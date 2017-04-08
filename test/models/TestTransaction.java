package models;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import reconciliation.Buy;

public class TestTransaction {

	private Transaction transaction;
	
	@Before
	public void setup(){
		Buy buy = Mockito.mock(Buy.class);
		Map<String,Double> result = new HashMap<>();
		result.put("Cash", 100.0);
		Mockito.when(buy.applyAction(Mockito.anyMap(), 
				Mockito.anyString(), Mockito.anyDouble(),
				Mockito.anyDouble())).thenReturn(result);
		transaction = new Transaction("AAPL",buy, 100.0, 100.0);
	}
	
	@Test
	public void testApplyAction(){
		Map<String,Double> entry = new HashMap<>();
		Map<String,Double> result = transaction.applyAction(entry);
		assertEquals(result.get("Cash"), 100.0, .001);
	}
}
