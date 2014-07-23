package net.apollosoft.ats.audit.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Controller
public class LoginController
{

    @RequestMapping(value="/login.do")
    public String login()
    {
        return "login";
    }

}