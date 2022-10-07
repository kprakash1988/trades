package org.abcstore.trades.controller;

import java.util.List;

import org.abcstore.trades.domain.Trade;
import org.abcstore.trades.exception.InvalidTradeException;
import org.abcstore.trades.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TradeController {

	@Autowired
	TradeService tradeService;

	@GetMapping("/trades")
	public List<Trade> findAllTrades() {
		return tradeService.findAll();
	}

	@PostMapping("/trades")
	public ResponseEntity<String> addTrade(@RequestBody Trade trade) {

		tradeService.store(trade);

		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
