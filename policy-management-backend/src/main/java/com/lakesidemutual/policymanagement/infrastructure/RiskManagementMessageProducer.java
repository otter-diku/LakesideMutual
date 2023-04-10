package com.lakesidemutual.policymanagement.infrastructure;

import org.microserviceapipatterns.domaindrivendesign.DomainEvent;
import org.microserviceapipatterns.domaindrivendesign.InfrastructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * RiskManagementMessageProducer is an infrastructure service class that is used to notify the Risk Management Server
 * about policy events (e.g., a new policy is created). These events are transmitted via Kafka.
 * */
@Component
public class RiskManagementMessageProducer implements InfrastructureService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${riskmanagement.queueName}")
	private String queueName;


	@Autowired
	private KafkaTemplate<String, DomainEvent> kafkaTemplate;

	public void emitEvent(DomainEvent event) {
		try {
			// jmsTemplate.convertAndSend(queueName, event);
			kafkaTemplate.send(queueName, event);

			logger.info("Successfully sent a policy event to the risk management message queue.");
		} catch(JmsException exception) {
			logger.error("Failed to send a policy event to the risk management message queue.", exception);
		}
	}
}

