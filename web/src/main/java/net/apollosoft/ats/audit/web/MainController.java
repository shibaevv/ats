package net.apollosoft.ats.audit.web;

import net.apollosoft.ats.audit.service.SecurityService;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.domain.security.IFunction;
import net.apollosoft.ats.util.ThreadLocalUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Controller
public class MainController
{

    private final SecurityService securityService;

    @Autowired
    public MainController(@Qualifier("securityService") SecurityService securityService)
    {
        this.securityService = securityService;
    }

    @RequestMapping(value="/main.do", params = "!dispatch", method = RequestMethod.GET)
    public String main(ModelMap model) throws ServiceException
    {
        return "main";
    }

    @RequestMapping(value = "/main.do", params = "dispatch=find")
    public String menuFind(ModelMap model) throws Exception {
        //set home page - per role
        IFunction home = securityService.getUserHome();
        model.addAttribute("home", home == null ? null : home.getName());
        model.addAttribute("homeUrl", home == null ? null : home.getQuery());
        model.addAttribute("username", ThreadLocalUtils.getUser().getName());
        return "menu/find";
    }

}