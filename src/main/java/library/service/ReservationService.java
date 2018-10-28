package library.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import library.model.*;
import library.repository.*;

@Service
public class ReservationService {

	private ReservationRepository reservationRepository;
	private BookService bookService;
	
	@Autowired
	public void setReservationRepository(ReservationRepository reservationRepository)
	{
		this.reservationRepository = reservationRepository;
	}
	
	@Autowired
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	public boolean addReservation(Reservation reservation)
	{
		boolean result = false;
		
		Hire hire = new Hire(reservation.getUser(), reservation.getBook());
		hire.setStatus("extended");
		Hire hire2 = new Hire(reservation.getUser(), reservation.getBook());
		hire2.setStatus("active");
		User user = reservation.getUser();
		
		if("active".equals(user.getStatus())&&
				!user.getReservations().contains(reservation) && 
				!user.getHires().contains(hire) && 
				!user.getHires().contains(hire2))	
		{
			LocalDate currentDate = LocalDate.now();
			LocalDate dateAfterSevenDays = currentDate.plusDays(7);
			reservation.setAddDate(currentDate);
			reservation.setEndDate(dateAfterSevenDays);
			reservationRepository.save(reservation);
			Book reservedBook = reservation.getBook();
			bookService.substractOneBook(reservedBook);
			result = true;
		}
		return result;
	}
	
	public void deleteReservation(Reservation reservation)
	{
		reservationRepository.delete(reservation);
		bookService.addOneBook(reservation.getBook());
	}
	
	public void changeReservationToHire(Reservation reservation)
	{
		reservationRepository.delete(reservation);
	}
	
	/*GETTERS*/
	
	public Reservation getReservation(Long id)
	{
		return reservationRepository.getReservationById(id);
	}
	
	public List<Reservation> getByUser(User user)
	{
		return reservationRepository.getReservationsByUser(user);
	}
	
	public List<Reservation> getByBook(Book book)
	{
		return reservationRepository.getReservationsByBook(book);
	}
	
	public List<Reservation> getAll()
	{
		return reservationRepository.findAll();
	}
	
}
