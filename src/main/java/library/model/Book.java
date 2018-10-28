package library.model;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name="book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="book_id")
	private Long id;
	
	@NotEmpty(message = "{library.model.Book.title.NotEmpty}")
	private String title;
	
	@NotEmpty(message = "{library.model.Book.author.NotEmpty}")
	private String author;
	
	@NotEmpty(message = "{library.model.Book.isbn.NotEmpty}")
	@Size(min=13, max=13, message = "{library.model.Book.isbn.Size}")
	@Column(unique=true)
	private String isbn;
	
	@NotEmpty(message = "{library.model.Book.type.NotEmpty}")
	private String type;
	
	@NotNull(message = "{library.model.Book.count.NotNull}")
	private int count;
	
	private int freeCount;
	
	@OneToMany(mappedBy = "book")
	private List<Reservation> reservations;
	
	@OneToMany(mappedBy = "book")
	private List<Hire> hires;
	
	public Book() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getFreeCount() {
		return freeCount;
	}

	public void setFreeCount(int freeCount) {
		this.freeCount = freeCount;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public List<Hire> getHires() {
		return hires;
	}

	public void setHires(List<Hire> hires) {
		this.hires = hires;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", isbn=" + isbn + ", type=" + type
				+ ", count=" + count + ", freeCount=" + freeCount + ", getId()=" + getId() + ", getTitle()="
				+ getTitle() + ", getAuthor()=" + getAuthor() + ", getIsbn()=" + getIsbn() + ", getType()=" + getType()
				+ ", getCount()=" + getCount() + ", getFreeCount()=" + getFreeCount() + ", getReservations()="
				+ getReservations() + ", getHires()=" + getHires() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	
}
