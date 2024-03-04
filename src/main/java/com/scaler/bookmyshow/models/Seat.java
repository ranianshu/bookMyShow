package com.scaler.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Seat extends BaseModel{
	
	private String number;
	@ManyToOne
	private SeatType seatType;
	private int rowVal;
	private int colVal;
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public SeatType getSeatType() {
		return seatType;
	}
	public void setSeatType(SeatType seatType) {
		this.seatType = seatType;
	}
	public int getRowVal() {
		return rowVal;
	}
	public void setRowVal(int rowVal) {
		this.rowVal = rowVal;
	}
	public int getColVal() {
		return colVal;
	}
	public void setColVal(int colVal) {
		this.colVal = colVal;
	}

}
