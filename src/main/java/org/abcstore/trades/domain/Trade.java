package org.abcstore.trades.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "trade")
public class Trade {

	@Id

	private String tradeId;

	private int version;

	private String counterParty;

	private String bookId;

	private LocalDate maturityDate;

	private LocalDate createdDate;

	private Boolean expired;

	/**
	 * @return the tradeId
	 */
	public String getTradeId() {
		return tradeId;
	}

	/**
	 * @param tradeId the tradeId to set
	 */
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @return the counterParty
	 */
	public String getCounterParty() {
		return counterParty;
	}

	/**
	 * @param counterParty the counterParty to set
	 */
	public void setCounterParty(String counterParty) {
		this.counterParty = counterParty;
	}

	/**
	 * @return the bookId
	 */
	public String getBookId() {
		return bookId;
	}

	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	/**
	 * @return the maturityDate
	 */
	public LocalDate getMaturityDate() {
		return maturityDate;
	}

	/**
	 * @param maturityDate the maturityDate to set
	 */
	public void setMaturityDate(LocalDate maturityDate) {
		this.maturityDate = maturityDate;
	}

	/**
	 * @return the createdDate
	 */
	public LocalDate getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the expiredFlag
	 */
	public Boolean isExpired() {
		if (expired == null) {
			return Boolean.FALSE;
		}
		return expired;
	}

	/**
	 * @param expiredFlag the expiredFlag to set
	 */
	public void setExpired(Boolean expired) {
		this.expired = expired;
	}

}
