package library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import library.model.*;
import library.service.*;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HireController {

  private ReservationService reservationService;
  private HireService hireService;

  @PostMapping("/addhire")
  public String addHire(@RequestParam(name = "id", required = false) Long ReservationId,
      RedirectAttributes redirectAttributes) {
    Reservation reservation = reservationService.getReservation(ReservationId);
    Long userId = reservation.getUser().getId();
    Hire hire = new Hire(reservation.getUser(), reservation.getBook());
    if (hireService.addHire(hire, reservation))
      redirectAttributes.addFlashAttribute("correctMessage", "You lent book successfully!");
    else
      redirectAttributes.addFlashAttribute("invalidMessage", "You can't lend books for unactive users!");
    return "redirect:adminpanel/searchuser/" + userId;
  }

  @PostMapping("/deletehire")
  public String deleteHire(@RequestParam(name = "id") Long HireId, RedirectAttributes redirectAttributes) {
    Hire hire = hireService.getById(HireId);
    Long userId = hire.getUser().getId();
    String status = hire.getStatus();
    if (status.equals("active") || status.equals("extended") || status.equals("expired"))
      hireService.unactiveHire(hire);
    redirectAttributes.addFlashAttribute("correctMessage", "Book is returned correctly");
    return "redirect:adminpanel/searchuser/" + userId;
  }

  @PostMapping("/updatehire")
  public String createReservation(RedirectAttributes redirectAttributes, @RequestParam(name = "id") Long hireId) {
    Hire hire = hireService.getById(hireId);
    if (hireService.changeExpDate(hire))
      redirectAttributes.addFlashAttribute("correctMessage", "You extend your expire term for two weeks!");
    else if ("unactive".equals(hire.getUser().getStatus()))
      redirectAttributes.addFlashAttribute("invalidMessage",
          "You can't extend your expire term because your accout is unactive!");
    else
      redirectAttributes.addFlashAttribute("invalidMessage", "Expire term can be extend only one time!");
    return "redirect:userpanel";
  }
}
