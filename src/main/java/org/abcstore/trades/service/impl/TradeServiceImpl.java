package org.abcstore.trades.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.abcstore.trades.domain.Trade;
import org.abcstore.trades.exception.InvalidTradeException;
import org.abcstore.trades.repository.TradeRepository;
import org.abcstore.trades.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeServiceImpl implements TradeService {

	@Autowired
	TradeRepository tradeRepository;

	@Override
	public List<Trade> findAll() {
		return tradeRepository.findAll();
	}

	@Override
	public boolean isValid(Trade trade) {

		if (validMaturityDate(trade)) {
			Optional<Trade> exsitingTrade = tradeRepository.findById(trade.getTradeId());
			if (exsitingTrade.isPresent()) {
				return validateVersion(trade, exsitingTrade.get());
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean validateVersion(Trade trade, Trade oldTrade) {
		return (trade.getVersion() >= oldTrade.getVersion());

	}

	@Override
	public void store(Trade trade) {

		if (!validMaturityDate(trade)) {
			throw new InvalidTradeException(trade.getTradeId() + " Invalid Maturatiy date");
		}

		Optional<Trade> exsitingTrade = tradeRepository.findById(trade.getTradeId());

		if (exsitingTrade.isPresent() && !validateVersion(trade, exsitingTrade.get())) {
			throw new InvalidTradeException(trade.getTradeId() + " lower version trade present");
		}

		trade.setCreatedDate(LocalDate.now());
		tradeRepository.save(trade);

	}

	private boolean validMaturityDate(Trade trade) {
		return trade.getMaturityDate().isBefore(LocalDate.now()) ? Boolean.FALSE : Boolean.TRUE;
	}

	@Override
	public void updateExpiryOfTrade() {
		tradeRepository.findAll().stream().forEach(t -> {
			if (!validMaturityDate(t)) {
				t.setExpired(Boolean.FALSE);
				tradeRepository.save(t);
			}
		});
	}

}
