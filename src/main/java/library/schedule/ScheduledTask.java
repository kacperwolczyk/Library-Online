package library.schedule;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import library.model.*;
import library.service.*;

@Component
public class ScheduledTask {
	
	private ReservationService reservationService;
	private HireService hireService;
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setReservationService(ReservationService reservationService) {
		this.reservationService = reservationService;
	}
	
	@Autowired
	public void setHireService(HireService hireService) {
		this.hireService = hireService;
	}



	@Scheduled(cron="0 0 0 * * *")
	public void reservationExpired()
	{
		LocalDate currentDate = LocalDate.now();
		List<Reservation> reservations = reservationService.getAll();
		for(Reservation reservation : reservations)
		{	
			LocalDate endDate = reservation.getEndDate();
			if(endDate.isBefore(currentDate))
				reservationService.deleteReservation(reservation);
		}
	}
	
	@Scheduled(cron="0 0 0 * * *")
	public void hireExpired()
	{
		LocalDate currentDate = LocalDate.now();
		List<Hire> hires = hireService.getAll();
		for(Hire hire: hires)
		{	
			LocalDate expDate = hire.getExpDate();
			if(expDate.isBefore(currentDate))
				hireService.changeHireStatusToExpired(hire);
		}
	}
	
	@Scheduled(cron="0 0 0 * * *")
	public void countCharge()
	{
		List<Hire> hires = hireService.getAll();	
		for(Hire hire: hires)
		{	
			hireService.countHireCharge(hire);
		}
	}
	
	@Scheduled(cron="0 0 0 * * *")
	public void userCountCharge()
	{
		List<User> users = userService.getAll();	
		for(User user: users)
		{	
			userService.sumUserHireCharge(user);
		}
	}
}

