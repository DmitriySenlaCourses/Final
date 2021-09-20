package com.senla.courses.shops.sevices;

import com.senla.courses.shops.api.services.KafkaService;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.*;

@Service
@Transactional
public class KafkaServiceImpl implements KafkaService {

    @Value("${bootstrap.servers.config}")
    private String server;
    @Value("${group.id.config}")
    private String groupIdConfig;
    @Value("${enable.auto.commit.config}")
    private boolean autoCommitConfig;
    @Value("${auto.commit.config.interval.ms}")
    private String autoCommitConfigInterval;
    @Value("${session.timeout.interval.ms}")
    private int sessionTimeoutInterval;

    private Map<String, Object> props = new HashMap<>();
    @Value("${topic.name}")
    private String topicName;

    public KafkaServiceImpl() {
    }

    @PostConstruct
    public void init() {
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupIdConfig);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, autoCommitConfig);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitConfigInterval);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutInterval);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    }

    @Override
    public List<String> getTopic() {

        Consumer<Integer, String> consumer = new KafkaConsumer<>(props);
        TopicPartition tp = new TopicPartition(topicName, 0);
        List<TopicPartition> tps = Collections.singletonList(tp);
        consumer.assign(tps);
        consumer.seek(tp, 0);

        ConsumerRecords<Integer, String> consumerRecords = consumer.poll(Duration.ofSeconds(1L));

        List<String> messages = new ArrayList<>();
        for (ConsumerRecord<Integer, String> consumerRecord : consumerRecords) {
            messages.add(consumerRecord.value());
        }

        consumer.close();
        return messages;

    }
}
