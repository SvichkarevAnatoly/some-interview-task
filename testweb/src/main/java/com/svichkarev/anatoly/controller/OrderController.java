package com.svichkarev.anatoly.controller;

import com.svichkarev.anatoly.EOrder;
import com.svichkarev.anatoly.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

import static com.svichkarev.anatoly.EOrder.Currency.USD;

@Controller
public class OrderController {

    private final InitialContext context;

    public OrderController() throws NamingException {
        final Properties jndiProperties = new Properties();
        jndiProperties.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

        context = new InitialContext(jndiProperties);
    }

    @RequestMapping
    public String showNewOrderForm(Model model) {
        final EOrder defaultOrder = new EOrder("number", "description", 5, USD);
        model.addAttribute("userForm", defaultOrder);
        return "index";
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public String addOrder(@ModelAttribute("userForm") EOrder order) {
        // TODO: validate amount
        getOrderService().addOrder(order);

        return "index";
    }

    @RequestMapping(value = "/hello/{name:.+}", method = RequestMethod.GET)
    public ModelAndView hello(@PathVariable("name") String name) {
        ModelAndView model = new ModelAndView();
        model.setViewName("index");
        model.addObject("name", name);

        return model;
    }

    private OrderService getOrderService() {
        try {
            return (OrderService) context.lookup("ejb:/testejb//OrderServiceBean!com.svichkarev.anatoly.OrderService");
        } catch (NamingException e) {
            e.printStackTrace();
            // TODO: decide what to do
        }
        return null;
    }
}
