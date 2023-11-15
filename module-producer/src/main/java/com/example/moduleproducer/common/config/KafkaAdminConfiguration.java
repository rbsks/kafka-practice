package com.example.moduleproducer.common.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaAdminConfiguration {

    @Value("${bootstrapServers}")
    private String bootstrapServers;

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic memberTopic() {
        return TopicBuilder
                .name("create_member")
                .partitions(2)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic couponTopic() {
        return TopicBuilder
                .name("create_coupon")
                .partitions(4)
                .replicas(3)
                .build();
    }
}
