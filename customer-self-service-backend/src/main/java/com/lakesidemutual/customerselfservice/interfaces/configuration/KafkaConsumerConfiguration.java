package com.lakesidemutual.customerselfservice.interfaces.configuration;

import com.lakesidemutual.customerselfservice.domain.insurancequoterequest.InsuranceQuoteExpiredEvent;
import com.lakesidemutual.customerselfservice.domain.insurancequoterequest.InsuranceQuoteResponseEvent;
import com.lakesidemutual.customerselfservice.domain.insurancequoterequest.PolicyCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.group-id}")
    private String groupId;


    @Bean
    public ConsumerFactory<String, InsuranceQuoteExpiredEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(InsuranceQuoteExpiredEvent.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, InsuranceQuoteExpiredEvent> insuranceQuoteExpiredListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, InsuranceQuoteExpiredEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, InsuranceQuoteResponseEvent> insuranceQuoteResponseConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(InsuranceQuoteResponseEvent.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, InsuranceQuoteResponseEvent> insuranceQuoteResponseListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, InsuranceQuoteResponseEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(insuranceQuoteResponseConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, PolicyCreatedEvent> policyCreatedConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(PolicyCreatedEvent.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PolicyCreatedEvent> policyCreatedListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PolicyCreatedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(policyCreatedConsumerFactory());
        return factory;
    }
}
