package pl.dmcs.jmazur.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.dmcs.jmazur.dto.UserDto;
import pl.dmcs.jmazur.service.ReCaptchaService;
import pl.dmcs.jmazur.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class RegistrationController {

    private final UserService userService;
    private final ReCaptchaService reCaptchaService;

    @Value("${recaptcha.site-key}")
    private String recaptchaSiteKey;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {

        model.addAttribute("user", new UserDto());
        model.addAttribute("siteKey", recaptchaSiteKey);
        return "register";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("user") UserDto user, BindingResult result,
                       Model model, HttpServletRequest request) {

        model.addAttribute("siteKey", recaptchaSiteKey);

        if (result.hasErrors()) {
            return "register";
        }

        boolean isCaptchaOk = reCaptchaService.verify(request.getParameter("g-recaptcha-response"));

        if (!isCaptchaOk) {
            model.addAttribute("captchaError", "error.reCaptcha");
            return "register";
        }

        try {
            userService.saveUser(user);
        } catch (RuntimeException e) {
            model.addAttribute("error", "error.email.taken");
            return "register";
        }

        return "redirect:/login";
    }

    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public String activate(@RequestParam("token") String token, Model model) {

        try {
            userService.checkToken(token);
            model.addAttribute("msg", "label.activated");
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "activation-error";
        }
        return "hello";
    }
}
