package com.blogspot.nurkiewicz;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.ProducerCallback;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * @author Tomasz Nurkiewicz
 * @since 09.01.11, 14:21
 */
@ManagedResource
public class FooService {

	private static final Logger log = LoggerFactory.getLogger(FooService.class);

	private JmsOperations jmsOperations;

	@ManagedOperation
	public String sendRequest(final String request) {
		log.info("Handling request: {}", request);
		return jmsOperations.execute(new ProducerCallback<String>() {
			@Override
			public String doInJms(Session session, MessageProducer producer) throws JMSException {
				final TextMessage message = session.createTextMessage(request);
				producer.send(message);
				log.debug("Sent JMS message with payload='{}', id: '{}'", request, message.getJMSMessageID());
				return message.getJMSMessageID();
			}
		});
	}

	public void setJmsOperations(JmsOperations jmsOperations) {
		this.jmsOperations = jmsOperations;
	}
}
