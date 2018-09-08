package com.svichkarev.anatoly.ejb;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface OrderService {

    void addOrder(EOrder order);

    List<EOrder> getOrders();
}
