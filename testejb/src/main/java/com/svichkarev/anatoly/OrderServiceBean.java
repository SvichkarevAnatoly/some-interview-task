package com.svichkarev.anatoly;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class OrderServiceBean implements OrderService {

    @PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;

    @Override
    public void addOrder(EOrder order) {
        System.out.println("addOrder(" + order + ")");

        /*UserEntity userEntity = entityManager.find(UserEntity.class, "1");
        if (userEntity == null) {
            entityManager.persist(new UserEntity("1", "Anatoly"));
        }*/

        // return "Hello world from ejb";
    }
}
