package br.com.liferay.daniel.pointrecord.user;

import br.com.liferay.daniel.pointrecord.domain.UnknownError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @RequestMapping(value = "/authenticated", method = RequestMethod.GET)
    public String authenticated (){
        return "Authenticated";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error (){
        UnknownError unknownError = null;
        unknownError.setCause("error null");

        return "Authenticated";
    }

}
