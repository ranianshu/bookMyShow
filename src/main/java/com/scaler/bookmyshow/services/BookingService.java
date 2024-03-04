package com.scaler.bookmyshow.services;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.scaler.bookmyshow.models.*;
import com.scaler.bookmyshow.repositories.BookingRepository;
import com.scaler.bookmyshow.repositories.ShowRepository;
import com.scaler.bookmyshow.repositories.ShowSeatRepository;
import com.scaler.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

	private BookingRepository bookingRepository;
	private ShowRepository showRepository;
	private ShowSeatRepository showSeatRepository;
	private UserRepository userRepository;

	private PriceCalculator priceCalculator;

	@Autowired
	public BookingService(BookingRepository bookingRepository, ShowRepository showRepository, ShowSeatRepository showSeatRepository, UserRepository userRepository, PriceCalculator priceCalculator) {
		this.bookingRepository = bookingRepository;
		this.showRepository = showRepository;
		this.showSeatRepository = showSeatRepository;
		this.userRepository = userRepository;
		this.priceCalculator = priceCalculator;
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Booking bookMovie(Long userId, List<Long> showSeatIds, Long showId) {
		//-----TODAY WE START TRANSACTION FROM HERE----

		//1. Get User from userId
		Optional<User> userOptional = userRepository.findById(userId);
		if(userOptional.isEmpty()){
			throw new RuntimeException();
		}
		User bookedBy = userOptional.get();

		//2. Get show from showId
		Optional<Show> showOptional = showRepository.findById(showId);
		if(showOptional.isEmpty()){
			throw new RuntimeException();
		}
		Show bookedFor = showOptional.get();

		//------START TRANSACTION------
		//3. Get ShowSeat from showSeatIds
		List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);

		//4. check if all the show seats are available
		//5. if no throw error
		for(ShowSeat showSeat: showSeats){
			if(!(showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE) ||
					(showSeat.getShowSeatStatus().equals(ShowSeatStatus.BOOKED))&&
							Duration.between(showSeat.getBlockedAt().toInstant(), new Date().toInstant()).toMinutes() > 15)){
				throw new RuntimeException();
			}
		}

		//6. if yes, mark seats as locked
		//7. save updated showSeats to db
		List<ShowSeat> savedShowSeats = new ArrayList<>();
		for(ShowSeat showSeat: showSeats){
			showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
			showSeat.setBlockedAt(new Date());
			showSeatRepository.save(showSeat);
			savedShowSeats.add(showSeat);
		}

		//-------END TRANSACTION-----
		//8. create corresponding Booking object
		Booking booking = new Booking();
		booking.setBookingStatus(BookingStatus.PENDING);
		booking.setShowSeats(savedShowSeats);
		booking.setUser(bookedBy);
		booking.setBookedAt(new Date());
		booking.setShow(bookedFor);
		booking.setAmount(priceCalculator.calculatePrice(savedShowSeats, bookedFor));

		Booking savedBooking = bookingRepository.save(booking);

		//-----TODAY WE WILL END TRANSACTION HERE-----
		return savedBooking;
	}

}
