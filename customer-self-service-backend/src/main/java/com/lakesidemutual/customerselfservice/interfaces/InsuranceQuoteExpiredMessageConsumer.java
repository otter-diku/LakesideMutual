package com.lakesidemutual.customerselfservice.interfaces;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.lakesidemutual.customerselfservice.domain.insurancequoterequest.InsuranceQuoteExpiredEvent;
import com.lakesidemutual.customerselfservice.domain.insurancequoterequest.InsuranceQuoteRequestAggregateRoot;
import com.lakesidemutual.customerselfservice.infrastructure.InsuranceQuoteRequestRepository;

/**
 * InsuranceQuoteExpiredMessageConsumer is a Spring component that consumes InsuranceQuoteExpiredEvents
 * as they arrive through the ActiveMQ message queue. It processes these events by marking the corresponding
 * insurance quote requests as expired.
 * */
@Component
public class InsuranceQuoteExpiredMessageConsumer {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private InsuranceQuoteRequestRepository insuranceQuoteRequestRepository;

	@KafkaListener(topics = "${insuranceQuoteExpiredEvent.queueName}",
			groupId = "${spring.kafka.group-id}",
			containerFactory = "insuranceQuoteExpiredListenerFactory")
	public void receiveInsuranceQuoteExpiredEvent(final InsuranceQuoteExpiredEvent insuranceQuoteExpiredEvent) {
		logger.info("Received insurance quote expired event");

		final Long id = insuranceQuoteExpiredEvent.getInsuranceQuoteRequestId();
		final Optional<InsuranceQuoteRequestAggregateRoot> insuranceQuoteRequestOpt = insuranceQuoteRequestRepository.findById(id);

		if(!insuranceQuoteRequestOpt.isPresent()) {
			logger.error("Unable to process an insurance quote expired event with an invalid insurance quote request id.");
			return;
		}

		final InsuranceQuoteRequestAggregateRoot insuranceQuoteRequest = insuranceQuoteRequestOpt.get();
		insuranceQuoteRequest.markQuoteAsExpired(insuranceQuoteExpiredEvent.getDate());
		logger.info("The insurance quote for insurance quote request " + insuranceQuoteRequest.getId() + " has expired.");
		insuranceQuoteRequestRepository.save(insuranceQuoteRequest);
	}
}