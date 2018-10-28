package library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import library.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

	List<Book> getBookByTitle(String title);
	List<Book> getBookByAuthor(String author);
	List<Book> getBookByType (String type);
	Book getBookById(Long id);
}
