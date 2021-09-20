package com.senla.courses.shops.controllers;

import com.senla.courses.shops.api.services.KafkaService;
import io.swagger.annotations.Api;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/kafka")
@Api(tags = {"Kafka controller"}, description = "Read kafka topics")
public class MyKafkaController {

    private KafkaService kafkaService;

    @Autowired
    public MyKafkaController(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @GetMapping
    public ResponseEntity<List<String>> getTopic() {
        List<String> messages = kafkaService.getTopic();
        return ResponseEntity.ok(messages);
    }
}
