package com.svichkarev.anatoly.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<EOrder> getOrders() {
        Query query = entityManager.createQuery("SELECT o FROM EOrder o");
        return new ArrayList<>(query.getResultList());
    }
}
