package library.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.*;

import library.model.*;
import library.repository.*;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

  private static final String defaultRole = "ROLE_USER";
  private PasswordEncoder passwordEncoder;
  private UserRepository userRepository;
  private UserRoleRepository roleRepository;
  private HireService hireService;

  public void addUserWithDefaultRole(User user) {
    UserRole role = roleRepository.findByRoleName(defaultRole);
    user.getRoles().add(role);
    String passwordHash = passwordEncoder.encode(user.getPassword());
    user.setPassword(passwordHash);
    user.setStatus("active");
    userRepository.save(user);
  }

  public void sumUserHireCharge(User user) {
    List<Hire> hires = hireService.getByUser(user);
    int charge = 0;
    for (Hire hire : hires) {
      if ("expired".equals(hire.getStatus())) {
        int hireCharge = hire.getCharge();
        charge = charge + hireCharge;
      }
    }
    user.setHireCharge(charge);
    userRepository.save(user);
  }

  public boolean enableUserAccount(User user) {
    boolean result = false;
    if ("unactive".equals(user.getStatus()) && user.getHireCharge() == 0) {
      user.setStatus("active");
      userRepository.save(user);
      result = true;
    }

    if ("active".equals(user.getStatus()))
      result = true;
    return result;
  }

  public boolean disableUserAccount(User user) {
    boolean result = false;
    if ("active".equals(user.getStatus())) {
      user.setStatus("unactive");
      userRepository.save(user);
      result = true;
    }
    return result;
  }

  public void updateUser(User user) {
    userRepository.save(user);
  }

  /* GETTERS */

  public User findUserByEmail(String email) {
    return userRepository.findUserByEmail(email);
  }

  public User findUserById(Long id) {
    return userRepository.getUserById(id);
  }

  public List<User> getAll() {
    return userRepository.findAll();
  }

}
