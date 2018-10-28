package library.controller;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import library.model.Book;
import library.service.BookService;

@Controller
public class BookController {

	private BookService bookService;
	
	@Autowired
	public void setBookService(BookService bookService)
	{
		this.bookService = bookService;
	}
	
	@GetMapping("/booklist")
	public String getAllBooks(@ModelAttribute("parameter") String parameter, 
			@ModelAttribute("value") String value, Model model)
	{
		if("bookstitle".equals(parameter))
			model.addAttribute("bookList", bookService.getBookByTitle(value));
		
		else if("booksauthor".equals(parameter))
			model.addAttribute("bookList", bookService.getBookByAuthor(value));
		
		else if("bookstype".equals(parameter))
			model.addAttribute("bookList", bookService.getBookByType(value));
		
		else
			model.addAttribute("bookList", bookService.getBookList());
		return "bookList";
	}
	
	@PostMapping("/booklist")
	public String searchBooks(@RequestParam(name="argument", required=false) String parameter,
			@RequestParam(name="argumentValue", required=false) String value, 
			RedirectAttributes redirectAttributes)
	{
		redirectAttributes.addFlashAttribute("parameter", parameter);
		redirectAttributes.addFlashAttribute("value", value);
		//return "redirect:booklist";
		return "redirect:booklist?"+parameter+"="+value;
	}
	
	
	@PostMapping("/addbook")
	public String addBook(@ModelAttribute @Valid Book book, 
			BindingResult bindResult,
			RedirectAttributes redirectAttributes)
	{
		if(bindResult.hasErrors())
			return "bookForm";
		else {
			try {
				bookService.addBook(book);
			} catch (DataIntegrityViolationException e) {
				redirectAttributes.addFlashAttribute("invalidMessage", "This ISBN is already used. Try again");
				return "redirect:adminpanel/bookform";
			}
			redirectAttributes.addFlashAttribute("correctMessage", "You added book successcully");
			return "redirect:adminpanel/bookform";
		}
	}
}
