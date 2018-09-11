package com.svichkarev.anatoly;

import com.svichkarev.anatoly.ejb.EOrder;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.svichkarev.anatoly.db.EOrder.toDbEntity;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(
                propertyName = "destination",
                propertyValue = "java:/txrequest"
        )
})
public class ReceivingMdb implements MessageListener {

    @PersistenceContext(unitName = "orderPU")
    private EntityManager entityManager;

    @Override
    public void onMessage(Message message) {
        EOrder order = getOrder(message);
        System.out.println("onMessage(" + order + ")");

        com.svichkarev.anatoly.db.EOrder dbOrder = toDbEntity(order);
        entityManager.persist(dbOrder);
        entityManager.flush();
        System.out.println(dbOrder.getId());
    }

    private EOrder getOrder(Message message) {
        if (message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage) message;

            try {
                return objectMessage.getBody(EOrder.class);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
