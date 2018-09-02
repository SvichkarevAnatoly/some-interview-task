package com.svichkarev.anatoly;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class HelloBean implements Hello {

    @PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;

    @Override
    public String sayHello() {
        UserEntity userEntity = entityManager.find(UserEntity.class, "1");
        if (userEntity == null) {
            entityManager.persist(new UserEntity("1", "Anatoly"));
        }

        return "Hello world from ejb";
    }
}
