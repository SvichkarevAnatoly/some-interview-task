package com.svichkarev.anatoly;

import javax.ejb.Remote;

@Remote
public interface OrderService {

    void addOrder(EOrder order);
}
