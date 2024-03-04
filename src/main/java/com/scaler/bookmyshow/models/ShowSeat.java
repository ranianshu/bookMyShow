package com.scaler.bookmyshow.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ShowSeat extends BaseModel{
	
	@ManyToOne
	private Seat seat;
	
	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public ShowSeatStatus getShowSeatStatus() {
		return showSeatStatus;
	}

	public void setShowSeatStatus(ShowSeatStatus showSeatStatus) {
		this.showSeatStatus = showSeatStatus;
	}

	public Date getBlockedAt() {
		return blockedAt;
	}

	public void setBlockedAt(Date blockedAt) {
		this.blockedAt = blockedAt;
	}

	@ManyToOne
	private Show show;
	
	@Enumerated(EnumType.ORDINAL)
	private ShowSeatStatus showSeatStatus;
	
	private Date blockedAt;
}
