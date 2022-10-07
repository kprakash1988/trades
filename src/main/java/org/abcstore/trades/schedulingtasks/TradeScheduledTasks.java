
package org.abcstore.trades.schedulingtasks;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.abcstore.trades.service.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TradeScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(TradeScheduledTasks.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	TradeService tradeService;

	@Scheduled(cron = "${trade.expiry.schedule}")
	public void updateExpiryOfTrade() {
		log.info("The time is now {}", dateFormat.format(new Date()));
		tradeService.updateExpiryOfTrade();
	}
}