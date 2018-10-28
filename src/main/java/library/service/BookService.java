package library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import library.model.Book;
import library.repository.BookRepository;

@Service
public class BookService {

	private BookRepository bookRepository;
	
	@Autowired
	public void setBookService(BookRepository bookRepository)
	{
		this.bookRepository = bookRepository;
	}
	
	public Book addBook(Book book)
	{
		book.setFreeCount(book.getCount());
		return bookRepository.save(book);
	}
	
	public void addOneBook(Book book)
	{
		int freeCount = book.getFreeCount();
		book.setFreeCount(freeCount+1);
		bookRepository.save(book);
	}
	
	public void substractOneBook(Book book)
	{
		int freeCount = book.getFreeCount();
		book.setFreeCount(freeCount-1);
		bookRepository.save(book);
	}
	
	/*GETTERS*/
	
	public List<Book> getBookByTitle(String title)
	{
		return bookRepository.getBookByTitle(title);
	}
	
	public List<Book> getBookByAuthor(String author)
	{
		return bookRepository.getBookByAuthor(author);
	}
	
	public List<Book> getBookByType(String type)
	{
		return bookRepository.getBookByType(type);
	}
	
	public Book getBookById(Long id)
	{
		return bookRepository.getBookById(id);
	}
	
	public List<Book> getBookList()
	{
		return bookRepository.findAll();
	}
}
