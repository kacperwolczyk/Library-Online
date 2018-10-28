package library.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import library.model.*;

@Repository
public interface HireRepository extends JpaRepository<Hire, Long>{

	Hire getHireById(Long id);
	List<Hire> getHireByBook(Book book);
	
	@Query("SELECT h FROM Hire h WHERE h.user=:user AND h.status!='unactive'")
	List<Hire> getHireByUser(@Param("user") User user);
	
	@Query("SELECT h FROM Hire h WHERE h.user=:user AND h.status='unactive'")
	List<Hire> getHireHistory(@Param("user") User user);
	
}
