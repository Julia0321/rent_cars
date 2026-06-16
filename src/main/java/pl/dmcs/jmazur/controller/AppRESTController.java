package pl.dmcs.jmazur.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.jmazur.dto.UserDto;
import pl.dmcs.jmazur.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest")
public class AppRESTController {

    private final UserService userService;

    @RequestMapping(value = "/{login}", method = RequestMethod.GET, produces = "application/json")
    public UserDto getUserInJson(@PathVariable("login") String login) {

        return userService.findUserByEmail(login);
    }

    @RequestMapping(value = "/{login}.xml", method = RequestMethod.GET, produces = "application/xml")
    public UserDto getUserInXml(@PathVariable("login") String login) {

        return userService.findUserByEmail(login);
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers() {

        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }
}
