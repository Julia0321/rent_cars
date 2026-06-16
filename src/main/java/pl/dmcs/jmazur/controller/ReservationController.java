package pl.dmcs.jmazur.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.dmcs.jmazur.domain.Reservation;
import pl.dmcs.jmazur.dto.CarDto;
import pl.dmcs.jmazur.dto.ReservationPayDto;
import pl.dmcs.jmazur.dto.ReservationSearchDto;
import pl.dmcs.jmazur.service.CarService;
import pl.dmcs.jmazur.service.ReservationService;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rent")
public class ReservationController {

    private final ReservationService reservationService;
    private final CarService carService;

    @RequestMapping(value = "book/car", method = RequestMethod.GET)
    public String rentCar(Model model) {

        model.addAttribute("search", new ReservationSearchDto());
        return "reservation";
    }

    @RequestMapping(value = "book/car", method = RequestMethod.POST)
    public String rentCar(@Valid @ModelAttribute("search") ReservationSearchDto search,
                          BindingResult result,
                          Model model) {

        if (result.hasErrors()) {
            return "reservation";
        }

        model.addAttribute("cars", carService.findAvailable(search.getFrom(), search.getTo()));
        return "reservation";
    }

    @RequestMapping(value = "/car", method = RequestMethod.GET)
    public String carDetails(@RequestParam("carUUID") String carUUID,
                             @RequestParam("from") LocalDate from,
                             @RequestParam("to") LocalDate to,
                             Model model) {

        addAttributes(carUUID, from, to, model);
        return "details";
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public String checkout(@RequestParam("carUUID") String carUUID,
                           @RequestParam("from") LocalDate from,
                           @RequestParam("to") LocalDate to,
                           Model model,
                           Principal principal) {

        addAttributes(carUUID, from, to, model);

        Reservation reservation =
                reservationService.createPendingReservation(carUUID, from, to, principal.getName());

        model.addAttribute("pay", new ReservationPayDto(reservation.getUuid()));

        return "checkout";
    }

    private void addAttributes(String carUUID, LocalDate from, LocalDate to, Model model) {

        CarDto car = carService.getCarDtoByUUID(carUUID);

        long days = java.time.Duration.between(from.atStartOfDay(), to.atStartOfDay()).toDays();

        BigDecimal total = car.getPricePerDay()
                .multiply(BigDecimal.valueOf(days))
                .setScale(2);

        model.addAttribute("car", car);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("days", days);
        model.addAttribute("total", total);
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public String pay(@ModelAttribute("pay") ReservationPayDto dto,
                      RedirectAttributes ra) {

        try {
            reservationService.markAsPaid(dto.getReservationUUID());
            ra.addFlashAttribute("msg", "label.payment.accepted");
            return "pay-confirmation";
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", "error.reservation.expired");
            return "redirect:/rent/book/car";
        }
    }
}
