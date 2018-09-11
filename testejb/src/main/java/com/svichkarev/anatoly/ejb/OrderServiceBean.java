package com.svichkarev.anatoly.ejb;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static com.svichkarev.anatoly.db.EOrder.toDbEntity;

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
        entityManager.persist(toDbEntity(order));
    }

    @Override
    public List<EOrder> getOrders() {
        sendMessage("test msg");
        Query query = entityManager.createQuery("SELECT o FROM EOrder o");
        return new ArrayList<>(query.getResultList());
    }

    public void sendMessage(String txt) {
        try {
            QueueConnection connection = (QueueConnection) factory.createConnection();

            try {
                QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                try {
                    MessageProducer producer = session.createProducer(target);
                    try {
                        TextMessage message = session.createTextMessage(txt);
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
