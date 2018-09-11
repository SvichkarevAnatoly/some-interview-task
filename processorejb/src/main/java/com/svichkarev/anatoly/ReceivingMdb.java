package com.svichkarev.anatoly;

import com.svichkarev.anatoly.ejb.EOrder;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(
                propertyName = "destination",
                propertyValue = "java:/txrequest"
        )
})
public class ReceivingMdb implements MessageListener {

    @Override
    public void onMessage(Message message) {
        if (message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage) message;

            try {
                EOrder order = objectMessage.getBody(EOrder.class);
                System.out.println(order);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
