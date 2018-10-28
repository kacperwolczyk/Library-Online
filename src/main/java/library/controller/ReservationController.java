package library.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import library.model.*;
import library.service.*;

@Controller
public class ReservationController {

	private ReservationService reservationService;
	private UserService userService;
	private BookService bookService;
	
	@Autowired
	public void setReservationService(ReservationService reservationService)
	{
		this.reservationService = reservationService;
	}
	
	@Autowired
	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}
	
	@Autowired
	public void setBookService(BookService bookService)
	{
		this.bookService = bookService;
	}
	
	@PostMapping("/addreservation")
	public String createReservation(RedirectAttributes redirectAttributes, Principal principal, @RequestParam(name="id") Long bookId)
	{
		Book chosenBook = bookService.getBookById(bookId);
		User user = userService.findUserByEmail(principal.getName());
		
		if(chosenBook.getFreeCount()>0)
		{
			Reservation reservation = new Reservation(user, chosenBook);
			if(reservationService.addReservation(reservation))
				redirectAttributes.addFlashAttribute("correctMessage", "You reserved book successfully!");				
			else if("active".equals(user.getStatus()))
				redirectAttributes.addFlashAttribute("invalidMessage", "You already reserved or lent this book. Check your profile");
			else if("unactive".equals(user.getStatus()))
				redirectAttributes.addFlashAttribute("invalidMessage", "Your account is unactive! Check your profile");
		}
		
		else if(chosenBook.getFreeCount()==0)
			redirectAttributes.addFlashAttribute("invalidMessage", "There is no more avaible books!");
		
		return "redirect:booklist";
	}
	
	@PostMapping("/deletereservation")
	public String deleteReservation(RedirectAttributes redirectAttributes, @RequestParam(name="id") Long ReservationId)
	{
		Reservation reservation = reservationService.getReservation(ReservationId);
		reservationService.deleteReservation(reservation);
		redirectAttributes.addFlashAttribute("correctMessage", "Reservation is canceled!");
		return "redirect:userpanel";
	}
}
