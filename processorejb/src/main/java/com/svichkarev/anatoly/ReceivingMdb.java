package com.svichkarev.anatoly;

import com.svichkarev.anatoly.ejb.EOrder;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
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

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory factory;

    @Resource(mappedName = "java:/txresponse")
    private Queue responseQueue;

    @PersistenceContext(unitName = "orderPU")
    private EntityManager entityManager;

    @Override
    public void onMessage(Message message) {
        EOrder order = getOrder(message);
        System.out.println("onMessage(" + order + ")");

        com.svichkarev.anatoly.db.EOrder dbOrder = save(order);

        sendResponseOrderId(dbOrder.getId(), message);
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

    private com.svichkarev.anatoly.db.EOrder save(EOrder order) {
        com.svichkarev.anatoly.db.EOrder dbOrder = toDbEntity(order);
        entityManager.persist(dbOrder);
        entityManager.flush();
        return dbOrder;
    }

    private void sendResponseOrderId(long orderId, Message receivedMessage) {
        try (QueueConnection connection = (QueueConnection) factory.createConnection();
             QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
             MessageProducer producer = session.createProducer(responseQueue)) {

            TextMessage orderMsg = session.createTextMessage(String.valueOf(orderId));
            orderMsg.setJMSCorrelationID(receivedMessage.getJMSMessageID());
            producer.send(orderMsg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
