package br.com.liferay.daniel.pointrecord.user;

import br.com.liferay.daniel.pointrecord.domain.UserWorkDTO;
import br.com.liferay.daniel.pointrecord.user.repository.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{pis}/works/{periodIni}/{periodFin}")
    private UserWorkDTO calculateWork (@PathVariable String pis,
                                       @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate periodIni ,
                                       @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate periodFin){
        return userService.calculateWorkUser(pis,periodIni,periodFin);
    }



}
