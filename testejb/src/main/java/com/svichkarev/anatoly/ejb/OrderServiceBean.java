package com.svichkarev.anatoly.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.svichkarev.anatoly.db.EOrder.toDbEntity;
import static com.svichkarev.anatoly.ejb.EOrder.Currency.EUR;
import static com.svichkarev.anatoly.ejb.EOrder.Currency.USD;
import static java.util.Arrays.asList;

@Stateless
public class OrderServiceBean implements OrderService {

    @PersistenceContext(unitName = "orderPU")
    private EntityManager entityManager;

    @Override
    public void addOrder(EOrder order) {
        System.out.println("addOrder(" + order + ")");
        entityManager.persist(toDbEntity(order));
    }

    @Override
    public List<EOrder> getOrders() {
        return asList(
                new EOrder("1", "1", 1, USD),
                new EOrder("2", "2", 2, EUR)
        );
    }
}
