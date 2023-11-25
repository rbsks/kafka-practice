package com.example.moduleproducer.common.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CouponProducerConfiguration {

    @Value("${bootstrapServers}")
    private String bootstrapServers;

    @Bean
    public ProducerFactory<String, Long> couponProducerFactory() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        configs.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, false);
        configs.put(ProducerConfig.BATCH_SIZE_CONFIG, 32 * 1024);
        configs.put(ProducerConfig.LINGER_MS_CONFIG, 20);
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public KafkaTemplate<String, Long> couponKafkaTemplate() {
        return new KafkaTemplate<>(couponProducerFactory());
    }
}
