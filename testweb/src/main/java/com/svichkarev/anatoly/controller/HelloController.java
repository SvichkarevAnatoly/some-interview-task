package com.svichkarev.anatoly.controller;

import com.svichkarev.anatoly.Hello;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

@Controller
public class HelloController {

    @RequestMapping
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/he", method = RequestMethod.GET)
    public String printWelcome(ModelMap model) throws NamingException {
        model.addAttribute("name", getEjbHello());
        return "index";
    }

    @RequestMapping(value = "/hello/{name:.+}", method = RequestMethod.GET)
    public ModelAndView hello(@PathVariable("name") String name) {
        ModelAndView model = new ModelAndView();
        model.setViewName("index");
        model.addObject("name", name);

        return model;
    }

    private String getEjbHello() throws NamingException {
        final Properties jndiProperties = new Properties();
        jndiProperties.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        InitialContext context = new InitialContext(jndiProperties);

        Hello bean = (Hello) context.lookup("ejb:/testejb//HelloBean!com.svichkarev.anatoly.Hello");
        return bean.sayHello();
    }
}
