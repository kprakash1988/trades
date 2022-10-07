package org.abcstore.trades.service;

import java.util.List;

import org.abcstore.trades.domain.Trade;

public interface TradeService {

	List<Trade> findAll();

	boolean isValid(Trade trade);

	void store(Trade trade);

	void updateExpiryOfTrade();

}
