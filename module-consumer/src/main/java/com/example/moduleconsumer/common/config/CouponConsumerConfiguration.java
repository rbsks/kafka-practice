package com.example.moduleconsumer.common.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CouponConsumerConfiguration {

    @Value("${bootstrapServers}")
    private String bootstrapServers;

    @Bean
    public ConsumerFactory<String, Long> couponConsumerFactory() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "create_coupon");
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, OffsetResetStrategy.LATEST.toString().toLowerCase());
        configs.put(ConsumerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, -1);
        return new DefaultKafkaConsumerFactory<>(
                configs,
                new StringDeserializer(),
                new LongDeserializer()
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Long> couponConsumerListener() {
        ConcurrentKafkaListenerContainerFactory<String, Long> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(couponConsumerFactory());
        factory.setConcurrency(4); // 컨슈머 그룹내의 컨슈머 수
        factory.setBatchListener(false);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        return factory;
    }
}
