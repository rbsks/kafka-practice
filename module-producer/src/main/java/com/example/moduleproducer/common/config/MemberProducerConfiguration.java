package com.example.moduleproducer.common.config;

import com.example.moduleproducer.member.presentation.dto.request.CreateMemberRequest;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MemberProducerConfiguration {

    @Value("${bootstrapServers}")
    private String bootstrapServers;

    /*
     * kafka-client 3.1.2에서는 partitioner 전략이 DefaultPartitioner인 경우
     * 배치가 가득 차거나 linger ms 동작으로 인해 레코드가 브로커로 전송되기 전까지 하나의 파티션에 고정으로 레코드를 전송하며
     * 레코드가 브로커로 전송이 되면 고정된 파티션이 다른 파티션으로 바뀌게 된다.
     * 즉, sticky partitioner 방식과 round robin 방식이 섞임.
     * KIP-480을 참고해서 적은 내용이며 kafka-client가 다른 버전인 경우에는 동작 방식이 다를 수 있음.
     */
    @Bean
    public ProducerFactory<String, CreateMemberRequest> memberProducerFactory() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configs.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, false);
        configs.put(ProducerConfig.BATCH_SIZE_CONFIG, 32 * 1024);
        configs.put(ProducerConfig.LINGER_MS_CONFIG, 20);
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public KafkaTemplate<String, CreateMemberRequest> memberKafkaTemplate() {
        return new KafkaTemplate<>(memberProducerFactory());
    }
}
