package com.lakesidemutual.customerselfservice.interfaces.configuration;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfiguration {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${insuranceQuoteRequestEvent.queueName}")
    private String insuranceQuoteRequestTopic;

    @Value(value = "${customerDecisionEvent.queueName}")
    private String customerDecisionTopic;

    @Value(value = "${insuranceQuoteResponseEvent.queueName}")
    private String insuranceQuoteResponseTopic;

    @Value(value = "${insuranceQuoteResponseEvent.queueName}")
    private String insuranceQuoteExpiredTopic;

    @Value(value = "${policyCreatedEvent.queueName}")
    private String policyCreatedTopic;

    @Value(value = "${riskmanagement.queueName}")
    private String riskManagementTopic;


    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic createInsuranceQuoteRequestTopic() {
        return new NewTopic(insuranceQuoteRequestTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic createCustomerDecisionTopic() {
        return new NewTopic(customerDecisionTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic createInsuranceQuoteResponseTopic() {
        return new NewTopic(insuranceQuoteResponseTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic createInsuranceQuoteExpiredTopic() {
        return new NewTopic(insuranceQuoteExpiredTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic createPolicyCreatedTopic() {
        return new NewTopic(policyCreatedTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic createRiskManagementTopic() {
        return new NewTopic(riskManagementTopic, 1, (short) 1);
    }


}