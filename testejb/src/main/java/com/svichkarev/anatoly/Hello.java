package com.svichkarev.anatoly;

import javax.ejb.Remote;

@Remote
public interface Hello {

    public String sayHello();
}