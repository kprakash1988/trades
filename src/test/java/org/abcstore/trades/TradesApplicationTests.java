package org.abcstore.trades;

import java.time.LocalDate;
import java.util.List;

import org.abcstore.trades.controller.TradeController;
import org.abcstore.trades.domain.Trade;
import org.abcstore.trades.exception.InvalidTradeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class TradesApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private TradeController tradeController;

	@Test
	void testTradeValidateAndStore_successful() {
		ResponseEntity responseEntity = tradeController.addTrade(createTrade("T1", "CP-1", 1, LocalDate.now()));
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(), responseEntity);
		List<Trade> tradeList = tradeController.findAllTrades();
		Assertions.assertEquals(1, tradeList.size());
		Assertions.assertEquals("T1", tradeList.get(0).getTradeId());
	}

	@Test
	void testTradeValidateAndStoreWhenMaturityDatePast() {
		try {
			LocalDate localDate = getLocalDate(2015, 05, 21);
			ResponseEntity responseEntity = tradeController.addTrade(createTrade("T2", "CP-2", 1, localDate));
		} catch (InvalidTradeException ie) {
			Assertions.assertEquals("Invalid Trade: T2 Invalid Maturatiy date", ie.getMessage());
		}
	}

	@Test
	void testTradeValidateAndStoreWhenOldVersion() {
		// step-1 create trade
		ResponseEntity responseEntity = tradeController.addTrade(createTrade("T1", "CP-1", 2, LocalDate.now()));
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(), responseEntity);
		List<Trade> tradeList = tradeController.findAllTrades();
		Assertions.assertEquals(1, tradeList.size());
		Assertions.assertEquals("T1", tradeList.get(0).getTradeId());
		Assertions.assertEquals(2, tradeList.get(0).getVersion());
		Assertions.assertEquals("T1B1", tradeList.get(0).getBookId());

		// step-2 create trade with old version
		try {
			ResponseEntity responseEntity1 = tradeController.addTrade(createTrade("T1", "CP-1", 1, LocalDate.now()));

		} catch (InvalidTradeException e) {
			System.out.println(e.getId());
			System.out.println(e.getMessage());
		}
		List<Trade> tradeList1 = tradeController.findAllTrades();
		Assertions.assertEquals(1, tradeList1.size());
		Assertions.assertEquals("T1", tradeList1.get(0).getTradeId());
		Assertions.assertEquals(2, tradeList1.get(0).getVersion());
		Assertions.assertEquals("T1B1", tradeList.get(0).getBookId());
	}

	@Test
	void testTradeValidateAndStoreWhenSameVersionTrade() {
		ResponseEntity responseEntity = tradeController.addTrade(createTrade("T1", "CP-1", 2, LocalDate.now()));
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(), responseEntity);
		List<Trade> tradeList = tradeController.findAllTrades();
		Assertions.assertEquals(1, tradeList.size());
		Assertions.assertEquals("T1", tradeList.get(0).getTradeId());
		Assertions.assertEquals(2, tradeList.get(0).getVersion());
		Assertions.assertEquals("T1B1", tradeList.get(0).getBookId());

		// step-2 create trade with same version
		Trade trade2 = createTrade("T1", "CP-1", 2, LocalDate.now());
		trade2.setBookId("T1B1V2");
		ResponseEntity responseEntity2 = tradeController.addTrade(trade2);
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(), responseEntity2);
		List<Trade> tradeList2 = tradeController.findAllTrades();
		Assertions.assertEquals(1, tradeList2.size());
		Assertions.assertEquals("T1", tradeList2.get(0).getTradeId());
		Assertions.assertEquals(2, tradeList2.get(0).getVersion());
		Assertions.assertEquals("T1B1V2", tradeList2.get(0).getBookId());

		// step-2 create trade with new version
		Trade trade3 = createTrade("T1", "CP-1", 2, LocalDate.now());
		trade3.setBookId("T1B1V3");
		ResponseEntity responseEntity3 = tradeController.addTrade(trade3);
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(), responseEntity3);
		List<Trade> tradeList3 = tradeController.findAllTrades();
		Assertions.assertEquals(1, tradeList3.size());
		Assertions.assertEquals("T1", tradeList3.get(0).getTradeId());
		Assertions.assertEquals(2, tradeList3.get(0).getVersion());
		Assertions.assertEquals("T1B1V3", tradeList3.get(0).getBookId());

	}

	private Trade createTrade(String tradeId, String counterParty, int version, LocalDate maturityDate) {
		Trade trade = new Trade();
		trade.setTradeId(tradeId);
		trade.setBookId(tradeId + "B1");
		trade.setVersion(version);
		trade.setCounterParty(counterParty);
		trade.setMaturityDate(maturityDate);
		trade.setExpired(Boolean.TRUE);
		return trade;
	}

	public static LocalDate getLocalDate(int year, int month, int day) {
		LocalDate localDate = LocalDate.of(year, month, day);
		return localDate;
	}

}
