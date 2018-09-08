package com.svichkarev.anatoly.controller;

import com.svichkarev.anatoly.Hello;
import com.svichkarev.anatoly.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

import static com.svichkarev.anatoly.Order.Currency.USD;

@Controller
public class HelloController {

    private final InitialContext context;

    public HelloController() throws NamingException {
        final Properties jndiProperties = new Properties();
        jndiProperties.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

        context = new InitialContext(jndiProperties);
    }

    @RequestMapping
    public String index(Model model) {
        final Order defaultOrder = new Order("number", "description", 5, USD);
        model.addAttribute("userForm", defaultOrder);
        return "index";
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public String showAddUserForm(@ModelAttribute("userForm") Order order) {
        // TODO: validate amount
        return "index";
    }

    @RequestMapping(value = "/he", method = RequestMethod.GET)
    public String printWelcome(ModelMap model) throws NamingException {
        model.addAttribute("name", getOrderController().sayHello());
        return "index";
    }

    @RequestMapping(value = "/hello/{name:.+}", method = RequestMethod.GET)
    public ModelAndView hello(@PathVariable("name") String name) {
        ModelAndView model = new ModelAndView();
        model.setViewName("index");
        model.addObject("name", name);

        return model;
    }

    private Hello getOrderController() throws NamingException {
        return (Hello) context.lookup("ejb:/testejb//HelloBean!com.svichkarev.anatoly.Hello");
    }
}
