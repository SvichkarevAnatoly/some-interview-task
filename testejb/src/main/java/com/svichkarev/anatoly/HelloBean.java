package com.svichkarev.anatoly;

import javax.ejb.Stateless;

@Stateless
public class HelloBean implements Hello {

    @Override
    public String sayHello() {
        return "Hello world from ejb";
    }
}
