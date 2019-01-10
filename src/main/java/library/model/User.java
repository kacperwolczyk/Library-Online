package library.model;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @NotEmpty(message = "{library.model.User.firstName.NotEmpty}")
  private String firstName;

  @NotEmpty(message = "{library.model.User.lastName.NotEmpty}")
  private String lastName;

  @NotEmpty(message = "{library.model.User.email.NotEmpty}")
  @Email(message = "{library.model.User.email.Email}")
  @Column(unique = true)
  private String email; /* email is also a userName */

  @NotEmpty(message = "{library.model.User.password.NotEmpty}")
  private String password;

  @NotEmpty(message = "{library.model.User.pesel.NotEmpty}")
  @Size(min = 11, max = 11, message = "{library.model.User.pesel.Size}")
  @Column(unique = true)
  private String pesel;

  private String status;

  @OneToMany(mappedBy = "user")
  private List<Reservation> reservations;

  @OneToMany(mappedBy = "user")
  private List<Hire> hires;

  @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  Set<UserRole> roles = new HashSet<>();
  private int hireCharge;

}
