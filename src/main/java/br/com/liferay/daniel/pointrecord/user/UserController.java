package br.com.liferay.daniel.pointrecord.user;

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


}
