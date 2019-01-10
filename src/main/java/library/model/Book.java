package library.model;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "book")
@Getter
@Setter
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "book_id")
  private Long id;

  @NotEmpty(message = "{library.model.Book.title.NotEmpty}")
  private String title;

  @NotEmpty(message = "{library.model.Book.author.NotEmpty}")
  private String author;

  @NotEmpty(message = "{library.model.Book.isbn.NotEmpty}")
  @Size(min = 13, max = 13, message = "{library.model.Book.isbn.Size}")
  @Column(unique = true)
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

}
