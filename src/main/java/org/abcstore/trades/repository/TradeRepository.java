package org.abcstore.trades.repository;

import org.abcstore.trades.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TradeRepository extends JpaRepository<Trade, String> {

}
