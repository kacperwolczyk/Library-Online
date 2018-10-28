package library.service;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import library.model.*;
import library.repository.HireRepository;

@Service
public class HireService {

	private HireRepository hireRepository;
	private ReservationService reservationService;
	private BookService bookService;
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setHireService(HireRepository hireRepository)
	{
		this.hireRepository = hireRepository;
	}
	
	@Autowired
	public void setReservationService(ReservationService reservationService)
	{
		this.reservationService = reservationService;
	}
	
	@Autowired
	public void setBookService(BookService bookService)
	{
		this.bookService = bookService;
	}
	
	public boolean addHire(Hire hire, Reservation reservation)
	{
		boolean result = false;
		if("active".equals(hire.getUser().getStatus()))
		{
			result = true;
			LocalDate currentDate = LocalDate.now();
			LocalDate dateAfterMonth = currentDate.plusDays(30);
			hire.setAddDate(currentDate);
			hire.setExpDate(dateAfterMonth);
			hire.setStatus("active");
			hireRepository.save(hire);
			reservationService.changeReservationToHire(reservation);
		}
		return result;
	}	
	
	public boolean changeExpDate(Hire hire)
	{
		boolean result = false;
		if("active".equals(hire.getStatus()) && "active".equals(hire.getUser().getStatus()))
		{
			LocalDate expDate = hire.getExpDate();
			LocalDate newExpDate = expDate.plusDays(14);
			hire.setExpDate(newExpDate);
			hire.setStatus("extended");
			hireRepository.save(hire);
			result = true;
		}
		return result;
	}
	
	public void changeHireStatusToExpired(Hire hire)
	{
		if("active".equals(hire.getStatus()) || "extended".equals(hire.getStatus()))
		{
			hire.setStatus("expired");
			User user = hire.getUser();
			user.setStatus("unactive");
			userService.updateUser(user);
			hireRepository.save(hire);
		}
	}
	
	public void countHireCharge(Hire hire)
	{
		if("expired".equals(hire.getStatus()))
		{
			LocalDate addDate = hire.getAddDate();
			LocalDate expDate = hire.getExpDate();
			int charge = expDate.getDayOfYear() - addDate.getDayOfYear();
			hire.setCharge(charge);
			hireRepository.save(hire);
		}
	}
		
	public void unactiveHire(Hire hire)
	{
		LocalDate currentDate = LocalDate.now();
		hire.setEndDate(currentDate);
		hire.setStatus("unactive");
		
		User user = hire.getUser();
		user.setHireCharge(user.getHireCharge() - hire.getCharge());
		hireRepository.save(hire);
		userService.updateUser(user);
		bookService.addOneBook(hire.getBook());
	}
	
	/*GETTERS*/
	
	public Hire getById(Long id)
	{
		return hireRepository.getHireById(id);
	}
	
	public List<Hire> getByUser(User user)
	{
		return hireRepository.getHireByUser(user);
	}
	
	public List<Hire> getByBook(Book book)
	{
		return hireRepository.getHireByBook(book);
	}
	
	public List<Hire> getHireHistory(User user)
	{
		return hireRepository.getHireHistory(user);
	}
	
	public List<Hire> getAll()
	{
		return hireRepository.findAll();
	}
}
