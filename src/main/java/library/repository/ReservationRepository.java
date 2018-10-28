package library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import library.model.*;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{

	Reservation getReservationById(Long id);
	
	List<Reservation> getReservationsByUser(User user);
	List<Reservation> getReservationsByBook(Book book);
}
