package com.svichkarev.anatoly.controller;

import com.svichkarev.anatoly.ejb.EOrder;
import com.svichkarev.anatoly.ejb.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

import static com.svichkarev.anatoly.ejb.EOrder.Currency.USD;

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
        model.addAttribute("orderForm", defaultOrder);
        return "index";
    }

    @RequestMapping(value = "/orders/add", method = RequestMethod.POST)
    public String addOrder(@ModelAttribute("orderForm") EOrder order) {
        // TODO: validate amount
        getOrderService().addOrder(order);

        return "index";
    }

    private OrderService getOrderService() {
        try {
            return (OrderService) context.lookup("ejb:/testejb//OrderServiceBean!com.svichkarev.anatoly.ejb.OrderService");
        } catch (NamingException e) {
            e.printStackTrace();
            // TODO: decide what to do
        }
        return null;
    }
}
