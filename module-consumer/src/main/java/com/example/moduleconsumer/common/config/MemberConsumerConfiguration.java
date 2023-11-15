package com.example.moduleconsumer.common.config;

import com.example.moduleconsumer.request.CreateMemberRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MemberConsumerConfiguration {

    @Value("${bootstrapServers}")
    private String bootstrapServers;

    @Bean
    public ConsumerFactory<String, CreateMemberRequest> memberConsumerFactory() {
        JsonDeserializer<CreateMemberRequest> jsonDeserializer = new JsonDeserializer<>(CreateMemberRequest.class);
        jsonDeserializer.setRemoveTypeHeaders(false); // deserializer 후 헤더 정보를 유지하기 위한 설정
        jsonDeserializer.addTrustedPackages("*"); // deserializer를 진행할 수 있는 패키지 경로 설정
        jsonDeserializer.setUseTypeMapperForKey(true); // @KafkaListner가 붙은 메서드에서 @Header 애노테이션을 이용하기 위한 설정

        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "create_member");
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return new DefaultKafkaConsumerFactory<>(
                configs,
                new StringDeserializer(),
                jsonDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CreateMemberRequest> memberConsumerListener() {
        ConcurrentKafkaListenerContainerFactory<String, CreateMemberRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(memberConsumerFactory());
        factory.setConcurrency(2); // 컨슈머 그룹내의 컨슈머 수
        return factory;
    }
}
