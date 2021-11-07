package com.senla.courses.shops.sevices;

import com.senla.courses.shops.api.services.KafkaService;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class KafkaServiceImpl implements KafkaService {

    @Value("${topic.name}")
    private String topicName;

    @Autowired
    @Qualifier("consConf")
    private Map<String, Object> constConfig;

    private Map<String, Object> props = new HashMap<>();

    @PostConstruct
    public void init() {
        Object consumerConfig = constConfig.values().iterator().next();
        props = (HashMap<String, Object>) consumerConfig;
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
