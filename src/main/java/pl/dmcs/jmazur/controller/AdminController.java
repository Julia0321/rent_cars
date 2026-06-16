package pl.dmcs.jmazur.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.dmcs.jmazur.dto.CarForAdminDto;
import pl.dmcs.jmazur.enums.CarStatusEnum;
import pl.dmcs.jmazur.enums.FuelEnum;
import pl.dmcs.jmazur.enums.TransmissionEnum;
import pl.dmcs.jmazur.service.CarService;
import pl.dmcs.jmazur.service.ReservationService;
import pl.dmcs.jmazur.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final ReservationService reservationService;
    private final UserService userService;
    private final CarService carService;

    @RequestMapping(method = RequestMethod.GET)
    public String adminHome() {

        return "admin-dashboard";
    }

    @RequestMapping(value = "/reservations", method = RequestMethod.GET)
    public String getAllReservations(Model model) {

        model.addAttribute("allReservations", reservationService.findAll());
        return "admin-reservations";
    }

    @RequestMapping(value = "/reservations/{uuid}/accept", method = RequestMethod.POST)
    public String changeReservationStatusOnAccepted(@PathVariable("uuid") String uuid, RedirectAttributes ra) {

        reservationService.acceptReservation(uuid);

        ra.addFlashAttribute("msg", "label.reservation.accepted");
        return "redirect:/admin/reservations";
    }

    @RequestMapping(value = "/reservations/{uuid}/reject", method = RequestMethod.POST)
    public String changeReservationStatusOnRejected(@PathVariable("uuid") String uuid, RedirectAttributes ra) {

        reservationService.rejectReservation(uuid);

        ra.addFlashAttribute("msg", "label.reservation.rejected");
        return "redirect:/admin/reservations";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getAllUsers(Model model) {

        model.addAttribute("users", userService.findAllUsersForAdmin());
        return "admin-users";
    }

    @RequestMapping(value = "/users/{uuid}/change", method = RequestMethod.POST)
    public String changeActivity(@PathVariable("uuid") String uuid, RedirectAttributes ra) {

        boolean b = userService.changeActivity(uuid);

        if (b) {
            ra.addFlashAttribute("msg", "label.user.activated");
        } else {
            ra.addFlashAttribute("msg", "label.user.deactivated");
        }

        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/users/{uuid}/make-admin", method = RequestMethod.POST)
    public String makeAdmin(@PathVariable("uuid") String uuid, RedirectAttributes ra) {

        userService.addAdminRole(uuid);

        ra.addFlashAttribute("msg", "label.role.admin.added");
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/users/{uuid}/remove-admin", method = RequestMethod.POST)
    public String removeAdmin(@PathVariable("uuid") String uuid, RedirectAttributes ra) {

        userService.removeAdminRole(uuid);

        ra.addFlashAttribute("msg", "label.role.admin.removed");
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/cars", method = RequestMethod.GET)
    public String getAllCars(Model model) {

        model.addAttribute("cars", carService.findAllForAdmin());

        return "admin-cars";
    }

    @RequestMapping(value = "/cars/new", method = RequestMethod.GET)
    public String addCar(Model model) {

        model.addAttribute("car", new CarForAdminDto());
        model.addAttribute("fuels", FuelEnum.values());
        model.addAttribute("transmissions", TransmissionEnum.values());
        model.addAttribute("statuses", CarStatusEnum.values());

        return "admin-car-form";
    }

    @RequestMapping(value = "/cars/new", method = RequestMethod.POST)
    public String addCarSubmit(
            @Valid @ModelAttribute("car") CarForAdminDto dto, BindingResult result,
            RedirectAttributes ra, Model model) {

        if (result.hasErrors()) {

            model.addAttribute("fuels", FuelEnum.values());
            model.addAttribute("transmissions", TransmissionEnum.values());
            model.addAttribute("statuses", CarStatusEnum.values());

            return "admin-car-form";
        }

        try {
            carService.addCar(dto);
        } catch (RuntimeException e) {
            result.rejectValue("registrationNumber", "error.registration.exists");
            return "admin-car-form";
        }

        ra.addFlashAttribute("msg", "label.car.added.success");
        return "redirect:/admin/cars";
    }

    @RequestMapping(value = "/cars/{uuid}/delete", method = RequestMethod.POST)
    public String deleteCar(@PathVariable("uuid") String uuid, RedirectAttributes ra) {

        carService.deleteCar(uuid);

        ra.addFlashAttribute("msg", "label.car.deleted");
        return "redirect:/admin/cars";
    }

    @RequestMapping(value = "/cars/{uuid}/toggle", method = RequestMethod.POST)
    public String changeAvailability(@PathVariable("uuid") String uuid, RedirectAttributes ra) {

        try {
            boolean b = carService.changeAvailability(uuid);

            if (b) {
                ra.addFlashAttribute("msg", "label.car.available");
            } else {
                ra.addFlashAttribute("error", "label.car.unavailable");
            }
        } catch (RuntimeException e) {
           ra.addFlashAttribute("msg", "error.cannot.change.status");
        }

        return "redirect:/admin/cars";
    }
}
