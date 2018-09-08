package com.svichkarev.anatoly.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.svichkarev.anatoly.db.EOrder.toDbEntity;

@Stateless
public class OrderServiceBean implements OrderService {

    @PersistenceContext(unitName = "orderPU")
    private EntityManager entityManager;

    @Override
    public void addOrder(EOrder order) {
        System.out.println("addOrder(" + order + ")");
        entityManager.persist(toDbEntity(order));
    }
}
