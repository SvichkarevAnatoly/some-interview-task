package com.svichkarev.anatoly.ejb;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.Session;
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
    private Queue target;

    @PersistenceContext(unitName = "orderPU")
    private EntityManager entityManager;

    @Override
    public void addOrder(EOrder order) {
        System.out.println("addOrder(" + order + ")");
        sendToProcessorEjb(order);
    }

    @Override
    public List<EOrder> getOrders() {
        Query query = entityManager.createQuery("SELECT o FROM EOrder o");
        return new ArrayList<>(query.getResultList());
    }

    private void sendToProcessorEjb(EOrder order) {
        try {
            QueueConnection connection = (QueueConnection) factory.createConnection();

            try {
                QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                try {
                    MessageProducer producer = session.createProducer(target);
                    try {
                        ObjectMessage message = session.createObjectMessage(order);
                        producer.send(message);
                    } finally {
                        producer.close();
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
    }
}
