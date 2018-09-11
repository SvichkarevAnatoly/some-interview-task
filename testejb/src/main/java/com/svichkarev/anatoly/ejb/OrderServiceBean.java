package com.svichkarev.anatoly.ejb;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class OrderServiceBean implements OrderService {

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory factory;

    @Resource(mappedName = "java:/txrequest")
    private Queue requestQueue;

    @Resource(mappedName = "java:/txresponse")
    private Queue responseQueue;

    @PersistenceContext(unitName = "orderPU")
    private EntityManager entityManager;

    @Override
    public void addOrder(EOrder order) {
        System.out.println("addOrder(" + order + ")");
        runSavingProcess(order);
    }

    @Override
    public List<EOrder> getOrders() {
        Query query = entityManager.createQuery("SELECT o FROM EOrder o");
        return new ArrayList<>(query.getResultList());
    }

    private void runSavingProcess(EOrder order) {
        long orderId = 0;
        try {
            QueueConnection connection = (QueueConnection) factory.createConnection();
            connection.start();

            try {
                QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                try {
                    MessageProducer producer = session.createProducer(requestQueue);
                    ObjectMessage message = send(order, session, producer);

                    if (message != null) {
                        orderId = syncReceive(session, message.getJMSMessageID());
                    }
                } finally {
                    session.close();
                }
            } finally {
                connection.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Successfully save order " + order + " with id " + orderId);
    }

    private long syncReceive(QueueSession session, String correlationId) throws JMSException {
        final String responseSelector = String.format("JMSCorrelationID='%s'", correlationId);
        QueueReceiver receiver = session.createReceiver(responseQueue, responseSelector);
        Message response = receiver.receive();

        long orderId = Long.valueOf(((TextMessage) response).getText());
        receiver.close();

        return orderId;
    }

    private ObjectMessage send(EOrder order, QueueSession session, MessageProducer producer) throws JMSException {
        ObjectMessage message;
        try {
            message = session.createObjectMessage(order);
            producer.send(message);
        } finally {
            producer.close();
        }
        return message;
    }
}
