package com.svichkarev.anatoly.controller;

import com.svichkarev.anatoly.CurrencyConverter;
import com.svichkarev.anatoly.ejb.EOrder;
import com.svichkarev.anatoly.ejb.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

import static com.svichkarev.anatoly.ejb.Currency.USD;

@Controller
public class OrderController {

    private static final int MAX_USD_AMOUNT = 5000;

    private OrderService orderService;

    @Autowired
    private CurrencyConverter converter;

    @PostConstruct
    public void init() throws NamingException {
        final Properties jndiProperties = new Properties();
        jndiProperties.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

        InitialContext context = new InitialContext(jndiProperties);
        orderService = (OrderService) context.lookup("ejb:/testejb//OrderServiceBean!com.svichkarev.anatoly.ejb.OrderService");
    }

    @RequestMapping
    public String showNewOrderForm(Model model) {
        final EOrder defaultOrder = new EOrder("number", "description", 5, USD);
        model.addAttribute("orderForm", defaultOrder);
        return "index";
    }

    @RequestMapping(value = "/orders")
    public String getOrders(Model model) {
        model.addAttribute("orders", orderService.getOrders());
        return "list";
    }

    @RequestMapping(value = "/orders/add", method = RequestMethod.POST)
    public String addOrder(@ModelAttribute("orderForm") EOrder order, Model model) {
        if (converter.convert(order.getAmount(), order.getCurrency(), USD) > MAX_USD_AMOUNT) {
            model.addAttribute("amountError", "Required amount is less than 5000 USD");
        } else {
            orderService.addOrder(order);
        }

        return "index";
    }
}
