package library.controller;

import java.security.Principal;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import library.model.*;
import library.service.*;

@Controller
public class UserController {
	
	private UserService userService;
	private ReservationService reservationService;
	private HireService hireService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setReservationService(ReservationService reservationService) {
		this.reservationService = reservationService;
	}
	
	@Autowired
	public void setHireService(HireService hireService) {
		this.hireService = hireService;
	}

	@GetMapping("/register")
	public String register(Model model, Principal principal) {
		if(principal!=null)
			return "redirect:userpanel";
		model.addAttribute("user", new User());
		return "registerForm";
	}

	@PostMapping("/register")
	public String addUser(@ModelAttribute @Valid User user,
			BindingResult bindResult, 
			RedirectAttributes redirectAttributes) {
		if(bindResult.hasErrors())
			return "registerForm";
		else {
			try {
				userService.addUserWithDefaultRole(user);
			} catch (DataIntegrityViolationException e) {
				redirectAttributes.addFlashAttribute("invalidMessage", "This email is already used. Try again");
				return "redirect:/register";
			}
			redirectAttributes.addFlashAttribute("correctMessage", "You have been successfully registered!");
			return "redirect:/register";
		}
		
	}	
	
	@GetMapping("/userpanel")
	public String userPanel(Principal principal, Model model)
	{
		User user = userService.findUserByEmail(principal.getName());
		model.addAttribute("myReservationList", reservationService.getByUser(user));
		model.addAttribute("myHireList", hireService.getByUser(user));
		model.addAttribute("myHistoryList", hireService.getHireHistory(user));
		model.addAttribute("user", user);
		return "userPanel";
	}
	
	@GetMapping("/adminpanel")
	public String getAdminPanel(Principal principal, Model model)
	{
		User admin = userService.findUserByEmail(principal.getName());
		model.addAttribute("admin", admin);
		return "adminPanel";
	}
	
	@GetMapping("/adminpanel/bookform")
	public String addBook(Model model)
	{
		model.addAttribute("book", new Book());
		return "bookForm";
	}
	
	@GetMapping("/adminpanel/searchuser")
	public String searchUser()
	{
		return "userInformation";
	}
	
	@PostMapping("/searchuser")
	public String adminPanel(@RequestParam(name="userEmail", required=false) String userEmail, RedirectAttributes redirectAttributes)
	{
		User user = userService.findUserByEmail(userEmail);
		redirectAttributes.addFlashAttribute("user", user);
		redirectAttributes.addFlashAttribute("userReservationList", reservationService.getByUser(user));
		redirectAttributes.addFlashAttribute("userHireList", hireService.getByUser(user));
		redirectAttributes.addFlashAttribute("userHistory", hireService.getHireHistory(user));
		
		Long id = user.getId();
		return "redirect:adminpanel/searchuser?user_id="+id;
	}
	
	@PostMapping("/enableaccount")
	public String enableAccount(@RequestParam(name="id") Long id, RedirectAttributes redirectAttributes)
	{
		User user = userService.findUserById(id);
		if(userService.enableUserAccount(user))
			redirectAttributes.addFlashAttribute("correctMessage", "Account is active now!");
		else
			redirectAttributes.addFlashAttribute("invalidMessage", "Account can't be active because user still has debt");
		return "redirect:/adminpanel/searchuser";
	}
	
	@PostMapping("/disableaccount")
	public String disableAccount(@RequestParam(name="id") Long id, RedirectAttributes redirectAttributes)
	{
		User user = userService.findUserById(id);
		if(userService.disableUserAccount(user))
			redirectAttributes.addFlashAttribute("correctMessage", "Account is unactive now!");
		else
			redirectAttributes.addFlashAttribute("invalidMessage", "Account is unactive already!");
		return "redirect:/adminpanel/searchuser";
	}
	
}