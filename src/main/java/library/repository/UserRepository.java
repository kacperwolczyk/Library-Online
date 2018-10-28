package library.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import library.model.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findUserByEmail(String email);
	User getUserById(Long id);
}
