package pl.dmcs.jmazur.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.dmcs.jmazur.dto.UserEditDto;
import pl.dmcs.jmazur.service.ReservationService;
import pl.dmcs.jmazur.service.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final ReservationService reservationService;
    private final UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard() {

        return "user-dashboard";
    }

    @RequestMapping(value = "/reservations", method = RequestMethod.GET)
    public String myReservations(Model model, Principal principal) {

        model.addAttribute("reservations",
                reservationService.findReservationsByUser(principal.getName()));

        return "my-reservations";
    }

    @RequestMapping(value = "/reservation/cancel/{uuid}", method = RequestMethod.POST)
    public String cancelReservation(@PathVariable("uuid") String uuid, RedirectAttributes ra) {

        try {
            reservationService.cancelReservation(uuid);
            ra.addFlashAttribute("msg", "label.reservation.cancelled");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "error.cannot.cancel.reservation");
        }
        return "redirect:/user/reservations";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Model model, Principal principal) {

        model.addAttribute("editUser", userService.findUserByEmailToEdit(principal.getName()));

        return "user-profile";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute("editUser") UserEditDto user,
                       BindingResult result,
                       Principal principal,
                       RedirectAttributes ra) {

        if (result.hasErrors()) {
            return "user-profile";
        }

        userService.edit(user, principal.getName());
        ra.addFlashAttribute("msg", "label.profile.updated");
        return "redirect:/user/edit";
    }
}
